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
package org.dominokit.jackson.processor.identity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomIdentitySerializerTest {
  @Test
  public void serializesGeneratedObjectIdWithRegisteredCustomSerializer() {
    IdentityCustomBean bean = new IdentityCustomBean("alpha");
    bean.setSelf(bean);

    String json = IdentityCustomBean_MapperImpl.INSTANCE.write(bean);

    assertEquals(
        "{\"@id\":\"identity:node-alpha\",\"name\":\"alpha\",\"self\":\"identity:node-alpha\"}",
        json);
  }
}
