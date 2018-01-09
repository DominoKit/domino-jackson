package com.progressoft.brix.domino.jacksonapt.processor;

import com.progressoft.brix.domino.jacksonapt.processor.serialization.AptSerializerBuilder;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

public class SerializerGenerator {

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