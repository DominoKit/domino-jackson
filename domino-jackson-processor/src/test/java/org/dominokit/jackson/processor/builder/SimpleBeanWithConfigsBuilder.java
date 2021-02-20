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
package org.dominokit.jackson.processor.builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder(buildMethodName = "create")
public class SimpleBeanWithConfigsBuilder {
  private String id;
  private String name;
  @JsonIgnore private String address;

  public SimpleBeanWithConfigsBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public SimpleBeanWithConfigsBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public SimpleBeanWithConfigsBuilder setAddress(String address) {
    this.address = address;
    return this;
  }

  public SimpleBeanWithConfigs create() {
    return new SimpleBeanWithConfigs(id, name, address);
  }
}
