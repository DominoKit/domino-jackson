package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.serialization.AptSerializerBuilder;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;


import java.io.IOException;
import java.util.Map;

/**
 * <p>SerializerGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class SerializerGenerator {

	/**
     * Generate serializer for given TypeMirror type. If type is annotated
     * with @JsonSubType and @JsonTypeInfo, serializers for all subclasses will be 
     * generated too.
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @return fully-qualified serialer name
     */
    public String generate(String packageName, TypeMirror beanType) {
    	String serializerName = Type.serializerName(packageName, beanType);
    	
        if (!TypeRegistry.containsSerializer(Type.stringifyTypeWithPackage(beanType))) {
            try {
            	generateSubTypeSerializers(packageName, beanType);
                TypeRegistry.addInActiveGenSerializer(beanType);
                new AptSerializerBuilder(packageName, beanType, ObjectMapperProcessor.filer).generate();
                TypeRegistry.registerSerializer(Type.stringifyTypeWithPackage(beanType), ClassName.bestGuess(serializerName));
                TypeRegistry.removeInActiveGenSerializer(beanType);
            } catch (IOException e) {
                throw new SerializerGenerationFailedException(beanType.toString());
            }
        }
        return serializerName;
    }
    
    private void generateSubTypeSerializers(String packageName, TypeMirror beanType) {
    	SubTypesInfo subTypesInfo= Type.getSubTypes(beanType);
        for (Map.Entry<String, TypeMirror> subtypeEntry: subTypesInfo.getSubTypes().entrySet()) {
			 new SerializerGenerator().generate(packageName, subtypeEntry.getValue());
        }
    }
    

    private class SerializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		SerializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
