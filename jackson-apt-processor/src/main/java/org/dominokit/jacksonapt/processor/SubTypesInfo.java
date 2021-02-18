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

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.util.Collections;
import java.util.Map;
import javax.lang.model.type.TypeMirror;

/**
 * An aggregator class that holds information about a specific Type that is annotated with {@link
 * com.fasterxml.jackson.annotation.JsonSubTypes} and {@link
 * com.fasterxml.jackson.annotation.JsonTypeInfo}
 */
public class SubTypesInfo {
  private final As include;
  private final String propertyName;
  private final Map<String, TypeMirror> subTypes;

  public SubTypesInfo(As include, String propertyName, Map<String, TypeMirror> subTypes) {
    this.include = include;
    this.propertyName = propertyName;
    this.subTypes = subTypes;
  }

  public static SubTypesInfo emtpy() {
    return new SubTypesInfo(null, null, Collections.emptyMap());
  }

  public boolean hasSubTypes() {
    return subTypes != null && !subTypes.isEmpty();
  }

  public As getInclude() {
    return include;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public Map<String, TypeMirror> getSubTypes() {
    return subTypes;
  }
}
