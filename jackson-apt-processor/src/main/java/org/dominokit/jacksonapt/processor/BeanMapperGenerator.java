package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectMapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanMapperGenerator extends AbstractBeanMapperGenerator {
    /** {@inheritDoc} */
    @Override
    protected Class<AbstractObjectMapper> getSuperClass() {
        return AbstractObjectMapper.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanName, beanType), makeNewSerializerMethod(beanName, beanType))
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
        new DeserializerGenerator().generate(beanType, packageName, beanName);
        new SerializerGenerator().generate(beanType, packageName, beanName);
    }
}
