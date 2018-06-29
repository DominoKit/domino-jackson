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

package org.dominokit.jacksonapt.client.ser.date;

import org.dominokit.jacksonapt.client.ser.AbstractJsonSerializerTest;
import org.dominokit.jacksonapt.ser.BaseDateJsonSerializer.DateJsonSerializer;

import java.util.Date;

/**
 * @author Nicolas Morel
 */
public class DateJsonSerializerTest extends AbstractJsonSerializerTest<Date> {

    @Override
    protected DateJsonSerializer createSerializer() {
        return DateJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        Date date = getUTCDate(2012, 8, 18, 12, 45, 56, 543);
        assertSerialization("" + date.getTime(), date);
    }
}
