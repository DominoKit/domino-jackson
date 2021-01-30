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

package org.dominokit.jacksonapt.ser.bean;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Interface hiding the actual implementation doing the bean serialization.
 *
 * @author Nicolas Morel.
 */
interface InternalSerializer<T> {

  /**
   * serializeInternally
   *
   * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
   * @param value a T object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonSerializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   * @param defaultIdentityInfo a {@link
   *     org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
   * @param defaultTypeInfo a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo}
   *     object.
   */
  void serializeInternally(
      JsonWriter writer,
      T value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params,
      IdentitySerializationInfo<T> defaultIdentityInfo,
      TypeSerializationInfo<T> defaultTypeInfo);
}
