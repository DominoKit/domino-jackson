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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.dominokit.jackson.ObjectMapper;
import org.dominokit.jackson.annotation.JSONMapper;
import org.junit.Test;

public class JsonDeserializeBuilderTest {

  @JSONMapper
  interface SimpleBeanMapper extends ObjectMapper<SimpleBean> {
    SimpleBeanMapper MAPPER = new JsonDeserializeBuilderTest_SimpleBeanMapperImpl();
  }

  @Test
  public void test_builder_deserialization() {
    SimpleBeanBuilder beanBuilder = new SimpleBeanBuilder();
    SimpleBean simpleBean = beanBuilder.setId("id").setName("name").build();

    String write = SimpleBeanMapper.MAPPER.write(simpleBean);

    SimpleBean result = SimpleBeanMapper.MAPPER.read(write);

    assertEquals(result, simpleBean);
  }

  @Test
  public void test_builder_deserialization_with_configs() {
    SimpleBeanWithConfigs_MapperImpl mapper = new SimpleBeanWithConfigs_MapperImpl();

    SimpleBeanWithConfigs result =
        mapper.read("{\"id\":\"id\",\"name\":\"name\",\"address\":\"address\"}");

    assertEquals("id", result.getId());
    assertEquals("name", result.getName());
    assertNull(result.getAddress());
  }
}
