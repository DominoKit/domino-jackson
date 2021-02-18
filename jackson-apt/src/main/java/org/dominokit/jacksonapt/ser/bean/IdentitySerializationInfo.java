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
package org.dominokit.jacksonapt.ser.bean;

import org.dominokit.jacksonapt.JsonSerializationContext;

/** Contains identity informations for serialization process. */
public interface IdentitySerializationInfo<T> {

  /**
   * isAlwaysAsId
   *
   * @return true if we should always serialize the bean as an identifier even if it has not been
   *     seralized yet
   */
  boolean isAlwaysAsId();

  /**
   * isProperty
   *
   * @return true if the identifier is also a property of the bean
   */
  boolean isProperty();

  /**
   * getPropertyName
   *
   * @return name of the identifier property
   */
  String getPropertyName();

  /**
   * getObjectId
   *
   * @param bean a T object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonSerializationContext} object.
   * @return a {@link org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer} object.
   */
  ObjectIdSerializer<?> getObjectId(T bean, JsonSerializationContext ctx);
}
