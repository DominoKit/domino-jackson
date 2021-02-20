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

package org.dominokit.jackson.ser;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jackson.JsonSerializer} implementation for {@link java.lang.Void}.
 */
public class VoidJsonSerializer extends JsonSerializer<Void> {

  private static final VoidJsonSerializer INSTANCE = new VoidJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.ser.VoidJsonSerializer}
   */
  public static VoidJsonSerializer getInstance() {
    return INSTANCE;
  }

  private VoidJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected void serializeNullValue(
      JsonWriter writer, JsonSerializationContext ctx, JsonSerializerParameters params) {
    if (writer.getSerializeNulls()) {
      writer.setSerializeNulls(false);
      writer.nullValue();
      writer.setSerializeNulls(true);
    } else {
      writer.nullValue();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      Void value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    // we should never be here, the null value is already handled and it's the only possible value
    // for Void
  }
}
