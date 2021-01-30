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

package org.dominokit.jacksonapt.server.ser.map.key;

import java.sql.Time;
import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.ser.map.key.DateKeySerializer;
import org.junit.Test;

/** @author Nicolas Morel */
public class SqlTimeKeySerializerTest extends AbstractKeySerializerTest<Time> {

  @Override
  protected DateKeySerializer createSerializer() {
    return DateKeySerializer.getInstance();
  }

  @Test
  public void testSerializeValue() {
    Time time = new Time(getUTCTime(2012, 8, 18, 12, 45, 56, 543));
    String expected = JacksonContextProvider.get().dateFormat().format(time);
    assertSerialization(expected, time);
  }
}
