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

package org.dominokit.jackson.ser.map.key;

import java.util.UUID;
import org.dominokit.jackson.JsonSerializationContext;

/**
 * Default {@link org.dominokit.jackson.ser.map.key.KeySerializer} implementation for {@link
 * java.util.UUID}.
 */
public final class UUIDKeySerializer extends KeySerializer<UUID> {

  private static final UUIDKeySerializer INSTANCE = new UUIDKeySerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.ser.map.key.UUIDKeySerializer}
   */
  @SuppressWarnings("unchecked")
  public static UUIDKeySerializer getInstance() {
    return INSTANCE;
  }

  private UUIDKeySerializer() {}

  /** {@inheritDoc} */
  @Override
  public boolean mustBeEscaped(JsonSerializationContext ctx) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected String doSerialize(UUID value, JsonSerializationContext ctx) {
    return value.toString();
  }
}
