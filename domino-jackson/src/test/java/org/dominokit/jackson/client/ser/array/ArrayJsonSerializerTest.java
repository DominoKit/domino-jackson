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

package org.dominokit.jackson.client.ser.array;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.client.ser.AbstractJsonSerializerTest;
import org.dominokit.jackson.ser.StringJsonSerializer;
import org.dominokit.jackson.ser.array.ArrayJsonSerializer;

/** @author Nicolas Morel */
public class ArrayJsonSerializerTest extends AbstractJsonSerializerTest<String[]> {

  private static final Logger logger = Logger.getLogger(ArrayJsonSerializerTest.class.getName());

  @Override
  protected JsonSerializer<String[]> createSerializer() {
    return ArrayJsonSerializer.newInstance(StringJsonSerializer.getInstance());
  }

  public void testSerializeValue() {
    try {
      assertSerialization(
          "[\"Hello\",\" \",\"World\",\"!\"]", new String[] {"Hello", " ", "World", "!"});
    } catch (Exception exception) {
      logger.log(Level.SEVERE, "", exception);
    }
    assertSerialization("[]", new String[0]);
  }
}
