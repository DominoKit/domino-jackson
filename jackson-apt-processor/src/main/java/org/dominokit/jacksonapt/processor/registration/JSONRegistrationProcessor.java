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
import org.dominokit.jacksonapt.registration.TypeToken;

import javax.annotation.Generated;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

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
    private static final String INSTANCE_NAME = "INSTANCE";

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
                .addModifiers(Modifier.PRIVATE);

        mappers.stream().map(this::registerMapperLine).forEach(constructorBuilder::addCode);

        readers.stream().map(this::registerReaderLine).forEach(constructorBuilder::addCode);

        writers.stream().map(this::registerWriterLine).forEach(constructorBuilder::addCode);

        ClassName className = ClassName.get(packageOf(element), element.getAnnotation(JSONRegistration.class).value() + "JsonRegistry");
        FieldSpec instanceField = FieldSpec
        	.builder(className, INSTANCE_NAME, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
			.initializer("new $T()", className)
			.build();
        
        MethodSpec getMapperMethod = createGetMethod("getMapper", MAPPERS, ObjectMapper.class, false);
        MethodSpec getReaderMethod = createGetMethod("getReader", READERS, ObjectReader.class, true);
        MethodSpec getWriterMethod = createGetMethod("getWriter", WRITERS, ObjectWriter.class, true);
        
        TypeSpec jacksonConfigurator = TypeSpec.classBuilder(className)
                .addJavadoc(CodeBlock.of("This is generated class, please don't modify\n"))
                .addSuperinterface(JsonRegistry.class)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "\"" + getClass().getCanonicalName() + "\"")
                        .build())
                .addModifiers(Modifier.PUBLIC)
                .addField(mappersMap)
                .addField(readersMap)
                .addField(writersMap)
                .addField(instanceField)
                .addMethod(createGetInstanceMethod(className))
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

    private MethodSpec createGetInstanceMethod(TypeName result) {
    	 return
    		 MethodSpec.methodBuilder("getInstance")
            	.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(result)
                 .addStatement("return " + INSTANCE_NAME)
                 .build();
                 
	}

	private MethodSpec createGetMethod(String name, String mapName, Class<?> returnType, boolean lookupIfNotFound) {
        TypeVariableName typeVariable = TypeVariableName.get("T");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember("value", "\"unchecked\"")
                        .build())
                .addTypeVariable(typeVariable)
                .returns(ParameterizedTypeName.get(ClassName.get(returnType), typeVariable))
        		.addParameter(
        			ParameterizedTypeName.get(
        				ClassName.get("org.dominokit.jacksonapt.registration", "TypeToken"), 
        				typeVariable),
            		"type");

      	ParameterizedTypeName returnTypeName = ParameterizedTypeName.get(ClassName.get(returnType), typeVariable);
        if (lookupIfNotFound) {
            methodBuilder.beginControlFlow("if(" + mapName + ".containsKey(type))")
                    .addStatement("return ($T)" + mapName + ".get(type)", returnTypeName)
                    .endControlFlow()
                    .addStatement("return ($T)" + MAPPERS + ".get(type)", returnTypeName);
        } else {
            methodBuilder.addStatement("return ($T)" + mapName + ".get(type)", returnTypeName);
        }
        
        return methodBuilder.build();
    }

    private FieldSpec createConstantMap(String name, Class<?> jsonType) {
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
        	ClassName.get(Map.class), 
        	ParameterizedTypeName.get(ClassName.get("org.dominokit.jacksonapt.registration", "TypeToken"), TypeVariableName.get("?")), 
        	ParameterizedTypeName.get(ClassName.get(jsonType), TypeVariableName.get("?")));

        return FieldSpec.builder(parameterizedTypeName, name, Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", HashMap.class)
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

        CodeBlock.Builder typeTokenBuilder = CodeBlock.builder();
        addTypeTokenLiteral(typeTokenBuilder, TypeName.get(getBeanType(element)));
        
        return CodeBlock.builder()
                .addStatement(
                	mapName + ".put($L, new " + packageName + "." + className + "())",
                	typeTokenBuilder.build())
                .build();
    }
    
    private void addTypeTokenLiteral(CodeBlock.Builder builder, TypeName name) {
    	builder.add("new $T<$L>(", TypeToken.class, name.isPrimitive()? name.box(): name);

    	TypeName rawType;
    	List<TypeName> typeArguments;

    	if (name instanceof ParameterizedTypeName) {
    		ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName)name;
    		rawType = parameterizedTypeName.rawType;
    		typeArguments = parameterizedTypeName.typeArguments;
    	} else if (name instanceof ArrayTypeName) {
    		ArrayTypeName arrayTypeName = (ArrayTypeName)name;

    		rawType = null;
    		typeArguments = Collections.singletonList(arrayTypeName.componentType);
    	} else if (name instanceof ClassName || name instanceof TypeName) {
    		rawType = name.isPrimitive()? name.box(): name;
    		typeArguments = Collections.emptyList();
    	} else
    		throw new IllegalArgumentException("Unsupported type " + name); 

    	if(rawType == null)
    		builder.add("null");
    	else
    		builder.add("$T.class", rawType);

    	for (TypeName typeArgumentName: typeArguments) {
    		builder.add(", ");
    		addTypeTokenLiteral(builder, typeArgumentName);
    	}

    	builder.add(") {}");
    }
    
    private String enclosingName(Element element) {
        if (useInterface(element))
            return element.getEnclosingElement().getSimpleName().toString() + "_";
        String prefix = element.getEnclosingElement().getKind().equals(ElementKind.PACKAGE) ? ""
                : element.getEnclosingElement().getSimpleName().toString() + "_";
        return prefix + element.getSimpleName().toString() + "_";
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
    protected List<Class<?>> supportedAnnotations() {
        return Collections.singletonList(JSONRegistration.class);
    }
}
