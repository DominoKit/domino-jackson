/*
 * Copyright 2014 Nicolas Morel
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

import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.stream.JsonReader;

/** Lazy initialize a {@link org.dominokit.jacksonapt.JsonDeserializer} */
public abstract class HasDeserializerAndParameters<V, S extends JsonDeserializer<V>>
    extends HasDeserializer<V, S> {

  private JsonDeserializerParameters parameters;

  /**
   * Getter for the field <code>parameters</code>.
   *
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  protected JsonDeserializerParameters getParameters() {
    if (null == parameters) {
      parameters = newParameters();
    }
    return parameters;
  }

  /**
   * newParameters
   *
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  protected JsonDeserializerParameters newParameters() {
    return JacksonContextProvider.get().defaultDeserializerParameters();
  }

  /**
   * Deserializes the property defined for this instance.
   *
   * @param reader a {@link JsonReader} reader
   * @param ctx a {@link JsonDeserializationContext} context of the deserialization process
   * @return a V object.
   */
  public V deserialize(JsonReader reader, JsonDeserializationContext ctx) {
    return getDeserializer().deserialize(reader, ctx, getParameters());
  }
}
