package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectReader;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanReaderGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanReaderGenerator extends AbstractBeanMapperGenerator {
    /** {@inheritDoc} */
    @Override
    protected Class<AbstractObjectReader> getSuperClass() {
        return AbstractObjectReader.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanName, beanType))
                .collect(Collectors.toSet());
    }

    /** {@inheritDoc} */
    @Override
    protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
        new DeserializerGenerator().generate(beanType, packageName, beanName);
    }
}
