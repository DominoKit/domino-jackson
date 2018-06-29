package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanWriterGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanWriterGenerator extends AbstractBeanMapperGenerator {
    /** {@inheritDoc} */
    @Override
    protected Class<AbstractObjectWriter> getSuperClass() {
        return AbstractObjectWriter.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, Name beanName) {
        return Stream.of(makeNewSerializerMethod(beanName))
                .collect(Collectors.toSet());
    }

    /** {@inheritDoc} */
    @Override
    protected void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName) {
        new SerializerGenerator().generate(beanType, packageName, beanName);
    }
}
