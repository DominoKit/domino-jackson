package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectReader;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanReaderGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanReaderGenerator extends AbstractMapperGenerator {
    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
	@Override
    protected Class<AbstractObjectReader> getSuperClass() {
        return AbstractObjectReader.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanType))
                .collect(Collectors.toSet());
    }
    
    @Override
	protected void generateDeserializer(TypeMirror beanType) {
		new DeserializerGenerator().generate(beanType);
	}
}
