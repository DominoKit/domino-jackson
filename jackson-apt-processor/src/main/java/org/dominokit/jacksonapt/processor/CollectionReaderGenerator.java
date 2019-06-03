package org.dominokit.jacksonapt.processor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.AbstractObjectReader;

import com.squareup.javapoet.MethodSpec;

public class CollectionReaderGenerator extends AbstractMapperGenerator {
	@Override
	protected Class<?> getSuperClass() {
		return AbstractObjectReader.class;
	}

	@Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanType))
                .collect(Collectors.toList());
    }
	
	@Override
	protected void generateDeserializer(TypeMirror beanType, String packageName, SubTypesInfo subTypesInfo) {
		new DeserializerGenerator().generate(beanType, packageName, subTypesInfo);
	}
}
