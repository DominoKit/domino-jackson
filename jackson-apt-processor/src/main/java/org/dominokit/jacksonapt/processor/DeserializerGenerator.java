package org.dominokit.jacksonapt.processor;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.deserialization.AptDeserializerBuilder;

import javax.lang.model.type.TypeMirror;

import java.io.IOException;
import java.util.Map;

import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.filer;

/**
 * <p>DeserializerGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class DeserializerGenerator {

    /**
     * Generate deserializer for given TypeMirror type. If type is annotated
     * with @JsonSubType and @JsonTypeInfo, deserializers for all subclasses will be 
     * generated too.
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @return fully qualified deserialer name
     */
    public String generate(TypeMirror beanType) {
        String packageName = MoreElements.getPackage(MoreTypes.asTypeElement(beanType)).getQualifiedName().toString();
        String deserializerName = Type.deserializerName(packageName, beanType);

        if (!TypeRegistry.containsDeserializer(Type.stringifyTypeWithPackage(beanType))) {
            try {
                generateSubTypesDeserializers(beanType);
                TypeRegistry.addInActiveGenDeserializer(beanType);
                new AptDeserializerBuilder(packageName, beanType, filer).generate();
                TypeRegistry.registerDeserializer(Type.stringifyTypeWithPackage(beanType), ClassName.bestGuess(deserializerName));
                TypeRegistry.removeInActiveGenDeserializer(beanType);
            } catch (IOException e) {
                throw new DeserializerGenerator.DeserializerGenerationFailedException(beanType.toString());
            }
        }
        return deserializerName;
    }

    private void generateSubTypesDeserializers(TypeMirror beanType) {
    	SubTypesInfo subTypesInfo= Type.getSubTypes(beanType);
        for (Map.Entry<String, TypeMirror> subtypeEntry: subTypesInfo.getSubTypes().entrySet()) {
        	new DeserializerGenerator().generate(subtypeEntry.getValue());
        }
    }
    
    private class DeserializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		DeserializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
