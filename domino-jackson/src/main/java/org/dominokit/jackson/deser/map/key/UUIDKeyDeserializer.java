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

package org.dominokit.jackson.deser.map.key;

import java.util.UUID;
import org.dominokit.jackson.JsonDeserializationContext;

/**
 * Default {@link org.dominokit.jackson.deser.map.key.KeyDeserializer} implementation for {@link
 * java.util.UUID}.
 */
public final class UUIDKeyDeserializer extends KeyDeserializer<UUID> {

  private static final UUIDKeyDeserializer INSTANCE = new UUIDKeyDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.deser.map.key.UUIDKeyDeserializer}
   */
  public static UUIDKeyDeserializer getInstance() {
    return INSTANCE;
  }

  private UUIDKeyDeserializer() {}

  /** {@inheritDoc} */
  @Override
  protected UUID doDeserialize(String key, JsonDeserializationContext ctx) {
    return UUID.fromString(key);
  }
}
