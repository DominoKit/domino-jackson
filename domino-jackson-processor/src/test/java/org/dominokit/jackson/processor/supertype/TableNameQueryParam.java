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
package org.dominokit.jackson.processor.supertype;

import java.util.Objects;

/** Concrete class that inherits @JsonTypeInfo and @JsonSubTypes from an ancestor interface. */
public class TableNameQueryParam implements NamedQueryParam {

  private String name;
  private String tableName;

  public TableNameQueryParam() {}

  public TableNameQueryParam(String name, String tableName) {
    this.name = name;
    this.tableName = tableName;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TableNameQueryParam)) {
      return false;
    }
    TableNameQueryParam that = (TableNameQueryParam) o;
    return Objects.equals(name, that.name) && Objects.equals(tableName, that.tableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, tableName);
  }
}
