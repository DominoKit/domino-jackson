package org.dominokit.jacksonapt.registration.processor;

import com.google.auto.common.MoreTypes;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONRegistration;
import org.dominokit.jacksonapt.annotation.JSONWriter;
import org.dominokit.jacksonapt.registration.JsonRegistry;

import javax.annotation.Generated;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoService(Processor.class)
public class JSONRegistrationProcessor extends AbstractProcessor {

    private static final String WRITERS = "WRITERS";
    private static final String READERS = "READERS";
    private static final String MAPPERS = "MAPPERS";
    private Messager messager;
    private Types typeUtils;
    private Filer filer;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(JSONRegistration.class)
                .forEach(configuration -> register(roundEnv, configuration));
        return false;
    }

    private void register(RoundEnvironment roundEnv, Element element) {
        FieldSpec mappersMap = createConstantMap(MAPPERS, ObjectMapper.class);
        FieldSpec readersMap = createConstantMap(READERS, ObjectReader.class);
        FieldSpec writersMap = createConstantMap(WRITERS, ObjectWriter.class);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        Set<? extends Element> mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
        mappers.stream().map(this::registerMapperLine).forEach(constructorBuilder::addCode);

        Set<? extends Element> readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
        readers.stream().map(this::registerReaderLine).forEach(constructorBuilder::addCode);

        Set<? extends Element> writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
        writers.stream().map(this::registerWriterLine).forEach(constructorBuilder::addCode);


        MethodSpec getMapperMethod = createGetMethod("getMapper", MAPPERS, ObjectMapper.class, false);
        MethodSpec getReaderMethod = createGetMethod("getReader", READERS, ObjectReader.class, true);
        MethodSpec getWriterMethod = createGetMethod("getWriter", WRITERS, ObjectWriter.class, true);

        JSONRegistration annotation = element.getAnnotation(JSONRegistration.class);
        TypeSpec jacksonConfigurator = TypeSpec.classBuilder(annotation.value() + "JsonRegistry")
                .addJavadoc(CodeBlock.of("This is generated class, please don't modify\n"))
                .addSuperinterface(JsonRegistry.class)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "\"" + getClass().getCanonicalName() + "\"")
                        .build())
                .addModifiers(Modifier.PUBLIC)
                .addField(mappersMap)
                .addField(readersMap)
                .addField(writersMap)
                .addMethod(constructorBuilder.build())
                .addMethod(getMapperMethod)
                .addMethod(getReaderMethod)
                .addMethod(getWriterMethod)
                .build();

        try {
            JavaFile.builder(packageOf(element), jacksonConfigurator).build().writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    private MethodSpec createGetMethod(String name, String mapName, Class<?> returnType, boolean lookupIfNotFound) {
        TypeVariableName typeVariable = TypeVariableName.get("T");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addTypeVariable(typeVariable)
                .returns(ParameterizedTypeName.get(ClassName.get(returnType), typeVariable))
                .addParameter(ParameterizedTypeName.get(ClassName.get(Class.class), typeVariable), "type");

        if (lookupIfNotFound) {
            methodBuilder.beginControlFlow("if(" + mapName + ".containsKey(type))")
                    .addStatement("return " + mapName + ".get(type)")
                    .endControlFlow()
                    .addStatement("return " + MAPPERS + ".get(type)");
        } else {
            methodBuilder.addStatement("return " + mapName + ".get(type)");
        }
        return methodBuilder.build();
    }

    private FieldSpec createConstantMap(String name, Class<?> jsonType) {
        ClassName mapType = ClassName.get(Map.class);
        ParameterizedTypeName classOfWildcard = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(Object.class));
        ClassName jsonMapperType = ClassName.get(jsonType);

        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(mapType, classOfWildcard, jsonMapperType);
        return FieldSpec.builder(parameterizedTypeName, name, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", HashMap.class)
                .build();
    }

    private String packageOf(Element configuration) {
        return processingEnv.getElementUtils().getPackageOf(configuration).getQualifiedName().toString();
    }

    private CodeBlock registerMapperLine(Element element) {
        return registerLine(element, MAPPERS);
    }

    private CodeBlock registerReaderLine(Element element) {
        return registerLine(element, READERS);
    }

    private CodeBlock registerWriterLine(Element element) {
        return registerLine(element, WRITERS);
    }

    private CodeBlock registerLine(Element element, String mapName) {
        String className = enclosingName(element) + (useInterface(element) ? element.getSimpleName() : "Mapper") + "Impl";
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        TypeMirror beanType = getBeanType(element);
        return CodeBlock.builder()
                .addStatement(mapName + ".put($T.class, new " + packageName + "." + className + "())", beanType)
                .build();
    }

    private String enclosingName(Element element) {
        if (useInterface(element))
            return element.getEnclosingElement().getSimpleName().toString() + "_";
        return element.getSimpleName().toString() + "_";
    }

    private boolean useInterface(Element element) {
        return isAssignableFrom(element, ObjectMapper.class) || isAssignableFrom(element, ObjectReader.class) ||
                isAssignableFrom(element, ObjectWriter.class);
    }

    private boolean isAssignableFrom(Element element, Class<?> targetClass) {
        return typeUtils.isAssignable(element.asType(), typeUtils.getDeclaredType(elementUtils.getTypeElement(targetClass.getName())));
    }

    private TypeMirror getBeanType(Element element) {
        if (useInterface(element)) {
            TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
            return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
        } else {
            return element.asType();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(JSONRegistration.class).map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
