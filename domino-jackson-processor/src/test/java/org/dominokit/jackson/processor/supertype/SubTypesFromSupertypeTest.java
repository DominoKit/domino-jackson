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

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * {@code @JsonTypeInfo} and {@code @JsonSubTypes} declared on an ancestor interface must be applied
 * to a property declared with a concrete subtype, as Jackson does.
 */
public class SubTypesFromSupertypeTest {

  private static final String JSON =
      "{\"queryParam\":{\"type\":\"TableNameQueryParam\",\"name\":\"count\",\"tableName\":\"roads\"}}";

  private static CountRequest request() {
    CountRequest request = new CountRequest();
    request.setQueryParam(new TableNameQueryParam("count", "roads"));
    return request;
  }

  @Test
  public void serializeWritesTypeFromAncestorInterface() {
    assertEquals(JSON, CountRequest_MapperImpl.INSTANCE.write(request()));
  }

  @Test
  public void serializeMatchesJackson() throws Exception {
    assertEquals(
        new ObjectMapper().writeValueAsString(request()),
        CountRequest_MapperImpl.INSTANCE.write(request()));
  }

  @Test
  public void deserializeReadsTypeFromAncestorInterface() {
    CountRequest result = CountRequest_MapperImpl.INSTANCE.read(JSON);
    assertEquals(new TableNameQueryParam("count", "roads"), result.getQueryParam());
  }
}
