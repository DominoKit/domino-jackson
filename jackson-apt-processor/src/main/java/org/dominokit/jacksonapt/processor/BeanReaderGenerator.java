package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectReader;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanReaderGenerator extends AbstractBeanMapperGenerator {
    @Override
    protected Class<AbstractObjectReader> getSuperClass() {
        return AbstractObjectReader.class;
    }

    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName) {
        return Stream.of(makeNewDeserializerMethod(element, beanName))
                .collect(Collectors.toSet());
    }

    @Override
    protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
        new DeserializerGenerator().generate(beanType, packageName, beanName);
    }
}
