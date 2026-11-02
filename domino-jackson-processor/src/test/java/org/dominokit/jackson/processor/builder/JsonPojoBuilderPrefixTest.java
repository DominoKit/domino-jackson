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

import org.junit.Test;

public class JsonPojoBuilderPrefixTest {

  @Test
  public void emptyPrefixUsesFieldNameMethods() {
    EmptyPrefixBean bean = new EmptyPrefixBean("id", "name");

    String json = EmptyPrefixBean_MapperImpl.INSTANCE.write(bean);
    EmptyPrefixBean result = EmptyPrefixBean_MapperImpl.INSTANCE.read(json);

    assertEquals(bean, result);
  }

  @Test
  public void withPrefixUsesWithMethods() {
    WithPrefixBean bean = new WithPrefixBean("id", "name");

    String json = WithPrefixBean_MapperImpl.INSTANCE.write(bean);
    WithPrefixBean result = WithPrefixBean_MapperImpl.INSTANCE.read(json);

    assertEquals(bean, result);
  }
}
