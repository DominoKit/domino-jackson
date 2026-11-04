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
package org.dominokit.jackson.processor.deserialization;

import static org.dominokit.jackson.processor.AbstractMapperProcessor.typeUtils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

/**
 * The container class as its own type parameters, not as the first field that happened to use it.
 * {@code ByName<Integer>} and {@code ByName<String>} share one generated deserializer {@code
 * ByNameBeanJsonDeserializerImpl<V>}.
 */
final class DeclaredContainer {

  private final TypeMirror open;
  private final TypeName declared;
  private final List<TypeVariableName> typeVariables;

  DeclaredContainer(TypeElement element) {
    this.open = openType(element);
    this.declared = TypeName.get(open);
    this.typeVariables =
        element.getTypeParameters().stream()
            .map(TypeVariableName::get)
            .collect(Collectors.toList());
  }

  TypeMirror getOpen() {
    return open;
  }

  TypeName getDeclared() {
    return declared;
  }

  TypeName parameterize(ClassName generated) {
    if (typeVariables.isEmpty()) {
      return generated;
    }
    return ParameterizedTypeName.get(generated, typeVariables.toArray(TypeName[]::new));
  }

  void addTypeVariables(TypeSpec.Builder type) {
    typeVariables.forEach(type::addTypeVariable);
  }

  void addTypeVariables(MethodSpec.Builder method) {
    typeVariables.forEach(method::addTypeVariable);
  }

  private static TypeMirror openType(TypeElement element) {
    if (element.getTypeParameters().isEmpty()) {
      return element.asType();
    }
    TypeMirror[] arguments =
        element.getTypeParameters().stream()
            .map(TypeParameterElement::asType)
            .toArray(TypeMirror[]::new);
    return typeUtils.getDeclaredType(element, arguments);
  }
}
