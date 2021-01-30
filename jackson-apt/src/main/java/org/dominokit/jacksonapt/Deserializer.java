/*
 * Copyright 2016 Nicolas Morel
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

package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.map.key.KeyDeserializer;

/**
 * Wrapper to access both key and json deserializer for a type.
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public abstract class Deserializer<T> {

  private KeyDeserializer<T> key;

  private JsonDeserializer<T> json;

  /**
   * key
   *
   * @return a {@link org.dominokit.jacksonapt.deser.map.key.KeyDeserializer} object.
   */
  public KeyDeserializer<T> key() {
    if (null == key) {
      key = createKeyDeserializer();
    }
    return key;
  }

  /**
   * createKeyDeserializer
   *
   * @return a {@link org.dominokit.jacksonapt.deser.map.key.KeyDeserializer} object.
   */
  protected abstract KeyDeserializer<T> createKeyDeserializer();

  /**
   * json
   *
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializer} object.
   */
  public JsonDeserializer<T> json() {
    if (null == json) {
      json = createJsonDeserializer();
    }
    return json;
  }

  /**
   * createJsonDeserializer
   *
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializer} object.
   */
  protected abstract JsonDeserializer<T> createJsonDeserializer();
}
