/*
 * Copyright 2015 Nicolas Morel
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

package org.dominokit.jackson.ser;

import java.util.Collection;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jackson.JsonSerializer} implementation for {@link
 * java.util.Collection}.
 *
 * @param <T> Type of the elements inside the {@link java.util.Collection}
 */
public class CollectionJsonSerializer<C extends Collection<T>, T> extends JsonSerializer<C> {

  /**
   * newInstance
   *
   * @param serializer {@link org.dominokit.jackson.JsonSerializer} used to serialize the objects
   *     inside the {@link java.util.Collection}.
   * @param <C> Type of the {@link Collection}
   * @return a new instance of {@link org.dominokit.jackson.ser.CollectionJsonSerializer}
   */
  public static <C extends Collection<?>> CollectionJsonSerializer<C, ?> newInstance(
      JsonSerializer<?> serializer) {
    return new CollectionJsonSerializer(serializer);
  }

  protected final JsonSerializer<T> serializer;

  /**
   * Constructor for CollectionJsonSerializer.
   *
   * @param serializer {@link org.dominokit.jackson.JsonSerializer} used to serialize the objects
   *     inside the {@link java.util.Collection}.
   */
  protected CollectionJsonSerializer(JsonSerializer<T> serializer) {
    if (null == serializer) {
      throw new IllegalArgumentException("serializer cannot be null");
    }
    this.serializer = serializer;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(C value) {
    return null == value || value.isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer, C values, JsonSerializationContext ctx, JsonSerializerParameters params) {
    if (values.isEmpty()) {
      if (ctx.isWriteEmptyJsonArrays()) {
        writer.beginArray();
        writer.endArray();
      } else {
        writer.cancelName();
      }
      return;
    }

    if (ctx.isWriteSingleElemArraysUnwrapped() && values.size() == 1) {
      // there is only one element, we write it directly
      serializer.serialize(writer, values.iterator().next(), ctx, params);
    } else {
      writer.beginArray();
      values.forEach(value -> serializer.serialize(writer, value, ctx, params));
      writer.endArray();
    }
  }
}
