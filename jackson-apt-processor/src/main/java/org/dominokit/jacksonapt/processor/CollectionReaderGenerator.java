package org.dominokit.jacksonapt.processor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.AbstractObjectReader;

import com.squareup.javapoet.MethodSpec;

public class CollectionReaderGenerator extends AbstractCollectionMapperGenerator {

	@Override
	protected Class<?> getSuperClass() {
		return AbstractObjectReader.class;
	}

	@Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName) {
        return Stream.of(makeNewDeserializerMethod(element, beanName))
                .collect(Collectors.toSet());
    }
	
	@Override
	protected void generateDeserializer(TypeMirror beanType, String packageName, Name beanName) {
		new DeserializerGenerator().generate(beanType, packageName, beanName);
	}
}
