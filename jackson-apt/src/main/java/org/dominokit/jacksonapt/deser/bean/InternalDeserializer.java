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

import java.util.Map;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.stream.JsonReader;

/** Interface hiding the actual implementation doing the bean deserialization. */
interface InternalDeserializer<T, S extends JsonDeserializer<T>> {

  /**
   * getDeserializer
   *
   * @return a S object.
   */
  S getDeserializer();

  /**
   * deserializeInline
   *
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   * @param identityInfo a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo}
   *     object.
   * @param typeInfo a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
   * @param typeInformation a {@link java.lang.String} object.
   * @param typeInformation a {@link java.lang.String} object.
   * @param bufferedProperties a {@link java.util.Map} object.
   * @return a T object.
   */
  T deserializeInline(
      JsonReader reader,
      JsonDeserializationContext ctx,
      JsonDeserializerParameters params,
      IdentityDeserializationInfo identityInfo,
      TypeDeserializationInfo typeInfo,
      String typeInformation,
      Map<String, String> bufferedProperties);

  /**
   * deserializeWrapped
   *
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   * @param identityInfo a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo}
   *     object.
   * @param typeInfo a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
   * @param typeInformation a {@link java.lang.String} object.
   * @param typeInformation a {@link java.lang.String} object.
   * @return a T object.
   */
  T deserializeWrapped(
      JsonReader reader,
      JsonDeserializationContext ctx,
      JsonDeserializerParameters params,
      IdentityDeserializationInfo identityInfo,
      TypeDeserializationInfo typeInfo,
      String typeInformation);
}
