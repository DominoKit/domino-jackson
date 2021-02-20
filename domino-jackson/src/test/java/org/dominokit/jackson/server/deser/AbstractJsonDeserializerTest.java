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

package org.dominokit.jackson.server.deser;

import static org.assertj.core.api.Assertions.assertThat;

import org.dominokit.jackson.DefaultJsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.server.ServerJacksonTestCase;
import org.dominokit.jackson.stream.JsonReader;
import org.junit.Test;

/** @author Nicolas Morel */
public abstract class AbstractJsonDeserializerTest<T> extends ServerJacksonTestCase {

  protected abstract JsonDeserializer<T> createDeserializer();

  @Test
  public void testDeserializeNullValue() {
    assertThat(deserialize("null")).isNull();
  }

  protected T deserialize(String value) {
    JsonDeserializationContext ctx = DefaultJsonDeserializationContext.builder().build();
    return deserialize(ctx, value);
  }

  protected T deserialize(JsonDeserializationContext ctx, String value) {
    JsonReader reader = ctx.newJsonReader(value);
    return createDeserializer().deserialize(reader, ctx);
  }

  protected void assertDeserialization(T expected, String value) {
    assertThat(expected).isEqualTo(deserialize(value));
  }

  protected void assertDeserialization(JsonDeserializationContext ctx, T expected, String value) {
    assertThat(expected).isEqualTo(deserialize(ctx, value));
  }

  public abstract void testDeserializeValue();
}
