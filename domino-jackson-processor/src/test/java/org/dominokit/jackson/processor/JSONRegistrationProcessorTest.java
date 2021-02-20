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
package org.dominokit.jackson.processor;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import org.dominokit.jackson.ObjectMapper;
import org.dominokit.jackson.ObjectReader;
import org.dominokit.jackson.ObjectWriter;
import org.dominokit.jackson.annotation.JSONMapper;
import org.dominokit.jackson.annotation.JSONReader;
import org.dominokit.jackson.annotation.JSONWriter;
import org.dominokit.jackson.registration.JsonRegistry;
import org.dominokit.jackson.registration.TypeToken;
import org.junit.Test;

public class JSONRegistrationProcessorTest {

  @JSONMapper
  public interface PersonMapper extends ObjectMapper<Person> {}

  @JSONReader
  public interface PersonReader extends ObjectReader<Person> {}

  @JSONWriter
  public interface PersonWriter extends ObjectWriter<Person> {}

  @JSONMapper
  interface ListOfMapMapper extends ObjectMapper<List<Map<Integer, SimpleBeanObject>>> {}

  @Test
  public void whenCompile_thenShouldRegisterMappersReadersAndWritersInTheirOwnRegistry() {
    JsonRegistry testJsonRegistry = TestJsonRegistry.getInstance();

    assertNotNull(testJsonRegistry.getMapper(TypeToken.of(Person.class)));
    assertNotNull(testJsonRegistry.getReader(TypeToken.of(Person.class)));
    assertNotNull(testJsonRegistry.getWriter(TypeToken.of(Person.class)));
    assertNotNull(
        testJsonRegistry.getMapper(
            new TypeToken<List<Map<Integer, SimpleBeanObject>>>(
                List.class,
                new TypeToken<Map<Integer, SimpleBeanObject>>(
                    Map.class,
                    TypeToken.of(Integer.class),
                    TypeToken.of(SimpleBeanObject.class)) {}) {}));
  }
}
