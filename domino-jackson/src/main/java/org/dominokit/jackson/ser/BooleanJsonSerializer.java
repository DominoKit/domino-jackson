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
 * Default {@link org.dominokit.jackson.JsonSerializer} implementation for {@link
 * java.lang.Boolean}.
 */
public class BooleanJsonSerializer extends JsonSerializer<Boolean> {

  private static final BooleanJsonSerializer INSTANCE = new BooleanJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.ser.BooleanJsonSerializer}
   */
  public static BooleanJsonSerializer getInstance() {
    return INSTANCE;
  }

  private BooleanJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected boolean isDefault(Boolean value) {
    return null == value || !value;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      Boolean value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    writer.value(value);
  }
}
