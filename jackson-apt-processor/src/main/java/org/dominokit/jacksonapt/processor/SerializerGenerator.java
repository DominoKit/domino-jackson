package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.serialization.AptSerializerBuilder;

import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

/**
 * <p>SerializerGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class SerializerGenerator {

    /**
     * <p>generate.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param beanName a {@link javax.lang.model.element.Name} object.
     * @return a {@link java.lang.String} object.
     */
    public String generate(TypeMirror beanType, String packageName, Name beanName) {
        String generatedClassName = Type.serializerName(packageName, beanName.toString());
        if (!TypeRegistry.containsSerializer(beanType.toString())) {
            try {
                new AptSerializerBuilder(beanType, ObjectMapperProcessor.filer)
                        .generate(beanName, packageName);
                TypeRegistry.registerSerializer(beanType.toString(), ClassName.bestGuess(generatedClassName));
            } catch (IOException e) {
                throw new SerializerGenerationFailedException(beanType.toString());
            }
        }
        return generatedClassName;
    }

    private class SerializerGenerationFailedException extends RuntimeException {
        SerializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
