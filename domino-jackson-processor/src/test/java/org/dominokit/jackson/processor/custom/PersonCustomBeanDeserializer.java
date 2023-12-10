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

import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.annotation.CustomDeserializer;
import org.dominokit.jackson.stream.JsonReader;

/** To be used for internal testing only */
@CustomDeserializer(Person.class)
public class PersonCustomBeanDeserializer extends JsonDeserializer<Person> {

  private static final PersonCustomBeanDeserializer INSTANCE = new PersonCustomBeanDeserializer();

  public static PersonCustomBeanDeserializer getInstance() {
    return INSTANCE;
  }

  @Override
  protected Person doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {

    String value = reader.nextString();
    if (nonNull(value)) {
      String[] split = value.split(",");
      Person bean = new Person();
      bean.setId(Long.parseLong(split[0]));
      bean.setName(split[1]);
      bean.setTitle(split[2]);
      return bean;
    }
    return null;
  }
}
