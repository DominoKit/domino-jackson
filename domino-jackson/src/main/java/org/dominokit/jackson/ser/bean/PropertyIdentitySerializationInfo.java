/*
 * Copyright 2013 Nicolas Morel
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

package org.dominokit.jackson.ser.bean;

import org.dominokit.jackson.JsonSerializationContext;

/** Contains identity information for serialization process. */
public abstract class PropertyIdentitySerializationInfo<T, V> extends BeanPropertySerializer<T, V>
    implements IdentitySerializationInfo<T> {

  /** if we always serialize the bean as an id even for the first encounter. */
  private final boolean alwaysAsId;

  /**
   * Constructor for PropertyIdentitySerializationInfo.
   *
   * @param alwaysAsId a boolean.
   * @param propertyName a {@link java.lang.String} object.
   */
  public PropertyIdentitySerializationInfo(boolean alwaysAsId, String propertyName) {
    super(propertyName);
    this.alwaysAsId = alwaysAsId;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isAlwaysAsId() {
    return alwaysAsId;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isProperty() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public ObjectIdSerializer<?> getObjectId(T bean, JsonSerializationContext ctx) {
    return new ObjectIdSerializer(getValue(bean, ctx), getSerializer());
  }
}
