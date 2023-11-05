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

import com.squareup.javapoet.MethodSpec;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.dominokit.jackson.AbstractObjectMapper;

/**
 * BeanMapperGenerator class. This class will generate a mapper that can Read a json or write a
 * json.
 */
public class BeanMapperGenerator extends AbstractMapperGenerator {
  @SuppressWarnings("rawtypes")
  @Override
  protected Class<AbstractObjectMapper> getSuperClass() {
    return AbstractObjectMapper.class;
  }

  /** {@inheritDoc} */
  @Override
  protected Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror beanType) {
    return Stream.of(
            makeNewDeserializerMethod(element, beanType), makeNewSerializerMethod(beanType))
        .collect(Collectors.toList());
  }

  /**
   * This method will generate a Serializer -writer- implementation for the target bean type.
   *
   * @param beanType the {@link TypeMirror} of the target bean.
   */
  @Override
  protected void generateSerializer(TypeMirror beanType) {
    new SerializerGenerator().generate(beanType);
  }

  /**
   * This method will generate a Deserializer -reader- implementation for the target bean type.
   *
   * @param beanType the {@link TypeMirror} of the target bean.
   */
  @Override
  protected void generateDeserializer(TypeMirror beanType) {
    new DeserializerGenerator().generate(beanType);
  }

  @Override
  protected GeneratedMappersRegistry.Category getCategory() {
    return GeneratedMappersRegistry.Category.MAPPER;
  }
}
