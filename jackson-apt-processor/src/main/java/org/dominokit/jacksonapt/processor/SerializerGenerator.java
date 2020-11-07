package org.dominokit.jacksonapt.processor;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.serialization.AptSerializerBuilder;

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
     * @return fully-qualified serialer name
     */
    public String generate(TypeMirror beanType) {
        String packageName = MoreElements.getPackage(MoreTypes.asTypeElement(beanType)).getQualifiedName().toString();
    	String serializerName = Type.serializerName(packageName, beanType);
        if (!TypeRegistry.containsSerializer(Type.stringifyTypeWithPackage(beanType))) {
            try {
            	generateSubTypeSerializers(beanType);
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
    
    private void generateSubTypeSerializers(TypeMirror beanType) {
    	SubTypesInfo subTypesInfo= Type.getSubTypes(beanType);
        for (Map.Entry<String, TypeMirror> subtypeEntry: subTypesInfo.getSubTypes().entrySet()) {
			 new SerializerGenerator().generate(subtypeEntry.getValue());
        }
    }
    

    private class SerializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		SerializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
