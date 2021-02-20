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
package org.dominokit.jackson.processor.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
public class TestBeanWithIgnore extends BaseBeanWithIgnore {

  @JsonIgnore private Integer id;

  @JsonIgnore
  @JsonProperty("gender")
  private String personGender;

  private String name;
  private String address;

  public TestBeanWithIgnore() {}

  public TestBeanWithIgnore(
      Integer id, String name, String address, String propertyx, String propertyy) {
    this.id = id;
    this.name = name;
    this.address = address;
    setPropertyx(propertyx);
    setPropertyy(propertyy);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPersonGender() {
    return personGender;
  }

  public void setPersonGender(String personGender) {
    this.personGender = personGender;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
