/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson.processor.subtype;

import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.annotation.CustomDeserializer;
import org.dominokit.jackson.stream.JsonReader;

@CustomDeserializer(CustomNameList.class)
public class CustomNameListDeserializer extends JsonDeserializer<CustomNameList> {

  private static final CustomNameListDeserializer INSTANCE = new CustomNameListDeserializer();

  public static CustomNameListDeserializer getInstance() {
    return INSTANCE;
  }

  @Override
  protected CustomNameList doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    CustomNameList list = new CustomNameList();
    String value = reader.nextString();
    String body = value.substring("names:".length());
    if (!body.isEmpty()) {
      for (String name : body.split("\\|")) {
        list.add(name);
      }
    }
    return list;
  }
}
