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

package com.progressoft.brix.domino.gwtjackson.ser.date;

import com.progressoft.brix.domino.gwtjackson.ser.AbstractJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.BaseDateJsonSerializer.SqlDateJsonSerializer;

import java.sql.Date;

/**
 * @author Nicolas Morel
 */
public class SqlDateJsonSerializerTest extends AbstractJsonSerializerTest<Date> {

    @Override
    protected SqlDateJsonSerializer createSerializer() {
        return SqlDateJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "\"2012-08-18\"", new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) ) );
    }
}
