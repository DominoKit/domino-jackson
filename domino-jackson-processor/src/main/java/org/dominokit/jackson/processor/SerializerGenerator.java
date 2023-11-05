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

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import java.io.IOException;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import org.dominokit.jackson.processor.serialization.AptSerializerBuilder;

/**
 * SerializerGenerator class.
 *
 * <p>This class will generate a Serializer to write a json.
 */
public class SerializerGenerator {

  /**
   * Generate serializer for given TypeMirror type. If type is annotated with @JsonSubType
   * and @JsonTypeInfo, serializers for all subclasses will be generated too.
   *
   * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
   * @return fully-qualified serialer name
   */
  public String generate(TypeMirror beanType) {
    String packageName =
        MoreElements.getPackage(MoreTypes.asTypeElement(beanType)).getQualifiedName().toString();
    String serializerName = Type.serializerName(packageName, beanType);
    if (!GeneratedMappersRegistry.INSTANCE.hasTypeToken(
            GeneratedMappersRegistry.Category.SERIALIZER, beanType)
        && !TypeRegistry.containsSerializer(Type.stringifyTypeWithPackage(beanType))) {
      GeneratedMappersRegistry.INSTANCE.addTypeToken(
          GeneratedMappersRegistry.Category.SERIALIZER, beanType);
      try {
        generateSubTypeSerializers(beanType);
        TypeRegistry.addInActiveGenSerializer(beanType);
        new AptSerializerBuilder(packageName, beanType, ObjectMapperProcessor.filer).generate();
        TypeRegistry.registerSerializer(
            Type.stringifyTypeWithPackage(beanType), ClassName.bestGuess(serializerName));
        TypeRegistry.removeInActiveGenSerializer(beanType);
      } catch (IOException e) {
        throw new SerializerGenerationFailedException(beanType.toString());
      }
    }
    return serializerName;
  }

  private void generateSubTypeSerializers(TypeMirror beanType) {
    SubTypesInfo subTypesInfo = Type.getSubTypes(beanType);
    for (Map.Entry<String, TypeMirror> subtypeEntry : subTypesInfo.getSubTypes().entrySet()) {
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
