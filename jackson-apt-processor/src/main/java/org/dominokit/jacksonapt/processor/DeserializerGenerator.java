package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.deserialization.AptDeserializerBuilder;

import javax.lang.model.type.TypeMirror;
import java.io.IOException;

import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.filer;

/**
 * <p>DeserializerGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class DeserializerGenerator {

    /**
     * <p>generate.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public String generate(TypeMirror beanType, String packageName) {
        String generatedClassName = Type.deserializerName(packageName, beanType);
        if (!TypeRegistry.containsDeserializer(beanType.toString())) {
            try {
                new AptDeserializerBuilder(beanType, filer).generate(packageName);
                TypeRegistry.registerDeserializer(beanType.toString(), ClassName.bestGuess(generatedClassName));
            } catch (IOException e) {
                throw new DeserializerGenerator.DeserializerGenerationFailedException(beanType.toString());
            }
        }
        return generatedClassName;
    }

    private class DeserializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		DeserializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
