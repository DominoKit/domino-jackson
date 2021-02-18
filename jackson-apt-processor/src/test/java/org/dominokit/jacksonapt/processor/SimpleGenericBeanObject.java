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

import java.util.List;

public class SimpleGenericBeanObject<T> {
  public int intField;
  public T typeField;
  public List<T>[] genericArr;
  public String str = "str";

  public SimpleGenericBeanObject() {
    this(0, null);
  }

  public SimpleGenericBeanObject(int intField, T typeField) {
    this.intField = intField;
    this.typeField = typeField;
  }

  public int getIntFueld() {
    return intField;
  }

  public T getTypeField() {
    return typeField;
  }

  public String getStr() {
    return str;
  }

  @Override
  public int hashCode() {
    return intField + 17 * typeField.hashCode();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object other) {
    return other instanceof SimpleGenericBeanObject
        && intField == ((SimpleGenericBeanObject) other).intField
        && typeField.equals(((SimpleGenericBeanObject) other).typeField);
  }
}
