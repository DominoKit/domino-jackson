/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson.server;

import junit.framework.TestSuite;
import org.dominokit.jackson.server.deser.*;
import org.dominokit.jackson.server.deser.array.ArrayJsonDeserializerTest;
import org.dominokit.jackson.server.deser.array.ByteArray2dJsonDeserializerTest;
import org.dominokit.jackson.server.deser.array.ByteArrayJsonDeserializerTest;
import org.dominokit.jackson.server.deser.collection.CollectionJsonDeserializerTest;
import org.dominokit.jackson.server.deser.collection.IterableJsonDeserializerTest;
import org.dominokit.jackson.server.deser.collection.ListJsonDeserializerTest;
import org.dominokit.jackson.server.deser.collection.SetJsonDeserializerTest;
import org.dominokit.jackson.server.deser.date.DateJsonDeserializerTest;
import org.dominokit.jackson.server.deser.date.SqlDateJsonDeserializerTest;
import org.dominokit.jackson.server.deser.date.SqlTimeJsonDeserializerTest;
import org.dominokit.jackson.server.deser.date.SqlTimestampJsonDeserializerTest;
import org.dominokit.jackson.server.deser.map.key.*;
import org.dominokit.jackson.server.deser.number.*;
import org.dominokit.jackson.server.ser.*;
import org.dominokit.jackson.server.ser.array.ArrayJsonSerializerTest;
import org.dominokit.jackson.server.ser.array.ByteArray2dJsonSerializerTest;
import org.dominokit.jackson.server.ser.array.ByteArrayJsonSerializerTest;
import org.dominokit.jackson.server.ser.collection.CollectionJsonSerializerTest;
import org.dominokit.jackson.server.ser.collection.IterableJsonSerializerTest;
import org.dominokit.jackson.server.ser.collection.ListJsonSerializerTest;
import org.dominokit.jackson.server.ser.collection.SetJsonSerializerTest;
import org.dominokit.jackson.server.ser.date.DateJsonSerializerTest;
import org.dominokit.jackson.server.ser.date.SqlDateJsonSerializerTest;
import org.dominokit.jackson.server.ser.date.SqlTimeJsonSerializerTest;
import org.dominokit.jackson.server.ser.date.SqlTimestampJsonSerializerTest;
import org.dominokit.jackson.server.ser.map.key.*;
import org.dominokit.jackson.server.ser.number.*;
import org.dominokit.jackson.server.stream.impl.DefaultJsonReaderTest;
import org.dominokit.jackson.server.stream.impl.DefaultJsonWriterTest;
import org.dominokit.jackson.server.stream.impl.FastJsonWriterTest;
import org.dominokit.jackson.server.stream.impl.NonBufferedJsonReaderTest;
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
public class JacksonAptSuite extends TestSuite {}
