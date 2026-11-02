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
package org.dominokit.jackson.processor.creator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
public class CreatorCustomBean {

  private final CreatorToken created;
  private final CreatorToken extra;

  @JsonCreator
  public CreatorCustomBean(
      @JsonProperty("created") CreatorToken created, @JsonProperty("extra") CreatorToken extra) {
    this.created = created;
    this.extra = extra;
  }

  public CreatorToken getCreated() {
    return created;
  }

  public CreatorToken getExtra() {
    return extra;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreatorCustomBean that = (CreatorCustomBean) o;
    return Objects.equals(created, that.created) && Objects.equals(extra, that.extra);
  }

  @Override
  public int hashCode() {
    return Objects.hash(created, extra);
  }
}
