package org.dominokit.jacksonapt.processor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.AbstractObjectMapper;

import com.squareup.javapoet.MethodSpec;

public class CollectionMapperGenerator extends AbstractCollectionMapperGenerator {
	@Override
	protected Class<?> getSuperClass() {
		return AbstractObjectMapper.class;
	}
	
	@Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanName), makeNewSerializerMethod(element, beanName))
                .collect(Collectors.toList());
    }

	@Override
	protected void generateSerializer(TypeMirror beanType, String packageName, Name beanName) {
		new SerializerGenerator().generate(beanType, packageName, beanName);
	}
	
	@Override
	protected void generateDeserializer(TypeMirror beanType, String packageName, Name beanName) {
		new DeserializerGenerator().generate(beanType, packageName, beanName);
	}
}
