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
package org.dominokit.jackson.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PropertyIdentityCustomBean {
  private IdentityToken id;
  private String name;
  private PropertyIdentityCustomBean self;

  public PropertyIdentityCustomBean() {}

  public PropertyIdentityCustomBean(IdentityToken id, String name) {
    this.id = id;
    this.name = name;
  }

  public IdentityToken getId() {
    return id;
  }

  public void setId(IdentityToken id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PropertyIdentityCustomBean getSelf() {
    return self;
  }

  public void setSelf(PropertyIdentityCustomBean self) {
    this.self = self;
  }
}
