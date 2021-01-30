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
package org.dominokit.jacksonapt.server.stream.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import org.dominokit.jacksonapt.server.stream.AbstractJsonReaderTest;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;
import org.dominokit.jacksonapt.stream.impl.DefaultJsonReader;
import org.dominokit.jacksonapt.stream.impl.MalformedJsonException;
import org.dominokit.jacksonapt.stream.impl.StringReader;
import org.junit.Test;

/** @author Nicolas Morel */
public class DefaultJsonReaderTest extends AbstractJsonReaderTest {

  @Override
  public JsonReader newJsonReader(String input) {
    return new DefaultJsonReader(new StringReader(input));
  }

  @Test
  public void testStrictVeryLongNumber() {
    JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
    reader.beginArray();
    try {
      assertThat(1d).isEqualTo(reader.nextDouble());
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testLenientVeryLongNumber() {
    JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(JsonToken.STRING).isEqualTo(reader.peek());
    assertThat(1d).isEqualTo(reader.nextDouble());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }
}
