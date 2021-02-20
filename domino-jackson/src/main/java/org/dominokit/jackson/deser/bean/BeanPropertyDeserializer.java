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

package org.dominokit.jackson.deser.bean;

import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.stream.JsonReader;

/** Deserializes a bean's property */
public abstract class BeanPropertyDeserializer<T, V>
    extends HasDeserializerAndParameters<V, JsonDeserializer<V>> {

  /**
   * Deserializes the property defined for this instance.
   *
   * @param reader a {@link JsonReader} reader
   * @param bean bean to set the deserialized property to
   * @param ctx a {@link JsonDeserializationContext} context of the deserialization process
   */
  public void deserialize(JsonReader reader, T bean, JsonDeserializationContext ctx) {
    setValue(bean, deserialize(reader, ctx), ctx);
  }

  /**
   * setValue
   *
   * @param bean a T object.
   * @param value a V object.
   * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
   */
  public abstract void setValue(T bean, V value, JsonDeserializationContext ctx);
}
