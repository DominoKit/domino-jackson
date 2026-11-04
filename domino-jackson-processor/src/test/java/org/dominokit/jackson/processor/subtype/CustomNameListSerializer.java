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

import java.util.StringJoiner;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.annotation.CustomSerializer;
import org.dominokit.jackson.stream.JsonWriter;

@CustomSerializer(CustomNameList.class)
public class CustomNameListSerializer extends JsonSerializer<CustomNameList> {

  private static final CustomNameListSerializer INSTANCE = new CustomNameListSerializer();

  public static CustomNameListSerializer getInstance() {
    return INSTANCE;
  }

  @Override
  protected void doSerialize(
      JsonWriter writer,
      CustomNameList value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    StringJoiner joiner = new StringJoiner("|");
    value.forEach(joiner::add);
    writer.value("names:" + joiner);
  }
}
