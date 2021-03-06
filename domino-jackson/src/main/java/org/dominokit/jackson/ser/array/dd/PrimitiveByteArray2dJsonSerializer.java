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

package org.dominokit.jackson.ser.array.dd;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;
import org.dominokit.jackson.utils.Base64Utils;

/** Default {@link org.dominokit.jackson.JsonSerializer} implementation for 2D array of byte. */
public class PrimitiveByteArray2dJsonSerializer extends JsonSerializer<byte[][]> {

  private static final PrimitiveByteArray2dJsonSerializer INSTANCE =
      new PrimitiveByteArray2dJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jackson.ser.array.dd.PrimitiveByteArray2dJsonSerializer}
   */
  public static PrimitiveByteArray2dJsonSerializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveByteArray2dJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(byte[][] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      byte[][] values,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
      writer.cancelName();
      return;
    }

    writer.beginArray();
    for (byte[] array : values) {
      writer.unescapeValue(Base64Utils.toBase64(array));
    }
    writer.endArray();
  }
}
