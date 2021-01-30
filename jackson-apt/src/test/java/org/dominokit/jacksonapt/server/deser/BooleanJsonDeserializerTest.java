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

package org.dominokit.jacksonapt.server.deser;

import static org.assertj.core.api.Assertions.assertThat;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.BooleanJsonDeserializer;
import org.junit.Test;

/** @author Nicolas Morel */
public class BooleanJsonDeserializerTest extends AbstractJsonDeserializerTest<Boolean> {

  @Override
  protected JsonDeserializer<Boolean> createDeserializer() {
    return BooleanJsonDeserializer.getInstance();
  }

  @Override
  @Test
  public void testDeserializeValue() {
    assertThat(deserialize("true")).isTrue();
    assertThat(deserialize("\"trUe\"")).isTrue();
    assertThat(deserialize("1")).isTrue();

    assertThat(deserialize("faLse")).isFalse();
    assertThat(deserialize("\"false\"")).isFalse();
    assertThat(deserialize("0")).isFalse();
    assertThat(deserialize("other")).isFalse();
  }
}
