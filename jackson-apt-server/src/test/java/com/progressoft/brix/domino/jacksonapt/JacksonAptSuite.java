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
package com.progressoft.brix.domino.jacksonapt;

import com.progressoft.brix.domino.jacksonapt.deser.*;
import com.progressoft.brix.domino.jacksonapt.deser.array.ArrayJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.array.ByteArray2dJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.array.ByteArrayJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.collection.CollectionJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.collection.IterableJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.collection.ListJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.collection.SetJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.date.DateJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.date.SqlDateJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.date.SqlTimeJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.date.SqlTimestampJsonDeserializerTest;
import com.progressoft.brix.domino.jacksonapt.deser.map.key.*;
import com.progressoft.brix.domino.jacksonapt.deser.number.*;
import com.progressoft.brix.domino.jacksonapt.ser.*;
import com.progressoft.brix.domino.jacksonapt.ser.array.ArrayJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.array.ByteArray2dJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.array.ByteArrayJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.collection.CollectionJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.collection.IterableJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.collection.ListJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.collection.SetJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.date.DateJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.date.SqlDateJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.date.SqlTimeJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.date.SqlTimestampJsonSerializerTest;
import com.progressoft.brix.domino.jacksonapt.ser.map.key.*;
import com.progressoft.brix.domino.jacksonapt.ser.number.*;
import com.progressoft.brix.domino.jacksonapt.stream.impl.DefaultJsonReaderTest;
import com.progressoft.brix.domino.jacksonapt.stream.impl.DefaultJsonWriterTest;
import com.progressoft.brix.domino.jacksonapt.stream.impl.FastJsonWriterTest;
import com.progressoft.brix.domino.jacksonapt.stream.impl.NonBufferedJsonReaderTest;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DefaultJsonReaderTest.class,
        NonBufferedJsonReaderTest.class,
        DefaultJsonWriterTest.class,
        FastJsonWriterTest.class,

        // Default json serializers
        ArrayJsonSerializerTest.class,
        ByteArrayJsonSerializerTest.class,
        ByteArray2dJsonSerializerTest.class,
        BigDecimalJsonSerializerTest.class,
        BigIntegerJsonSerializerTest.class,
        BooleanJsonSerializerTest.class,
        ByteJsonSerializerTest.class,
        CharacterJsonSerializerTest.class,
        CollectionJsonSerializerTest.class,
        DateJsonSerializerTest.class,
        DoubleJsonSerializerTest.class,
        EnumJsonSerializerTest.class,
        FloatJsonSerializerTest.class,
        IntegerJsonSerializerTest.class,
        IterableJsonSerializerTest.class,
        ListJsonSerializerTest.class,
        LongJsonSerializerTest.class,
        SetJsonSerializerTest.class,
        ShortJsonSerializerTest.class,
        SqlDateJsonSerializerTest.class,
        SqlTimeJsonSerializerTest.class,
        SqlTimestampJsonSerializerTest.class,
        StringJsonSerializerTest.class,
        UUIDJsonSerializerTest.class,
        VoidJsonSerializerTest.class,

        // Default key serializers
        BooleanKeySerializerTest.class,
        CharacterKeySerializerTest.class,
        DateKeySerializerTest.class,
        EnumKeySerializerTest.class,
        SqlDateKeySerializerTest.class,
        SqlTimeKeySerializerTest.class,
        SqlTimestampKeySerializerTest.class,
        StringKeySerializerTest.class,
        UUIDKeySerializerTest.class,
        BigDecimalKeySerializerTest.class,
        BigIntegerKeySerializerTest.class,
        ByteKeySerializerTest.class,
        DoubleKeySerializerTest.class,
        FloatKeySerializerTest.class,
        IntegerKeySerializerTest.class,
        LongKeySerializerTest.class,
        ShortKeySerializerTest.class,

        // Default deserializers
        ArrayJsonDeserializerTest.class,
        ByteArrayJsonDeserializerTest.class,
        ByteArray2dJsonDeserializerTest.class,
        BigDecimalJsonDeserializerTest.class,
        BigIntegerJsonDeserializerTest.class,
        BooleanJsonDeserializerTest.class,
        ByteJsonDeserializerTest.class,
        CharacterJsonDeserializerTest.class,
        CollectionJsonDeserializerTest.class,
        DateJsonDeserializerTest.class,
        DoubleJsonDeserializerTest.class,
        EnumJsonDeserializerTest.class,
        FloatJsonDeserializerTest.class,
        IntegerJsonDeserializerTest.class,
        IterableJsonDeserializerTest.class,
        ListJsonDeserializerTest.class,
        LongJsonDeserializerTest.class,
        SetJsonDeserializerTest.class,
        ShortJsonDeserializerTest.class,
        SqlDateJsonDeserializerTest.class,
        SqlTimeJsonDeserializerTest.class,
        SqlTimestampJsonDeserializerTest.class,
        StringJsonDeserializerTest.class,
        UUIDJsonDeserializerTest.class,
        VoidJsonDeserializerTest.class,

        // Default key deserializers
        BooleanKeyDeserializerTest.class,
        CharacterKeyDeserializerTest.class,
        DateKeyDeserializerTest.class,
        EnumKeyDeserializerTest.class,
        SqlDateKeyDeserializerTest.class,
        SqlTimeKeyDeserializerTest.class,
        SqlTimestampKeyDeserializerTest.class,
        StringKeyDeserializerTest.class,
        UUIDKeyDeserializerTest.class,
        BigDecimalKeyDeserializerTest.class,
        BigIntegerKeyDeserializerTest.class,
        ByteKeyDeserializerTest.class,
        DoubleKeyDeserializerTest.class,
        FloatKeyDeserializerTest.class,
        IntegerKeyDeserializerTest.class,
        LongKeyDeserializerTest.class,
        ShortKeyDeserializerTest.class
})
public class JacksonAptSuite extends TestSuite {


}
