package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.processor.deserialization.AptDeserializerBuilder;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import java.io.IOException;
import java.util.Map;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;
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
        String beanTypeStr = Type.stringifyType(beanType);
        String generatedClassName = Type.deserializerName(packageName, beanType);
        
        if (!TypeRegistry.containsDeserializer(beanTypeStr)) {
            try {
            	generateSubTypesDeserializers(beanType);
                new AptDeserializerBuilder(beanType, filer).generate(packageName);
                TypeRegistry.registerDeserializer(beanTypeStr, ClassName.bestGuess(generatedClassName));
            } catch (IOException e) {
                throw new DeserializerGenerator.DeserializerGenerationFailedException(beanType.toString());
            }
        }
        return generatedClassName;
    }

    private void generateSubTypesDeserializers(TypeMirror beanType) {
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
			 
        	new DeserializerGenerator().generate(
					subtypeEntry.getValue(),
					elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(subtypeEntry.getValue()))).toString());
        }
    }
    
    private class DeserializerGenerationFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		DeserializerGenerationFailedException(String type) {
            super(type);
        }
    }
}
