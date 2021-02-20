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

package org.dominokit.jackson.ser.map;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.ser.map.key.KeySerializer;
import org.dominokit.jackson.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jackson.JsonSerializer} implementation for {@link java.util.Map}.
 *
 * @param <M> Type of the {@link java.util.Map}
 * @param <K> Type of the keys inside the {@link java.util.Map}
 * @param <V> Type of the values inside the {@link java.util.Map}
 */
public class MapJsonSerializer<M extends Map<K, V>, K, V> extends JsonSerializer<M> {

  /**
   * newInstance
   *
   * @param keySerializer {@link org.dominokit.jackson.ser.map.key.KeySerializer} used to serialize
   *     the keys.
   * @param valueSerializer {@link org.dominokit.jackson.JsonSerializer} used to serialize the
   *     values.
   * @param <M> Type of the {@link java.util.Map}
   * @return a new instance of {@link org.dominokit.jackson.ser.map.MapJsonSerializer}
   */
  public static <M extends Map<?, ?>> MapJsonSerializer<M, ?, ?> newInstance(
      KeySerializer<?> keySerializer, JsonSerializer<?> valueSerializer) {
    return new MapJsonSerializer(keySerializer, valueSerializer);
  }

  protected final KeySerializer<K> keySerializer;

  protected final JsonSerializer<V> valueSerializer;

  /**
   * Constructor for MapJsonSerializer.
   *
   * @param keySerializer {@link org.dominokit.jackson.ser.map.key.KeySerializer} used to serialize
   *     the keys.
   * @param valueSerializer {@link org.dominokit.jackson.JsonSerializer} used to serialize the
   *     values.
   */
  protected MapJsonSerializer(KeySerializer<K> keySerializer, JsonSerializer<V> valueSerializer) {
    if (null == keySerializer) {
      throw new IllegalArgumentException("keySerializer cannot be null");
    }
    if (null == valueSerializer) {
      throw new IllegalArgumentException("valueSerializer cannot be null");
    }
    this.keySerializer = keySerializer;
    this.valueSerializer = valueSerializer;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(M value) {
    return null == value || value.isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer, M values, JsonSerializationContext ctx, JsonSerializerParameters params) {
    writer.beginObject();

    serializeValues(writer, values, ctx, params);

    writer.endObject();
  }

  /**
   * serializeValues
   *
   * @param writer a {@link org.dominokit.jackson.stream.JsonWriter} object.
   * @param values a M object.
   * @param ctx a {@link org.dominokit.jackson.JsonSerializationContext} object.
   * @param params a {@link org.dominokit.jackson.JsonSerializerParameters} object.
   */
  public void serializeValues(
      JsonWriter writer, M values, JsonSerializationContext ctx, JsonSerializerParameters params) {
    if (!values.isEmpty()) {
      Map<K, V> map = values;
      if (ctx.isOrderMapEntriesByKeys() && !(values instanceof SortedMap<?, ?>)) {
        map = new TreeMap<K, V>(map);
      }

      if (ctx.isWriteNullMapValues()) {

        for (Entry<K, V> entry : map.entrySet()) {
          String name = keySerializer.serialize(entry.getKey(), ctx);
          if (keySerializer.mustBeEscaped(ctx)) {
            writer.name(name);
          } else {
            writer.unescapeName(name);
          }
          valueSerializer.serialize(writer, entry.getValue(), ctx, params, true);
        }

      } else {

        for (Entry<K, V> entry : map.entrySet()) {
          if (null != entry.getValue()) {
            String name = keySerializer.serialize(entry.getKey(), ctx);
            if (keySerializer.mustBeEscaped(ctx)) {
              writer.name(name);
            } else {
              writer.unescapeName(name);
            }
            valueSerializer.serialize(writer, entry.getValue(), ctx, params, true);
          }
        }
      }
    }
  }
}
