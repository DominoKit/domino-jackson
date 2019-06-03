package org.dominokit.jacksonapt.processor;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.filer;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.DEFAULT_WILDCARD;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.processor.deserialization.FieldDeserializersChainBuilder;
import org.dominokit.jacksonapt.processor.serialization.FieldSerializerChainBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public abstract class AbstractMapperGenerator implements MapperGenerator {

	@Override
	public void generate(Element element) throws IOException {
		String className = enclosingName(element, "_") + (useInterface(element) ? element.getSimpleName() : "Mapper") + "Impl";
		String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
		TypeMirror beanType = getElementType(element);
		Name beanName = typeUtils.asElement(beanType).getSimpleName();

		generateJsonMappers(beanType, packageName, beanName);

		TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(abstractObjectMapper(element))
                .addField(FieldSpec.builder(ClassName.bestGuess(className), "INSTANCE")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.builder().add("new $T()", ClassName.bestGuess(className)).build()).
                                build())
                .addMethod(makeConstructor(beanName))
                .addMethods(getMapperMethods(element, beanType));

		if (useInterface(element))
            builder.addSuperinterface(TypeName.get(element.asType()));

		TypeSpec classSpec = builder
				.build();

		JavaFile.builder(packageName, classSpec).build().writeTo(filer);
	}

	protected static TypeMirror getElementType(Element element) {
		if(useInterface(element)){
			TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
			return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
		}else{
			return element.asType();
		}

	}

	protected static boolean useInterface(Element element) {
		return 
			Type.isAssignableFrom(element.asType(), ObjectMapper.class) 
			|| Type.isAssignableFrom(element.asType(), ObjectReader.class) 
			|| Type.isAssignableFrom(element.asType(), ObjectWriter.class);
	}

	protected String enclosingName(Element element, String postfix) {
		if (useInterface(element)) {
			return element.getEnclosingElement().getSimpleName().toString() + postfix;

		}
		return element.getSimpleName().toString() + postfix;

	}

	protected TypeName abstractObjectMapper(Element element) {
		TypeMirror beanType = getElementType(element);
		return ParameterizedTypeName.get(ClassName.get(getSuperClass()),
				ClassName.get(beanType));
	}

	protected MethodSpec makeConstructor(Name beanName) {
		return MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addStatement("super(\"" + beanName + "\")").build();
	}

	/**
	 *  <p>makeNewDeserializerMethod.</p>
	 *  
	 * Creates method for build corresponding deserializer for  given beanType. If beanType is
	 * basic type, generated code utilize existing deserializers. Otherwise, it creates instances
	 * of newly generated ones.
	 * 
	 * @param element
	 * @param beanType
	 * @return 
	 */
	protected MethodSpec makeNewDeserializerMethod(Element element, TypeMirror beanType) {
		CodeBlock.Builder builder = CodeBlock.builder();
		if (Type.isBasicType(typeUtils.erasure(beanType)))
			builder.addStatement("return $L", new FieldDeserializersChainBuilder(getElementType(element)).getInstance(getElementType(element)));
		else
			builder.addStatement("return new " + deserializerName(beanType));

		return MethodSpec.methodBuilder("newDeserializer")
				.addModifiers(Modifier.PROTECTED)
				.addAnnotation(Override.class)
				.returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
						ClassName.get(getElementType(element))))
				.addCode(builder.build())
				.build();

	}
	
	/**
	 *  <p>makeNewSerializerMethod.</p>
	 *  
	 * Creates method for build corresponding serializer for  given beanType. If beanType is
	 * basic type, generated code utilize existing serializers. Otherwise, it creates instances
	 * of newly generated ones.
	 * 
	 * @param beanType
	 * @return
	 */
	protected MethodSpec makeNewSerializerMethod(TypeMirror beanType) {
		CodeBlock.Builder builder = CodeBlock.builder();
		if (Type.isBasicType(typeUtils.erasure(beanType)))
			builder.addStatement("return $L", new FieldSerializerChainBuilder(beanType).getInstance(beanType));
		else
			builder.addStatement("return new " + serializerName(beanType));

		return MethodSpec.methodBuilder("newSerializer")
				.addModifiers(Modifier.PROTECTED)
				.addAnnotation(Override.class)
				.returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
				.addCode(builder.build())
				.build();
	}
	
	private String deserializerName(TypeMirror type) {
		return Type.stringifyType(type) + "BeanJsonDeserializerImpl()";
	}
	
	private String serializerName(TypeMirror type) {
		return Type.stringifyType(type) + "BeanJsonSerializerImpl()";
	}
	
	/**
	 * <p>getSuperClass.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	protected abstract Class<?> getSuperClass();

	/**
     * <p>getMapperMethods.</p>
     *
     * @param element  a {@link javax.lang.model.element.Element} object.
     * @param type a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.Iterable} object.
     */
	protected abstract Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror type);
	
	/**
     * <p>generateJsonMappers.</p>
     *
     * Creates  mapper implementation for given beanType. If beanType is generic type, create
     * corresponding mappers for type arguments incl. their type arguments as well. All type
     * parameters will be included as part of the name of the mapper implementation.
     * 
     * Note that mappers are not generated for "simple" data types, since their implementation
     * already exist.
     * 
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     */
	
	private void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
		if (beanType instanceof DeclaredType) {
			TypeElement beanElement =  (TypeElement)((DeclaredType)beanType).asElement();
			SubTypesInfo subTypeInfo = SubTypesInfo.emtpy();
			
			JsonTypeInfo typeInfo = beanElement.getAnnotation(com.fasterxml.jackson.annotation.JsonTypeInfo.class);
			if (typeInfo != null && typeInfo.use() == JsonTypeInfo.Id.NAME ) {
				JsonSubTypes subTypes = beanElement.getAnnotation(com.fasterxml.jackson.annotation.JsonSubTypes.class);
				if (subTypes != null) {
					Map<String, TypeMirror> subtypesMap = getSubtypeTypeMirrors(beanElement);
					subTypeInfo = new SubTypesInfo(
						typeInfo.include(), 
						typeInfo.property().isEmpty() ? typeInfo.use().getDefaultPropertyName() : typeInfo.property(), 
						subtypesMap);
				}
			}
			
			List<? extends TypeMirror> typeArguments = ((DeclaredType)beanType).getTypeArguments();
			for (TypeMirror typeParamType:typeArguments)
				generateJsonMappers(
						typeParamType,
						packageName,
						typeUtils.asElement(typeParamType).getSimpleName());
			
			if (!Type.isBasicType(typeUtils.erasure(beanType))) {
				generateDeserializer(beanType, packageName, subTypeInfo);
				generateSerializer(beanType, packageName, subTypeInfo);
			}
		}
	}
	
	/**
	 * Iterate over JsonSubTypes.Type annotations and converts them to a map 
	 * @param element
	 * @return  map of JsonSubTypes.Type.name (as String) and JsonSubTypes.Type.value (as TypeMirror)
	 */
	// Retrieving Class<?> from Annotation can be tricky in Annotation processor
	// See https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
	@SuppressWarnings("unchecked")
	private Map<String, TypeMirror> getSubtypeTypeMirrors(Element element) {
		List<? extends AnnotationMirror> subTypes = element.getAnnotationMirrors().stream()
				.filter(am -> am.getAnnotationType().asElement().getSimpleName().toString().equals("JsonSubTypes")) // Get JsonSubType annotation mirror
				.flatMap(am-> am.getElementValues().entrySet().stream()) // do a flat map for JsonSubType element values map entries
				.filter(entry -> entry.getKey().getSimpleName().toString().equals("value")) //find the "value" element of JsonSubType
				.flatMap(entry -> ((List<AnnotationMirror>)entry.getValue().getValue()).stream()) // treat JsonSubType.value() as list of annotation mirrors of JsonSubType.Type
				.collect(Collectors.toList());
		
		return 
			subTypes.stream()
				.collect(
					Collectors.toMap(
						am -> am.getElementValues().entrySet().stream() // create a stream from all element values map entries for a given JsonSubType.Type
							.filter(entry -> entry.getKey().getSimpleName().toString().equals("name")) // find "name" element 
							.map(entry-> (String)entry.getValue().getValue()) // get the value from "name" element, which is a String
							.findFirst()
							.orElse(null),
						am -> am.getElementValues().entrySet().stream() // create a stream from all element values map entries for a given JsonSubType.Type
							.filter(entry -> entry.getKey().getSimpleName().toString().equals("value")) // find "name" element
							.map(entry-> (TypeMirror)entry.getValue().getValue())
							.findFirst()
							.orElse(null)));
		
	}
	
	protected void generateSerializer(TypeMirror beanType, String packageName, SubTypesInfo subTypesInfo) {
	}

	protected void generateDeserializer(TypeMirror beanType, String packageName, SubTypesInfo subTypesInfo) {
	}
}
