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

package org.dominokit.jacksonapt.ser.array;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for array.
 *
 * @param <T> Type of the elements inside the array
 * @author Nicolas Morel
 * @version $Id: $
 */
public class ArrayJsonSerializer<T> extends JsonSerializer<T[]> {

  /**
   * newInstance
   *
   * @param serializer {@link org.dominokit.jacksonapt.JsonSerializer} used to serialize the objects
   *     inside the array.
   * @param <T> Type of the elements inside the array
   * @return a new instance of {@link org.dominokit.jacksonapt.ser.array.ArrayJsonSerializer}
   */
  public static <T> ArrayJsonSerializer<T> newInstance(JsonSerializer<T> serializer) {
    return new ArrayJsonSerializer<T>(serializer);
  }

  private final JsonSerializer<T> serializer;

  /**
   * Constructor for ArrayJsonSerializer.
   *
   * @param serializer {@link org.dominokit.jacksonapt.JsonSerializer} used to serialize the objects
   *     inside the array.
   */
  protected ArrayJsonSerializer(JsonSerializer<T> serializer) {
    if (null == serializer) {
      throw new IllegalArgumentException("serializer cannot be null");
    }
    this.serializer = serializer;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(T[] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      T[] values,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
      writer.cancelName();
      return;
    }

    if (ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1) {
      serializer.serialize(writer, values[0], ctx, params);
    } else {
      writer.beginArray();
      for (T value : values) {
        serializer.serialize(writer, value, ctx, params);
      }
      writer.endArray();
    }
  }
}
