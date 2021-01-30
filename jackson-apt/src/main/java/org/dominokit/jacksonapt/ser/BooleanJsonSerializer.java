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

package org.dominokit.jacksonapt.ser;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for {@link
 * java.lang.Boolean}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class BooleanJsonSerializer extends JsonSerializer<Boolean> {

  private static final BooleanJsonSerializer INSTANCE = new BooleanJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jacksonapt.ser.BooleanJsonSerializer}
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
