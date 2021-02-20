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

package org.dominokit.jackson.ser.array;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;
import org.dominokit.jackson.utils.Base64Utils;

/** Default {@link org.dominokit.jackson.JsonSerializer} implementation for array of byte. */
public class PrimitiveByteArrayJsonSerializer extends JsonSerializer<byte[]> {

  private static final PrimitiveByteArrayJsonSerializer INSTANCE =
      new PrimitiveByteArrayJsonSerializer();

  /**
   * getInstance.
   *
   * @return an instance of {@link org.dominokit.jackson.ser.array.PrimitiveByteArrayJsonSerializer}
   */
  public static PrimitiveByteArrayJsonSerializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveByteArrayJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(byte[] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      byte[] values,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
      writer.cancelName();
      return;
    }

    writer.unescapeValue(Base64Utils.toBase64(values));
  }
}
