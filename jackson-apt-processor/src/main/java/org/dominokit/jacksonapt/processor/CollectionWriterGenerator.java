package org.dominokit.jacksonapt.processor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.AbstractObjectWriter;

import com.squareup.javapoet.MethodSpec;

public class CollectionWriterGenerator extends AbstractCollectionMapperGenerator {

	@Override
	protected Class<?> getSuperClass() {
		return AbstractObjectWriter.class;
	}

	@Override
	protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName) {
		 return Stream.of(makeNewSerializerMethod(element, beanName))
	                .collect(Collectors.toList());
	}

	@Override
	protected void generateSerializer(TypeMirror beanType, String packageName, Name beanName) {
		new SerializerGenerator().generate(beanType, packageName, beanName);
	}
}
