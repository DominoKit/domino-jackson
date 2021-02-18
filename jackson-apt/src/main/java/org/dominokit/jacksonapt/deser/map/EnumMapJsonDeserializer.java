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

package org.dominokit.jacksonapt.deser.map;

import java.util.EnumMap;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.map.key.EnumKeyDeserializer;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for {@link
 * java.util.EnumMap}.
 *
 * <p>Cannot be overriden. Use {@link org.dominokit.jacksonapt.deser.map.BaseMapJsonDeserializer}.
 *
 * @param <E> Type of the enum keys inside the {@link java.util.EnumMap}
 * @param <V> Type of the values inside the {@link java.util.EnumMap}
 */
public final class EnumMapJsonDeserializer<E extends Enum<E>, V>
    extends BaseMapJsonDeserializer<EnumMap<E, V>, E, V> {

  /**
   * newInstance
   *
   * @param keyDeserializer {@link org.dominokit.jacksonapt.deser.map.key.EnumKeyDeserializer} used
   *     to deserialize the enum keys.
   * @param valueDeserializer {@link org.dominokit.jacksonapt.JsonDeserializer} used to deserialize
   *     the values.
   * @param <V> Type of the values inside the {@link java.util.EnumMap}
   * @param <E> the enum type
   * @return a new instance of {@link org.dominokit.jacksonapt.deser.map.EnumMapJsonDeserializer}
   */
  public static <E extends Enum<E>, V> EnumMapJsonDeserializer<E, V> newInstance(
      EnumKeyDeserializer<E> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
    return new EnumMapJsonDeserializer<E, V>(keyDeserializer, valueDeserializer);
  }

  /** Class of the enum key */
  private final Class<E> enumClass;

  /**
   * @param keyDeserializer {@link EnumKeyDeserializer} used to deserialize the enum keys.
   * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
   */
  private EnumMapJsonDeserializer(
      EnumKeyDeserializer<E> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
    super(keyDeserializer, valueDeserializer);
    this.enumClass = keyDeserializer.getEnumClass();
  }

  /** {@inheritDoc} */
  @Override
  protected EnumMap<E, V> newMap() {
    return new EnumMap<E, V>(enumClass);
  }
}
