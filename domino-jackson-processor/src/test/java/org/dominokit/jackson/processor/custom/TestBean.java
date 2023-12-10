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

import java.util.Objects;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
public class TestBean {

  private int id;
  private String name;

  private Employee bean;

  private Person person;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee getBean() {
    return bean;
  }

  public void setBean(Employee bean) {
    this.bean = bean;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestBean testBean = (TestBean) o;
    return id == testBean.id
        && Objects.equals(name, testBean.name)
        && Objects.equals(bean, testBean.bean);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, bean);
  }
}
