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

package org.dominokit.jacksonapt.server.deser.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import org.dominokit.jacksonapt.deser.BaseDateJsonDeserializer.SqlDateJsonDeserializer;
import org.dominokit.jacksonapt.server.deser.AbstractJsonDeserializerTest;
import org.junit.Test;

/** @author Nicolas Morel */
public class SqlDateJsonDeserializerTest extends AbstractJsonDeserializerTest<Date> {

  @Override
  protected SqlDateJsonDeserializer createDeserializer() {
    return SqlDateJsonDeserializer.getInstance();
  }

  @Override
  @Test
  public void testDeserializeValue() {
    assertDeserialization(new Date(1377543971773l), "1377543971773");
    assertThat(getUTCTime(2012, 8, 18, 0, 0, 0, 0))
        .isEqualTo(deserialize("\"2012-08-18\"").getTime());
  }
}
