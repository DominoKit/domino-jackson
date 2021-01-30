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
package org.dominokit.jacksonapt.processor;

import java.util.Objects;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class ChildObject extends ParentObject {

  public static final ChildObject_MapperImpl MAPPER = new ChildObject_MapperImpl();

  private String address;

  public ChildObject() {}

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ChildObject)) return false;
    if (!super.equals(o)) return false;
    ChildObject that = (ChildObject) o;
    return Objects.equals(getAddress(), that.getAddress());
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), getAddress());
  }
}
