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

import java.util.UUID;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jackson.JsonSerializer} implementation for {@link java.util.UUID}.
 */
public class UUIDJsonSerializer extends JsonSerializer<UUID> {

  private static final UUIDJsonSerializer INSTANCE = new UUIDJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.ser.UUIDJsonSerializer}
   */
  public static UUIDJsonSerializer getInstance() {
    return INSTANCE;
  }

  private UUIDJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      UUID value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    writer.unescapeValue(value.toString());
  }
}
