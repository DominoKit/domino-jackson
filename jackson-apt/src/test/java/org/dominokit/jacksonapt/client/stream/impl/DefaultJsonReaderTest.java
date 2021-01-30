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
package org.dominokit.jacksonapt.client.stream.impl;

import org.dominokit.jacksonapt.client.stream.AbstractJsonReaderTest;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;
import org.dominokit.jacksonapt.stream.impl.DefaultJsonReader;
import org.dominokit.jacksonapt.stream.impl.MalformedJsonException;
import org.dominokit.jacksonapt.stream.impl.StringReader;

/** @author Nicolas Morel */
public class DefaultJsonReaderTest extends AbstractJsonReaderTest {

  @Override
  public JsonReader newJsonReader(String input) {
    return new DefaultJsonReader(new StringReader(input));
  }

  public void testStrictVeryLongNumber() {
    JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
    reader.beginArray();
    try {
      assertEquals(1d, reader.nextDouble());
      fail();
    } catch (MalformedJsonException expected) {
    }
  }

  public void testLenientVeryLongNumber() {
    JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
    reader.setLenient(true);
    reader.beginArray();
    assertEquals(JsonToken.STRING, reader.peek());
    assertEquals(1d, reader.nextDouble());
    reader.endArray();
    assertEquals(JsonToken.END_DOCUMENT, reader.peek());
  }
}
