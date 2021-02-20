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

import java.util.Map;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.deser.map.key.KeyDeserializer;
import org.dominokit.jackson.stream.JsonReader;
import org.dominokit.jackson.stream.JsonToken;

/**
 * Base {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link java.util.Map}.
 *
 * @param <M> Type of the {@link java.util.Map}
 * @param <K> Type of the keys inside the {@link java.util.Map}
 * @param <V> Type of the values inside the {@link java.util.Map}
 */
public abstract class BaseMapJsonDeserializer<M extends Map<K, V>, K, V>
    extends JsonDeserializer<M> {

  /** {@link KeyDeserializer} used to deserialize the keys. */
  protected final KeyDeserializer<K> keyDeserializer;

  /** {@link JsonDeserializer} used to deserialize the values. */
  protected final JsonDeserializer<V> valueDeserializer;

  /**
   * Constructor for BaseMapJsonDeserializer.
   *
   * @param keyDeserializer {@link org.dominokit.jackson.deser.map.key.KeyDeserializer} used to
   *     deserialize the keys.
   * @param valueDeserializer {@link org.dominokit.jackson.JsonDeserializer} used to deserialize the
   *     values.
   */
  protected BaseMapJsonDeserializer(
      KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
    if (null == keyDeserializer) {
      throw new IllegalArgumentException("keyDeserializer cannot be null");
    }
    if (null == valueDeserializer) {
      throw new IllegalArgumentException("valueDeserializer cannot be null");
    }
    this.keyDeserializer = keyDeserializer;
    this.valueDeserializer = valueDeserializer;
  }

  /** {@inheritDoc} */
  @Override
  public M doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    M result = newMap();

    reader.beginObject();
    while (JsonToken.END_OBJECT != reader.peek()) {
      String name = reader.nextName();
      K key = keyDeserializer.deserialize(name, ctx);
      V value = valueDeserializer.deserialize(reader, ctx, params);
      result.put(key, value);
    }
    reader.endObject();

    return result;
  }

  /**
   * Instantiates a new map for deserialization process.
   *
   * @return the new map
   */
  protected abstract M newMap();

  /** {@inheritDoc} */
  @Override
  public void setBackReference(
      String referenceName, Object reference, M value, JsonDeserializationContext ctx) {
    if (null != value) {
      for (V val : value.values()) {
        valueDeserializer.setBackReference(referenceName, reference, val, ctx);
      }
    }
  }
}
