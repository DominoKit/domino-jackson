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
package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Objects;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class CreatorBean {

  private int id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date date;

  @JsonCreator
  public CreatorBean(@JsonProperty int id, @JsonProperty Date date) {
    this.id = id;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreatorBean that = (CreatorBean) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
