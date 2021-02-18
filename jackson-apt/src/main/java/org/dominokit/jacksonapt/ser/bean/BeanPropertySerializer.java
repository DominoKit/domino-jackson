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

package org.dominokit.jacksonapt.ser.bean;

import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/** Serializes a bean's property */
public abstract class BeanPropertySerializer<T, V> extends HasSerializer<V, JsonSerializer<V>> {

  protected final String propertyName;

  private JsonSerializerParameters parameters;

  /**
   * Constructor for BeanPropertySerializer.
   *
   * @param propertyName a {@link java.lang.String} object.
   */
  protected BeanPropertySerializer(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
   * Getter for the field <code>parameters</code>.
   *
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  protected JsonSerializerParameters getParameters() {
    if (null == parameters) {
      parameters = newParameters();
    }
    return parameters;
  }

  /**
   * newParameters
   *
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  protected JsonSerializerParameters newParameters() {
    return JacksonContextProvider.get().defaultSerializerParameters();
  }

  /**
   * Getter for the field <code>propertyName</code>.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Serializes the property name
   *
   * @param writer writer
   * @param bean bean containing the property to serialize
   * @param ctx context of the serialization process
   */
  public void serializePropertyName(JsonWriter writer, T bean, JsonSerializationContext ctx) {
    writer.unescapeName(propertyName);
  }

  /**
   * getValue
   *
   * @param bean bean containing the property to serialize
   * @param ctx context of the serialization process
   * @return the property's value
   */
  public abstract V getValue(T bean, JsonSerializationContext ctx);

  /**
   * Serializes the property defined for this instance.
   *
   * @param writer writer
   * @param bean bean containing the property to serialize
   * @param ctx context of the serialization process
   */
  public void serialize(JsonWriter writer, T bean, JsonSerializationContext ctx) {
    getSerializer().serialize(writer, getValue(bean, ctx), ctx, getParameters());
  }
}
