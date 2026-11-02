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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonCreatorCustomMapperTest {

  @Test
  public void creatorParameterAndFieldUseCustomMappers() {
    CreatorCustomBean bean =
        new CreatorCustomBean(new CreatorToken("from-creator"), new CreatorToken("from-field"));

    String json = CreatorCustomBean_MapperImpl.INSTANCE.write(bean);

    assertEquals("{\"created\":\"token:from-creator\",\"extra\":\"token:from-field\"}", json);
    assertEquals(bean, CreatorCustomBean_MapperImpl.INSTANCE.read(json));
  }
}
