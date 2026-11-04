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
package org.dominokit.jackson.processor.subtype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TypeArgumentResolutionTest {

  @Test
  public void keepsStringKeysInMapSubclass() {
    ByNameHolder result = ByNameHolder_MapperImpl.INSTANCE.read("{\"codes\":{\"007\":7}}");

    assertEquals("007", result.getCodes().keySet().iterator().next());
    assertEquals(Integer.valueOf(7), result.getCodes().get("007"));
  }

  @Test
  public void keepsNestedListElementType() {
    NestedListsHolder result =
        NestedListsHolder_MapperImpl.INSTANCE.read("{\"users\":[[{\"name\":\"Ada\"}]]}");

    assertTrue(result.getUsers().get(0).get(0) instanceof NamedUser);
    assertEquals("Ada", result.getUsers().get(0).get(0).getName());
  }

  @Test
  public void resolvesElementTypeFromListInterface() {
    UserListHolder result =
        UserListHolder_MapperImpl.INSTANCE.read("{\"users\":[{\"name\":\"Ada\"}]}");

    assertTrue(result.getUsers().get(0) instanceof NamedUser);
    assertEquals("Ada", result.getUsers().get(0).getName());
  }
}
