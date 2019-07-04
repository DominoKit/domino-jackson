package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.MethodSpec;
import org.dominokit.jacksonapt.AbstractObjectMapper;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>BeanMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class BeanMapperGenerator extends AbstractMapperGenerator {
    @SuppressWarnings("rawtypes")
	@Override
    protected Class<AbstractObjectMapper> getSuperClass() {
        return AbstractObjectMapper.class;
    }

    /** {@inheritDoc} */
    @Override
    protected Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror beanType) {
        return Stream.of(makeNewDeserializerMethod(element, beanType), makeNewSerializerMethod(beanType))
                .collect(Collectors.toList());
    }

    @Override
   	protected void generateSerializer(TypeMirror beanType, String packageName) {
   		new SerializerGenerator().generate(packageName, beanType);
   	}
   	
   	@Override
   	protected void generateDeserializer(TypeMirror beanType, String packageName) {
   		new DeserializerGenerator().generate(packageName, beanType);
   	}
}
