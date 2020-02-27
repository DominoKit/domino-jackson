package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.deserialization.AptDeserializerBuilder;

import javax.lang.model.type.DeclaredType;
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
     * @param packageName a {@link java.lang.String} object.
     * @return fully qualified deserialer name
     */
    public String generate(String packageName, TypeMirror beanType) {
        String deserializerName = Type.deserializerName(packageName, beanType);

        if (!TypeRegistry.containsDeserializer(Type.stringifyTypeWithPackage(beanType))) {
            try {
            	generateSubTypesDeserializers(beanType, packageName);
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

    private void generateSubTypesDeserializers(TypeMirror beanType, String packageName) {
    	SubTypesInfo subTypesInfo= Type.getSubTypes(beanType);
        for (Map.Entry<String, TypeMirror> subtypeEntry: subTypesInfo.getSubTypes().entrySet()) {
        	// @JsonTypeInfo and @JsonSubTypes must be used only on non generic types
        	// This limitation is imposed by the the the java.land.model API, which does 
        	// not allow to retrieve interface(s) for given class (i.e. using getSupperClass()). 
        	// That limits the possibilities to inspect interface type parameters. Even
        	// beanType is TypeMirror for base interface (i.e. annotated with @JsonTypeInfo and @JsonSubTypes)
        	// with specified type arguments, we can not match them against the 
        	// type parameters of the subtype retrieved by @JsonSubTypes.
        	if (
        			!((DeclaredType)beanType).getTypeArguments().isEmpty()
        			|| !((DeclaredType)((DeclaredType)subtypeEntry.getValue()).asElement().asType()).getTypeArguments().isEmpty())
        		throw new RuntimeException("@JsonSubTypes and &JsonTypeInfo can be used only on non-generic Java types");
			 
        	new DeserializerGenerator().generate(packageName, subtypeEntry.getValue());
        }
    }
    
    private class DeserializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		DeserializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
