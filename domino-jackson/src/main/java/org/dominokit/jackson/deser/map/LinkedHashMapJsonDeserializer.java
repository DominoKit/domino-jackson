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

package org.dominokit.jackson.deser.map;

import java.util.LinkedHashMap;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.deser.map.key.KeyDeserializer;

/**
 * Default {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.util.LinkedHashMap}.
 *
 * <p>Cannot be overriden. Use {@link org.dominokit.jackson.deser.map.BaseMapJsonDeserializer}.
 *
 * @param <K> Type of the keys inside the {@link java.util.LinkedHashMap}
 * @param <V> Type of the values inside the {@link java.util.LinkedHashMap}
 */
public final class LinkedHashMapJsonDeserializer<K, V>
    extends BaseMapJsonDeserializer<LinkedHashMap<K, V>, K, V> {

  /**
   * newInstance
   *
   * @param keyDeserializer {@link org.dominokit.jackson.deser.map.key.KeyDeserializer} used to
   *     deserialize the keys.
   * @param valueDeserializer {@link org.dominokit.jackson.JsonDeserializer} used to deserialize the
   *     values.
   * @param <K> Type of the keys inside the {@link java.util.LinkedHashMap}
   * @param <V> Type of the values inside the {@link java.util.LinkedHashMap}
   * @return a new instance of {@link org.dominokit.jackson.deser.map.LinkedHashMapJsonDeserializer}
   */
  public static <K, V> LinkedHashMapJsonDeserializer<K, V> newInstance(
      KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
    return new LinkedHashMapJsonDeserializer<K, V>(keyDeserializer, valueDeserializer);
  }

  /**
   * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
   * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
   */
  private LinkedHashMapJsonDeserializer(
      KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
    super(keyDeserializer, valueDeserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected LinkedHashMap<K, V> newMap() {
    return new LinkedHashMap<K, V>();
  }
}
