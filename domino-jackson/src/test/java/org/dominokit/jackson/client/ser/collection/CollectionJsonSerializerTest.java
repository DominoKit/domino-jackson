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

package org.dominokit.jackson.client.ser.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.client.ser.AbstractJsonSerializerTest;
import org.dominokit.jackson.ser.IterableJsonSerializer;
import org.dominokit.jackson.ser.StringJsonSerializer;

/** @author Nicolas Morel */
public class CollectionJsonSerializerTest extends AbstractJsonSerializerTest<Collection<String>> {

  @Override
  protected JsonSerializer<Collection<String>> createSerializer() {
    return (JsonSerializer) IterableJsonSerializer.newInstance(StringJsonSerializer.getInstance());
  }

  public void testSerializeValue() {
    assertSerialization(
        "[\"Hello\",\" \",\"World\",\"!\"]", Arrays.asList("Hello", " ", "World", "!"));
    assertSerialization("[]", Collections.<String>emptyList());
  }
}
