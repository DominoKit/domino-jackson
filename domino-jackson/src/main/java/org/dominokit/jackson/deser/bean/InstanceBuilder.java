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

import java.util.Map;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.stream.JsonReader;

/** InstanceBuilder interface. */
public interface InstanceBuilder<T> {

  /**
   * newInstance
   *
   * @param reader a {@link org.dominokit.jackson.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
   * @param bufferedProperties a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @param bufferedPropertiesValues a {@link java.util.Map} object.
   * @return a {@link org.dominokit.jackson.deser.bean.Instance} object.
   */
  Instance<T> newInstance(
      JsonReader reader,
      JsonDeserializationContext ctx,
      JsonDeserializerParameters params,
      Map<String, String> bufferedProperties,
      Map<String, Object> bufferedPropertiesValues);

  /**
   * getParametersDeserializer
   *
   * @return a {@link org.dominokit.jackson.deser.bean.MapLike} object.
   */
  MapLike<HasDeserializerAndParameters> getParametersDeserializer();
}
