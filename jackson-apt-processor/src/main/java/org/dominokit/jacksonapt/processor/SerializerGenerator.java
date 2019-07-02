package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.serialization.AptSerializerBuilder;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;

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
     * <p>generate.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public String generate(TypeMirror beanType, String packageName) {
    	String beanTypeStr = Type.stringifyType(beanType);
    	String generatedClassName = Type.serializerName(packageName, beanType);
    	
        if (!TypeRegistry.containsSerializer(beanTypeStr)) {
            try {
            	generateSubTypeSerializers(beanType);
            	
                new AptSerializerBuilder(beanType, ObjectMapperProcessor.filer).generate(packageName);
                TypeRegistry.registerSerializer(beanTypeStr, ClassName.bestGuess(generatedClassName));
            } catch (IOException e) {
                throw new SerializerGenerationFailedException(beanType.toString());
            }
        }
        return generatedClassName;
    }
    
    private void generateSubTypeSerializers(TypeMirror beanType) {
    	SubTypesInfo subTypesInfo= Type.getSubTypes(beanType);
        for (Map.Entry<String, TypeMirror> subtypeEntry: subTypesInfo.getSubTypes().entrySet()) {
        	// @JsonTypeInfo and @JsonSubTypes must be used only on non generic types
        	// This limitation is imposed by the the the java.land.model API, which does 
        	// not allow to get interface(s) for given class. That means even if we have 
        	// beanType for base interface (i.e. annotated with @JsonTypeInfo and @JsonSubTypes)
        	// with specified type arguments, we can not match them against the 
        	// type parameters of the subtype retrieved by @JsonSubTypess
        	if (
        			!((DeclaredType)beanType).getTypeArguments().isEmpty()
        			|| !((DeclaredType)((DeclaredType)subtypeEntry.getValue()).asElement().asType()).getTypeArguments().isEmpty())
        		throw new RuntimeException("@JsonSubTypes and &JsonTypeInfo can be used only on non-generic Java types");
        	
			 new SerializerGenerator().generate(
					subtypeEntry.getValue(), 
					elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(subtypeEntry.getValue()))).toString());
        }
    }
    

    private class SerializerGenerationFailedException extends RuntimeException {

		SerializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
