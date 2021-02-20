/*
 * Copyright 2013 Nicolas Morel
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

package org.dominokit.jackson.server.deser.map.key;

import org.dominokit.jackson.deser.map.key.BaseNumberKeyDeserializer;
import org.junit.Test;

/** @author Nicolas Morel */
public class ShortKeyDeserializerTest extends AbstractKeyDeserializerTest<Short> {

  @Override
  protected BaseNumberKeyDeserializer.ShortKeyDeserializer createDeserializer() {
    return BaseNumberKeyDeserializer.ShortKeyDeserializer.getInstance();
  }

  @Override
  @Test
  public void testDeserializeValue() {
    assertDeserialization(new Short("34"), "34");
    assertDeserialization(new Short("-1"), "-1");
    assertDeserialization(Short.MIN_VALUE, "-32768");
    assertDeserialization(Short.MAX_VALUE, "32767");
  }
}
