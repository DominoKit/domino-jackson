package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.*;

import javax.lang.model.element.*;

import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.*;

/**
 * <p>Abstract AbstractBeanMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractBeanMapperGenerator extends AbstractMapperGenerator {
    MethodSpec makeNewDeserializerMethod(Element element, Name beanName) {
        return MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
                        ClassName.get(getElementType(element))))
                .addStatement("return new " + beanName + "BeanJsonDeserializerImpl()")
                .build();
    }

    MethodSpec makeNewSerializerMethod(Name beanName) {
        return MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
                .addStatement("return new " + beanName + "BeanJsonSerializerImpl()")
                .build();
    }
}
