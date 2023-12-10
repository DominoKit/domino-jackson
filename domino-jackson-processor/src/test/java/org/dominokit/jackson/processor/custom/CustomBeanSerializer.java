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
package org.dominokit.jackson.processor.custom;

import static java.util.Objects.nonNull;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.annotation.CustomSerializer;
import org.dominokit.jackson.stream.JsonWriter;

@CustomSerializer(Employee.class)
public class CustomBeanSerializer extends JsonSerializer<Employee> {

  private static final CustomBeanSerializer INSTANCE = new CustomBeanSerializer();

  public static CustomBeanSerializer getInstance() {
    return INSTANCE;
  }

  @Override
  protected void doSerialize(
      JsonWriter writer,
      Employee value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (nonNull(value)) {
      writer.value(value.getId() + "," + value.getName() + "," + value.getTitle());
    } else {
      writer.value("");
    }
  }
}
