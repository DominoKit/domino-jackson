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
package com.progressoft.brix.domino.gwtjackson;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.progressoft.brix.domino.gwtjackson.deser.*;
import com.progressoft.brix.domino.gwtjackson.deser.array.ArrayJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.array.ByteArray2dJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.array.ByteArrayJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.collection.CollectionJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.collection.IterableJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.collection.ListJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.collection.SetJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.date.DateJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.date.SqlDateJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.date.SqlTimeJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.date.SqlTimestampJsonDeserializerTest;
import com.progressoft.brix.domino.gwtjackson.deser.map.key.*;
import com.progressoft.brix.domino.gwtjackson.deser.number.*;
import com.progressoft.brix.domino.gwtjackson.ser.*;
import com.progressoft.brix.domino.gwtjackson.ser.array.ArrayJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.array.ByteArray2dJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.array.ByteArrayJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.collection.CollectionJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.collection.IterableJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.collection.ListJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.collection.SetJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.date.DateJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.date.SqlDateJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.date.SqlTimeJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.date.SqlTimestampJsonSerializerTest;
import com.progressoft.brix.domino.gwtjackson.ser.map.key.*;
import com.progressoft.brix.domino.gwtjackson.ser.number.*;
import com.progressoft.brix.domino.gwtjackson.stream.impl.DefaultJsonReaderTest;
import com.progressoft.brix.domino.gwtjackson.stream.impl.DefaultJsonWriterTest;
import com.progressoft.brix.domino.gwtjackson.stream.impl.FastJsonWriterTest;
import com.progressoft.brix.domino.gwtjackson.stream.impl.NonBufferedJsonReaderTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GwtJacksonAptSuite extends GWTTestSuite{
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests for gwt-jackson-apt");
        suite.addTestSuite(GwtJacksonAptTest.class);

        // Stream - tests from gson
        suite.addTestSuite( DefaultJsonReaderTest.class );
        suite.addTestSuite( NonBufferedJsonReaderTest.class );
        suite.addTestSuite( DefaultJsonWriterTest.class );
        suite.addTestSuite( FastJsonWriterTest.class );

        // Default json serializers
        suite.addTestSuite( ArrayJsonSerializerTest.class );
        suite.addTestSuite( ByteArrayJsonSerializerTest.class );
        suite.addTestSuite( ByteArray2dJsonSerializerTest.class );
        suite.addTestSuite( BigDecimalJsonSerializerTest.class );
        suite.addTestSuite( BigIntegerJsonSerializerTest
                .class );
        suite.addTestSuite( BooleanJsonSerializerTest.class );
        suite.addTestSuite( ByteJsonSerializerTest.class );
        suite.addTestSuite( CharacterJsonSerializerTest.class );
        suite.addTestSuite( CollectionJsonSerializerTest.class );
        suite.addTestSuite( DateJsonSerializerTest.class );
        suite.addTestSuite( DoubleJsonSerializerTest.class );
        suite.addTestSuite( EnumJsonSerializerTest.class );
        suite.addTestSuite( FloatJsonSerializerTest.class );
        suite.addTestSuite( IntegerJsonSerializerTest.class );
        suite.addTestSuite( IterableJsonSerializerTest.class );
        suite.addTestSuite( ListJsonSerializerTest.class );
        suite.addTestSuite( LongJsonSerializerTest.class );
        suite.addTestSuite( SetJsonSerializerTest.class );
        suite.addTestSuite( ShortJsonSerializerTest.class );
        suite.addTestSuite( SqlDateJsonSerializerTest.class );
        suite.addTestSuite( SqlTimeJsonSerializerTest.class );
        suite.addTestSuite( SqlTimestampJsonSerializerTest.class );
        suite.addTestSuite( StringJsonSerializerTest.class );
        suite.addTestSuite( UUIDJsonSerializerTest.class );
        suite.addTestSuite( VoidJsonSerializerTest.class );

        // Default key serializers
        suite.addTestSuite( BooleanKeySerializerTest.class );
        suite.addTestSuite( CharacterKeySerializerTest.class );
        suite.addTestSuite( DateKeySerializerTest.class );
        suite.addTestSuite( EnumKeySerializerTest.class );
        suite.addTestSuite( SqlDateKeySerializerTest.class );
        suite.addTestSuite( SqlTimeKeySerializerTest.class );
        suite.addTestSuite( SqlTimestampKeySerializerTest.class );
        suite.addTestSuite( StringKeySerializerTest.class );
        suite.addTestSuite( UUIDKeySerializerTest.class );
        suite.addTestSuite( BigDecimalKeySerializerTest.class );
        suite.addTestSuite( BigIntegerKeySerializerTest.class );
        suite.addTestSuite( ByteKeySerializerTest.class );
        suite.addTestSuite( DoubleKeySerializerTest.class );
        suite.addTestSuite( FloatKeySerializerTest.class );
        suite.addTestSuite( IntegerKeySerializerTest.class );
        suite.addTestSuite( LongKeySerializerTest.class );
        suite.addTestSuite( ShortKeySerializerTest.class );

        // Default deserializers
        suite.addTestSuite( ArrayJsonDeserializerTest.class );
        suite.addTestSuite( ByteArrayJsonDeserializerTest.class );
        suite.addTestSuite( ByteArray2dJsonDeserializerTest.class );
        suite.addTestSuite( BigDecimalJsonDeserializerTest.class );
        suite.addTestSuite( BigIntegerJsonDeserializerTest.class );
        suite.addTestSuite( BooleanJsonDeserializerTest.class );
        suite.addTestSuite( ByteJsonDeserializerTest.class );
        suite.addTestSuite( CharacterJsonDeserializerTest.class );
        suite.addTestSuite( CollectionJsonDeserializerTest.class );
        suite.addTestSuite( DateJsonDeserializerTest.class );
        suite.addTestSuite( DoubleJsonDeserializerTest.class );
        suite.addTestSuite( EnumJsonDeserializerTest.class );
        suite.addTestSuite( FloatJsonDeserializerTest.class );
        suite.addTestSuite( IntegerJsonDeserializerTest.class );
        suite.addTestSuite( IterableJsonDeserializerTest.class );
        suite.addTestSuite( ListJsonDeserializerTest.class );
        suite.addTestSuite( LongJsonDeserializerTest.class );
        suite.addTestSuite( SetJsonDeserializerTest.class );
        suite.addTestSuite( ShortJsonDeserializerTest.class );
        suite.addTestSuite( SqlDateJsonDeserializerTest.class );
        suite.addTestSuite( SqlTimeJsonDeserializerTest.class );
        suite.addTestSuite( SqlTimestampJsonDeserializerTest.class );
        suite.addTestSuite( StringJsonDeserializerTest.class );
        suite.addTestSuite( UUIDJsonDeserializerTest.class );
        suite.addTestSuite( VoidJsonDeserializerTest.class );

        // Default key deserializers
        suite.addTestSuite( BooleanKeyDeserializerTest.class );
        suite.addTestSuite( CharacterKeyDeserializerTest.class );
        suite.addTestSuite( DateKeyDeserializerTest.class );
        suite.addTestSuite( EnumKeyDeserializerTest.class );
        suite.addTestSuite( SqlDateKeyDeserializerTest.class );
        suite.addTestSuite( SqlTimeKeyDeserializerTest.class );
        suite.addTestSuite( SqlTimestampKeyDeserializerTest.class );
        suite.addTestSuite( StringKeyDeserializerTest.class );
        suite.addTestSuite( UUIDKeyDeserializerTest.class );
        suite.addTestSuite( BigDecimalKeyDeserializerTest.class );
        suite.addTestSuite( BigIntegerKeyDeserializerTest.class );
        suite.addTestSuite( ByteKeyDeserializerTest.class );
        suite.addTestSuite( DoubleKeyDeserializerTest.class );
        suite.addTestSuite( FloatKeyDeserializerTest.class );
        suite.addTestSuite( IntegerKeyDeserializerTest.class );
        suite.addTestSuite( LongKeyDeserializerTest.class );
        suite.addTestSuite( ShortKeyDeserializerTest.class );
        return suite;
    }
}
