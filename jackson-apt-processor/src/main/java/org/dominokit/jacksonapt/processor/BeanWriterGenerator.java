package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectWriter;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanWriterGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanWriterGenerator extends AbstractMapperGenerator {
    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
	@Override
    protected Class<AbstractObjectWriter> getSuperClass() {
        return AbstractObjectWriter.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror beanType) {
        return Stream.of(makeNewSerializerMethod(beanType))
                .collect(Collectors.toList());
    }
    
    @Override
	protected void generateSerializer(TypeMirror beanType) {
		new SerializerGenerator().generate(beanType);
	}
}
