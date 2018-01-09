package com.progressoft.brix.domino.jacksonapt.processor;

import com.progressoft.brix.domino.jacksonapt.AbstractObjectReader;
import com.progressoft.brix.domino.jacksonapt.AbstractObjectWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanWriterGenerator extends AbstractBeanMapperGenerator {
    @Override
    protected Class<AbstractObjectWriter> getSuperClass() {
        return AbstractObjectWriter.class;
    }

    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName) {
        return Stream.of(makeNewSerializerMethod(beanName))
                .collect(Collectors.toSet());
    }

    @Override
    protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
        new SerializerGenerator().generate(beanType, packageName, beanName);
    }
}
