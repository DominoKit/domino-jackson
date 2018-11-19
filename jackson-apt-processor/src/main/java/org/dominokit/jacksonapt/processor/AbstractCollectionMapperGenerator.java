package org.dominokit.jacksonapt.processor;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.DEFAULT_WILDCARD;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.processor.deserialization.FieldDeserializersChainBuilder;
import org.dominokit.jacksonapt.processor.serialization.FieldSerializerChainBuilder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;


public abstract class AbstractCollectionMapperGenerator extends AbstractMapperGenerator {
	protected MethodSpec makeNewDeserializerMethod(Element element, Name beanName) {
		return MethodSpec.methodBuilder("newDeserializer")
				.addModifiers(Modifier.PROTECTED)
				.addAnnotation(Override.class)
				.returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
						ClassName.get(getElementType(element))))
				.addCode(
						CodeBlock.builder()
						.add("return ")
						.add(new FieldDeserializersChainBuilder(getElementType(element)).getInstance(getElementType(element)))
						.add(";")
						.build())
				.build();
	}

	protected MethodSpec makeNewSerializerMethod(Element element, Name beanName) {
		return MethodSpec.methodBuilder("newSerializer")
				.addModifiers(Modifier.PROTECTED)
				.addAnnotation(Override.class)
				.returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
				.addCode(
						CodeBlock.builder()
						.add("return ")
						.add(new FieldSerializerChainBuilder(getElementType(element)).getInstance(getElementType(element)))
						.add(";")
						.build())
				.build();
	}


	@Override
	protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
		List<? extends TypeMirror> typeArguments = ((DeclaredType)beanType).getTypeArguments();
		for (TypeMirror typeParamType:typeArguments){
			if(Type.isCollection(typeParamType) || Type.isMap(typeParamType))
				generateJsonMappers(typeParamType, packageName, typeUtils.asElement(typeParamType).getSimpleName());
			else if (Type.isBasicType(typeParamType)) 
				//In case of basic type, no need to generate serializer
				return;
			else {
				Element typeParamElement = typeUtils.asElement(typeParamType);
				String typeParamPackageName = elementUtils.getPackageOf(typeParamElement).getQualifiedName().toString();

				generateDeserializer(typeParamType, typeParamPackageName, typeParamElement.getSimpleName());
				generateSerializer(typeParamType, typeParamPackageName, typeParamElement.getSimpleName());
			}
		}
	}

	protected void generateSerializer(TypeMirror beanType, String packageName, Name beanName) {
	}

	protected void generateDeserializer(TypeMirror beanType, String packageName, Name beanName) {
	}
}
