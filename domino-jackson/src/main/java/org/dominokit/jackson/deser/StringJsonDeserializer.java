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

/**
 * Default {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.lang.String}.
 */
public class StringJsonDeserializer extends JsonDeserializer<String> {

  private static final StringJsonDeserializer INSTANCE = new StringJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link org.dominokit.jackson.deser.StringJsonDeserializer}
   */
  public static StringJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private StringJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public String doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    return reader.nextString();
  }
}
