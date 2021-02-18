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

/** Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for array of boolean. */
public class PrimitiveBooleanArrayJsonSerializer extends JsonSerializer<boolean[]> {

  private static final PrimitiveBooleanArrayJsonSerializer INSTANCE =
      new PrimitiveBooleanArrayJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jacksonapt.ser.array.PrimitiveBooleanArrayJsonSerializer}
   */
  public static PrimitiveBooleanArrayJsonSerializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveBooleanArrayJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(boolean[] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      boolean[] values,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
      writer.cancelName();
      return;
    }

    if (ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1) {
      writer.value(values[0]);
    } else {
      writer.beginArray();
      for (boolean value : values) {
        writer.value(value);
      }
      writer.endArray();
    }
  }
}
