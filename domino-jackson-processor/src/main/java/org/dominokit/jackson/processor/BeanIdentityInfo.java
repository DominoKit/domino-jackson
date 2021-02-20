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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.util.Optional;
import javax.lang.model.type.TypeMirror;

/**
 * An info class that collects information about the identity of a bean type annotated with {@link
 * JsonIdentityInfo} and {@link JsonIdentityReference}
 */
public class BeanIdentityInfo {

  private final String propertyName;

  private final boolean idABeanProperty;

  private final boolean alwaysAsId;

  private final TypeMirror generator;

  private final Optional<TypeMirror> scope;

  private final Optional<TypeMirror> type;

  BeanIdentityInfo(
      String propertyName, boolean alwaysAsId, TypeMirror generator, Optional<TypeMirror> scope) {
    this.propertyName = propertyName;
    this.alwaysAsId = alwaysAsId;
    this.generator = generator;
    this.scope = scope;
    this.idABeanProperty = true;
    this.type = Optional.empty();
  }

  BeanIdentityInfo(
      String propertyName,
      boolean alwaysAsId,
      TypeMirror generator,
      Optional<TypeMirror> scope,
      Optional<TypeMirror> type) {
    this.propertyName = propertyName;
    this.alwaysAsId = alwaysAsId;
    this.generator = generator;
    this.scope = scope;
    this.idABeanProperty = false;
    this.type = type;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public boolean isIdABeanProperty() {
    return idABeanProperty;
  }

  public boolean isAlwaysAsId() {
    return alwaysAsId;
  }

  public TypeMirror getGenerator() {
    return generator;
  }

  public Optional<TypeMirror> getScope() {
    return scope;
  }

  public Optional<TypeMirror> getType() {
    return type;
  }
}
