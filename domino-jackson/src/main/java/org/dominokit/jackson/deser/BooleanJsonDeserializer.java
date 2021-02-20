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

package org.dominokit.jackson.deser;

import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.stream.JsonReader;
import org.dominokit.jackson.stream.JsonToken;

/**
 * Default {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.lang.Boolean}.
 */
public class BooleanJsonDeserializer extends JsonDeserializer<Boolean> {

  private static final BooleanJsonDeserializer INSTANCE = new BooleanJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.deser.BooleanJsonDeserializer}
   */
  public static BooleanJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private BooleanJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public Boolean doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    JsonToken token = reader.peek();
    if (JsonToken.BOOLEAN.equals(token)) {
      return reader.nextBoolean();
    } else if (JsonToken.STRING.equals(token)) {
      return Boolean.valueOf(reader.nextString());
    } else if (JsonToken.NUMBER.equals(token)) {
      return reader.nextInt() == 1;
    } else {
      return null;
    }
  }
}
