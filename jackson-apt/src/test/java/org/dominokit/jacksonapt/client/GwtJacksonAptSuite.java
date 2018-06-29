/*
 * Copyright 2017 Ahmad Bawaneh
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
package org.dominokit.jacksonapt.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.dominokit.jacksonapt.client.deser.*;
import org.dominokit.jacksonapt.client.deser.array.ArrayJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.array.ByteArray2dJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.array.ByteArrayJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.collection.CollectionJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.collection.IterableJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.collection.ListJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.collection.SetJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.date.DateJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.date.SqlDateJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.date.SqlTimeJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.date.SqlTimestampJsonDeserializerTest;
import org.dominokit.jacksonapt.client.deser.map.key.*;
import org.dominokit.jacksonapt.client.deser.number.*;
import org.dominokit.jacksonapt.client.ser.*;
import org.dominokit.jacksonapt.client.ser.array.ArrayJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.array.ByteArray2dJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.array.ByteArrayJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.collection.CollectionJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.collection.IterableJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.collection.ListJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.collection.SetJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.date.DateJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.date.SqlDateJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.date.SqlTimeJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.date.SqlTimestampJsonSerializerTest;
import org.dominokit.jacksonapt.client.ser.map.key.*;
import org.dominokit.jacksonapt.client.ser.number.*;
import org.dominokit.jacksonapt.client.stream.impl.DefaultJsonReaderTest;
import org.dominokit.jacksonapt.client.stream.impl.DefaultJsonWriterTest;
import org.dominokit.jacksonapt.client.stream.impl.FastJsonWriterTest;
import org.dominokit.jacksonapt.client.stream.impl.NonBufferedJsonReaderTest;

public class GwtJacksonAptSuite extends GWTTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests for client-jackson-apt");

        // Stream - tests from gson
        suite.addTestSuite(DefaultJsonReaderTest.class);
        suite.addTestSuite(NonBufferedJsonReaderTest.class);
        suite.addTestSuite(DefaultJsonWriterTest.class);
        suite.addTestSuite(FastJsonWriterTest.class);

        // Default json serializers
        suite.addTestSuite(ArrayJsonSerializerTest.class);
        suite.addTestSuite(ByteArrayJsonSerializerTest.class);
        suite.addTestSuite(ByteArray2dJsonSerializerTest.class);
        suite.addTestSuite(BigDecimalJsonSerializerTest.class);
        suite.addTestSuite(BigIntegerJsonSerializerTest.class);
        suite.addTestSuite(BooleanJsonSerializerTest.class);
        suite.addTestSuite(ByteJsonSerializerTest.class);
        suite.addTestSuite(CharacterJsonSerializerTest.class);
        suite.addTestSuite(CollectionJsonSerializerTest.class);
        suite.addTestSuite(DateJsonSerializerTest.class);
        suite.addTestSuite(DoubleJsonSerializerTest.class);
        suite.addTestSuite(EnumJsonSerializerTest.class);
        suite.addTestSuite(FloatJsonSerializerTest.class);
        suite.addTestSuite(IntegerJsonSerializerTest.class);
        suite.addTestSuite(IterableJsonSerializerTest.class);
        suite.addTestSuite(ListJsonSerializerTest.class);
        suite.addTestSuite(LongJsonSerializerTest.class);
        suite.addTestSuite(SetJsonSerializerTest.class);
        suite.addTestSuite(ShortJsonSerializerTest.class);
        suite.addTestSuite(SqlDateJsonSerializerTest.class);
        suite.addTestSuite(SqlTimeJsonSerializerTest.class);
        suite.addTestSuite(SqlTimestampJsonSerializerTest.class);
        suite.addTestSuite(StringJsonSerializerTest.class);
        suite.addTestSuite(UUIDJsonSerializerTest.class);
        suite.addTestSuite(VoidJsonSerializerTest.class);

        // Default key serializers
        suite.addTestSuite(BooleanKeySerializerTest.class);
        suite.addTestSuite(CharacterKeySerializerTest.class);
        suite.addTestSuite(DateKeySerializerTest.class);
        suite.addTestSuite(EnumKeySerializerTest.class);
        suite.addTestSuite(SqlDateKeySerializerTest.class);
        suite.addTestSuite(SqlTimeKeySerializerTest.class);
        suite.addTestSuite(SqlTimestampKeySerializerTest.class);
        suite.addTestSuite(StringKeySerializerTest.class);
        suite.addTestSuite(UUIDKeySerializerTest.class);
        suite.addTestSuite(BigDecimalKeySerializerTest.class);
        suite.addTestSuite(BigIntegerKeySerializerTest.class);
        suite.addTestSuite(ByteKeySerializerTest.class);
        suite.addTestSuite(DoubleKeySerializerTest.class);
        suite.addTestSuite(FloatKeySerializerTest.class);
        suite.addTestSuite(IntegerKeySerializerTest.class);
        suite.addTestSuite(LongKeySerializerTest.class);
        suite.addTestSuite(ShortKeySerializerTest.class);

        // Default deserializers
        suite.addTestSuite(ArrayJsonDeserializerTest.class);
        suite.addTestSuite(ByteArrayJsonDeserializerTest.class);
        suite.addTestSuite(ByteArray2dJsonDeserializerTest.class);
        suite.addTestSuite(BigDecimalJsonDeserializerTest.class);
        suite.addTestSuite(BigIntegerJsonDeserializerTest.class);
        suite.addTestSuite(BooleanJsonDeserializerTest.class);
        suite.addTestSuite(ByteJsonDeserializerTest.class);
        suite.addTestSuite(CharacterJsonDeserializerTest.class);
        suite.addTestSuite(CollectionJsonDeserializerTest.class);
        suite.addTestSuite(DateJsonDeserializerTest.class);
        suite.addTestSuite(DoubleJsonDeserializerTest.class);
        suite.addTestSuite(EnumJsonDeserializerTest.class);
        suite.addTestSuite(FloatJsonDeserializerTest.class);
        suite.addTestSuite(IntegerJsonDeserializerTest.class);
        suite.addTestSuite(IterableJsonDeserializerTest.class);
        suite.addTestSuite(ListJsonDeserializerTest.class);
        suite.addTestSuite(LongJsonDeserializerTest.class);
        suite.addTestSuite(SetJsonDeserializerTest.class);
        suite.addTestSuite(ShortJsonDeserializerTest.class);
        suite.addTestSuite(SqlDateJsonDeserializerTest.class);
        suite.addTestSuite(SqlTimeJsonDeserializerTest.class);
        suite.addTestSuite(SqlTimestampJsonDeserializerTest.class);
        suite.addTestSuite(StringJsonDeserializerTest.class);
        suite.addTestSuite(UUIDJsonDeserializerTest.class);
        suite.addTestSuite(VoidJsonDeserializerTest.class);

        // Default key deserializers
        suite.addTestSuite(BooleanKeyDeserializerTest.class);
        suite.addTestSuite(CharacterKeyDeserializerTest.class);
        suite.addTestSuite(DateKeyDeserializerTest.class);
        suite.addTestSuite(EnumKeyDeserializerTest.class);
        suite.addTestSuite(SqlDateKeyDeserializerTest.class);
        suite.addTestSuite(SqlTimeKeyDeserializerTest.class);
        suite.addTestSuite(SqlTimestampKeyDeserializerTest.class);
        suite.addTestSuite(StringKeyDeserializerTest.class);
        suite.addTestSuite(UUIDKeyDeserializerTest.class);
        suite.addTestSuite(BigDecimalKeyDeserializerTest.class);
        suite.addTestSuite(BigIntegerKeyDeserializerTest.class);
        suite.addTestSuite(ByteKeyDeserializerTest.class);
        suite.addTestSuite(DoubleKeyDeserializerTest.class);
        suite.addTestSuite(FloatKeyDeserializerTest.class);
        suite.addTestSuite(IntegerKeyDeserializerTest.class);
        suite.addTestSuite(LongKeyDeserializerTest.class);
        suite.addTestSuite(ShortKeyDeserializerTest.class);
        return suite;
    }
}
