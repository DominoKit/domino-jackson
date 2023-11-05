/*
 * Copyright © 2019 Dominokit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dominokit.jackson.processor;

import static org.dominokit.jackson.processor.ObjectMapperProcessor.filer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import java.io.IOException;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import org.dominokit.jackson.processor.deserialization.AptDeserializerBuilder;

/**
 * DeserializerGenerator class.
 *
 * <p>This class will generate a Deserializer to read a json.
 */
public class DeserializerGenerator {

  /**
   * Generate deserializer for given TypeMirror type. If type is annotated with @JsonSubType and
   * {@link JsonTypeInfo}, deserializers for all subclasses will be generated too.
   *
   * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
   * @return fully qualified deserialer name
   */
  public String generate(TypeMirror beanType) {
    String packageName =
        MoreElements.getPackage(MoreTypes.asTypeElement(beanType)).getQualifiedName().toString();
    String deserializerName = Type.deserializerName(packageName, beanType);

    if (!GeneratedMappersRegistry.INSTANCE.hasTypeToken(
            GeneratedMappersRegistry.Category.DESERIALIZER, beanType)
        && !TypeRegistry.containsDeserializer(Type.stringifyTypeWithPackage(beanType))) {
      GeneratedMappersRegistry.INSTANCE.addTypeToken(
          GeneratedMappersRegistry.Category.DESERIALIZER, beanType);
      try {
        generateSubTypesDeserializers(beanType);
        TypeRegistry.addInActiveGenDeserializer(beanType);
        new AptDeserializerBuilder(packageName, beanType, filer).generate();
        TypeRegistry.registerDeserializer(
            Type.stringifyTypeWithPackage(beanType), ClassName.bestGuess(deserializerName));
        TypeRegistry.removeInActiveGenDeserializer(beanType);
      } catch (IOException e) {
        throw new DeserializerGenerator.DeserializerGenerationFailedException(
            beanType.toString(), e);
      }
    }
    return deserializerName;
  }

  private void generateSubTypesDeserializers(TypeMirror beanType) {
    SubTypesInfo subTypesInfo = Type.getSubTypes(beanType);
    for (Map.Entry<String, TypeMirror> subtypeEntry : subTypesInfo.getSubTypes().entrySet()) {
      new DeserializerGenerator().generate(subtypeEntry.getValue());
    }
  }

  private class DeserializerGenerationFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DeserializerGenerationFailedException(String message, Throwable cause) {
      super(message, cause);
    }

    DeserializerGenerationFailedException(String type) {
      super(type);
    }
  }
}
