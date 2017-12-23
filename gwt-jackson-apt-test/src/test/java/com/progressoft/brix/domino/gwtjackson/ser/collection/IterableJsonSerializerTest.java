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

package com.progressoft.brix.domino.gwtjackson.ser.collection;

import com.progressoft.brix.domino.gwtjackson.JsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.AbstractJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.IterableJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.StringJsonSerializer;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Nicolas Morel
 */
public class IterableJsonSerializerTest extends AbstractJsonSerializerTest<Iterable<String>> {

    @Override
    protected JsonSerializer<Iterable<String>> createSerializer() {
        return (JsonSerializer) IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testSerializeValue() {
        assertSerialization( "[\"Hello\",\" \",\"World\",\"!\"]", Arrays.asList( "Hello", " ", "World", "!" ) );
        assertSerialization( "[]", Collections.<String>emptyList() );
    }

}
