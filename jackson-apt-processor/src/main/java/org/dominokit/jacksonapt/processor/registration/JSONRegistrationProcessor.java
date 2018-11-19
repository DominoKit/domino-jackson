package org.dominokit.jacksonapt.processor.registration;

import com.google.auto.common.MoreTypes;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.annotation.JSONRegistration;
import org.dominokit.jacksonapt.processor.AbstractMapperProcessor;
import org.dominokit.jacksonapt.registration.JsonRegistry;
import org.dominokit.jacksonapt.registration.Type;

import javax.annotation.Generated;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor6;

import java.io.IOException;
import java.util.*;

/**
 * <p>JSONRegistrationProcessor class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@AutoService(Processor.class)
public class JSONRegistrationProcessor extends AbstractMapperProcessor {

    private static final String WRITERS = "WRITERS";
    private static final String READERS = "READERS";
    private static final String MAPPERS = "MAPPERS";

    /** {@inheritDoc} */
    @Override
    protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(JSONRegistration.class)
                .forEach(this::register);
        return false;
    }

    private void register(Element element) {
        FieldSpec mappersMap = createConstantMap(MAPPERS, ObjectMapper.class);
        FieldSpec readersMap = createConstantMap(READERS, ObjectReader.class);
        FieldSpec writersMap = createConstantMap(WRITERS, ObjectWriter.class);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        mappers.stream().map(this::registerMapperLine).forEach(constructorBuilder::addCode);

        readers.stream().map(this::registerReaderLine).forEach(constructorBuilder::addCode);

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
            handleError(e);
        }
    }

    private MethodSpec createGetMethod(String name, String mapName, Class<?> returnType, boolean lookupIfNotFound) {
        TypeVariableName typeVariable = TypeVariableName.get("T");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addTypeVariable(typeVariable)
                .returns(ParameterizedTypeName.get(ClassName.get(returnType), typeVariable))
                .addParameter(ClassName.get("org.dominokit.jacksonapt.registration", "Type"), "type");

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
        ClassName typeClassName = ClassName.get(Type.class);
        ClassName jsonMapperType = ClassName.get(jsonType);
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(mapType, typeClassName, jsonMapperType);

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

        StringBuilder typesCreationCode = new StringBuilder();
    	List<ClassName> classNames = new ArrayList<>();
    	processType(getBeanType(element), typesCreationCode, classNames);
    	
        return CodeBlock.builder()
                .addStatement(
                	mapName + ".put("+ typesCreationCode.toString() + ", new " + packageName + "." + className + "())", 
                	classNames.toArray(new Object[classNames.size()]))
                .build();
    }
    
    private void processType(TypeMirror type, StringBuilder result, List<ClassName> classNames) {
		type.accept(new SimpleTypeVisitor6<Void, Void>() {
			
			@Override
			public Void visitDeclared(DeclaredType declaredType, Void v) {
				result.append("Type.of(");
				classNames.add(ClassName.get((TypeElement) declaredType.asElement()));
				result.append("$T");
				result.append(".class)");

				for (TypeMirror type: declaredType.getTypeArguments()) {
					result.append(".typeParam(");
					processType(type, result, classNames);
					result.append(")");
				}
				
				return null;
			}

			@Override
			public Void visitPrimitive(PrimitiveType primitiveType, Void v) {
				result.append("Type.of(");
				result.append(primitiveType);
				result.append(".class)");
				return null; 
			}

			@Override
			public Void visitArray(ArrayType arrayType, Void v) {
				result.append("Type.array(");
				processType(arrayType.getComponentType(), result, classNames);
				result.append(")");
				
				return null;
			}

			@Override
			public Void visitTypeVariable(TypeVariable typeVariable, Void v) {
				processType(processingEnv.getTypeUtils().erasure(typeVariable), result, classNames);
				return null;
			}

			@Override
			public Void visitError(ErrorType errorType, Void v) { 
				return null;
			}

			@Override
			protected Void defaultAction(TypeMirror typeMirror, Void v) { 
				return null;
			}
		},
		null);
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

    /** {@inheritDoc} */
    @Override
    protected List<Class> supportedAnnotations() {
        return Collections.singletonList(JSONRegistration.class);
    }
}
