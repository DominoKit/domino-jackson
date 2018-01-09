package com.progressoft.brix.domino.jacksonapt.processor;

import com.progressoft.brix.domino.jacksonapt.processor.deserialization.AptDeserializerBuilder;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

import static com.progressoft.brix.domino.jacksonapt.processor.ObjectMapperProcessor.filer;

public class DeserializerGenerator {

    public String generate(TypeMirror beanType, String packageName, Name beanName) {
        String generatedClassName = Type.deserializerName(packageName, beanName.toString());
        if (!TypeRegistry.containsDeserializer(beanType.toString())) {
            try {
                new AptDeserializerBuilder(beanType, filer)
                        .generate(beanName, packageName);
                TypeRegistry.registerDeserializer(beanType.toString(), ClassName.bestGuess(generatedClassName));
            } catch (IOException e) {
                throw new DeserializerGenerator.DeserializerGenerationFailedException(beanType.toString());
            }
        }
        return generatedClassName;
    }

    private class DeserializerGenerationFailedException extends RuntimeException {
        DeserializerGenerationFailedException(String type) {
            super(type);
        }
    }
}