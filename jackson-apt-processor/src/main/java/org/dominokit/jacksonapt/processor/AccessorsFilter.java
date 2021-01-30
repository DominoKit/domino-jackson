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
package org.dominokit.jacksonapt.processor;

import static java.util.Objects.isNull;
import static org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator.AccessorInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class AccessorsFilter {

  protected Types typeUtils;

  public AccessorsFilter(Types typeUtils) {
    this.typeUtils = typeUtils;
  }

  protected Set<AccessorInfo> getAccessors(TypeMirror beanType) {
    TypeElement element = (TypeElement) typeUtils.asElement(beanType);
    TypeMirror superclass = element.getSuperclass();
    if (superclass.getKind().equals(TypeKind.NONE)) {
      return new HashSet<>();
    }

    Set<AccessorInfo> collect =
        ((TypeElement) ObjectMapperProcessor.typeUtils.asElement(beanType))
            .getEnclosedElements().stream()
                .filter(
                    e ->
                        ElementKind.METHOD.equals(e.getKind())
                            && !e.getModifiers().contains(Modifier.STATIC)
                            && e.getModifiers().contains(Modifier.PUBLIC))
                .map(e -> new AccessorInfo(Optional.of((ExecutableElement) e)))
                .collect(Collectors.toSet());
    collect.addAll(getAccessors(superclass));
    return collect;
  }

  protected String getPropertyName(Element field) {
    JsonProperty annotation = field.getAnnotation(JsonProperty.class);
    if (isNull(annotation) || JsonProperty.USE_DEFAULT_NAME.equals(annotation.value())) {
      return field.getSimpleName().toString();
    } else {
      return annotation.value();
    }
  }
}
