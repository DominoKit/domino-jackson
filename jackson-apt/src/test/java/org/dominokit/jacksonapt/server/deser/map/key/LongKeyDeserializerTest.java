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

package org.dominokit.jacksonapt.server.deser.map.key;

import org.dominokit.jacksonapt.deser.map.key.BaseNumberKeyDeserializer;
import org.junit.Test;

/** @author Nicolas Morel */
public class LongKeyDeserializerTest extends AbstractKeyDeserializerTest<Long> {

  @Override
  protected BaseNumberKeyDeserializer.LongKeyDeserializer createDeserializer() {
    return BaseNumberKeyDeserializer.LongKeyDeserializer.getInstance();
  }

  @Override
  @Test
  public void testDeserializeValue() {
    assertDeserialization(3441764551145441542l, "3441764551145441542");
    assertDeserialization(new Long("-3441764551145441542"), "-3441764551145441542");
    assertDeserialization(Long.MIN_VALUE, "-9223372036854775808");
    assertDeserialization(Long.MAX_VALUE, "9223372036854775807");
  }
}
