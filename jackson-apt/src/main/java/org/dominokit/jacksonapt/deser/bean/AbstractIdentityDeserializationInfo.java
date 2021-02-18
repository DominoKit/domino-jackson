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
package org.dominokit.jacksonapt.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

/** Abstract AbstractIdentityDeserializationInfo class. */
public abstract class AbstractIdentityDeserializationInfo<T, V>
    extends HasDeserializer<V, JsonDeserializer<V>> implements IdentityDeserializationInfo<T> {

  /** Name of the property holding the identity */
  private final String propertyName;

  /** Type of {@link ObjectIdGenerator} used for generating Object Id */
  private final Class<?> type;

  /** Scope of the Object Id (may be null, to denote global) */
  private final Class<?> scope;

  /**
   * Constructor for AbstractIdentityDeserializationInfo.
   *
   * @param propertyName a {@link java.lang.String} object.
   * @param type a {@link java.lang.Class} object.
   * @param scope a {@link java.lang.Class} object.
   */
  protected AbstractIdentityDeserializationInfo(
      String propertyName, Class<?> type, Class<?> scope) {
    this.propertyName = propertyName;
    this.type = type;
    this.scope = scope;
  }

  /** {@inheritDoc} */
  @Override
  public final String getPropertyName() {
    return propertyName;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isProperty() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public IdKey newIdKey(Object id) {
    return new IdKey(type, scope, id);
  }

  /** {@inheritDoc} */
  @Override
  public final Object readId(JsonReader reader, JsonDeserializationContext ctx) {
    return getDeserializer().deserialize(reader, ctx);
  }
}
