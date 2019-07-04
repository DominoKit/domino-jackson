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
package org.dominokit.jacksonapt.processor;

import com.squareup.javapoet.ClassName;
import org.dominokit.jacksonapt.deser.*;
import org.dominokit.jacksonapt.deser.array.*;
import org.dominokit.jacksonapt.deser.array.dd.*;
import org.dominokit.jacksonapt.deser.collection.*;
import org.dominokit.jacksonapt.deser.map.*;
import org.dominokit.jacksonapt.deser.map.key.BaseNumberKeyDeserializer;
import org.dominokit.jacksonapt.deser.map.key.EnumKeyDeserializer;
import org.dominokit.jacksonapt.deser.map.key.StringKeyDeserializer;
import org.dominokit.jacksonapt.processor.deserialization.FieldDeserializersChainBuilder;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static org.dominokit.jacksonapt.deser.BaseDateJsonDeserializer.*;
import static org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer.*;
import static org.dominokit.jacksonapt.deser.array.ArrayJsonDeserializer.ArrayCreator;
import static org.dominokit.jacksonapt.deser.array.dd.Array2dJsonDeserializer.Array2dCreator;

public class DeserializerInstanceBuilderTest extends BaseInstanceBuilderTest {

    @Override
    MappersChainBuilder getMappersChainBuilder(TypeMirror beanType) {
        return new FieldDeserializersChainBuilder(Type.getPackage(beanType), beanType);
    }

    @Test
    public void testBasicTypeFields() throws Exception {

        addFieldTest("stringField", result -> assertEquals(buildTestString("$T.getInstance()", StringJsonDeserializer.class), result));

        addFieldTest("byteField", result -> assertEquals(buildTestString("$T.getInstance()", ByteJsonDeserializer.class), result));
        addFieldTest("boxedByteField", result -> assertEquals(buildTestString("$T.getInstance()", ByteJsonDeserializer.class), result));

        addFieldTest("shortField", result -> assertEquals(buildTestString("$T.getInstance()", ShortJsonDeserializer.class), result));
        addFieldTest("boxedShortField", result -> assertEquals(buildTestString("$T.getInstance()", ShortJsonDeserializer.class), result));

        addFieldTest("intField", result -> assertEquals(buildTestString("$T.getInstance()", IntegerJsonDeserializer.class), result));
        addFieldTest("boxedIntField", result -> assertEquals(buildTestString("$T.getInstance()", IntegerJsonDeserializer.class), result));

        addFieldTest("longField", result -> assertEquals(buildTestString("$T.getInstance()", LongJsonDeserializer.class), result));
        addFieldTest("boxedLongField", result -> assertEquals(buildTestString("$T.getInstance()", LongJsonDeserializer.class), result));

        addFieldTest("doubleField", result -> assertEquals(buildTestString("$T.getInstance()", DoubleJsonDeserializer.class), result));
        addFieldTest("boxedDoubleField", result -> assertEquals(buildTestString("$T.getInstance()", DoubleJsonDeserializer.class), result));

        addFieldTest("floatField", result -> assertEquals(buildTestString("$T.getInstance()", FloatJsonDeserializer.class), result));
        addFieldTest("boxedFloatField", result -> assertEquals(buildTestString("$T.getInstance()", FloatJsonDeserializer.class), result));

        addFieldTest("booleanField", result -> assertEquals(buildTestString("$T.getInstance()", BooleanJsonDeserializer.class), result));
        addFieldTest("boxedBooleanField", result -> assertEquals(buildTestString("$T.getInstance()", BooleanJsonDeserializer.class), result));

        addFieldTest("charField", result -> assertEquals(buildTestString("$T.getInstance()", CharacterJsonDeserializer.class), result));
        addFieldTest("boxedCharField", result -> assertEquals(buildTestString("$T.getInstance()", CharacterJsonDeserializer.class), result));

        addFieldTest("bigIntegerField", result -> assertEquals(buildTestString("$T.getInstance()", BigIntegerJsonDeserializer.class), result));

        addFieldTest("bigDecimalField", result -> assertEquals(buildTestString("$T.getInstance()", BigDecimalJsonDeserializer.class), result));

        addFieldTest("dateField", result -> assertEquals(buildTestString("$T.getInstance()", DateJsonDeserializer.class), result));

        addFieldTest("sqlDateField", result -> assertEquals(buildTestString("$T.getInstance()", SqlDateJsonDeserializer.class), result));

        addFieldTest("timeField", result -> assertEquals(buildTestString("$T.getInstance()", SqlTimeJsonDeserializer.class), result));

        addFieldTest("timestampField", result -> assertEquals(buildTestString("$T.getInstance()", SqlTimestampJsonDeserializer.class), result));

        addFieldTest("voidField", result -> assertEquals(buildTestString("$T.getInstance()", VoidJsonDeserializer.class), result));

        runTests();

    }

    @Test
    public void testEnumTypeField() throws Exception {
        addFieldTest("enumField", result -> assertEquals(buildTestString("$T.newInstance($T.class)", EnumJsonDeserializer.class, AnEnum.class), result));
        runTests();
    }

    @Test
    public void testBasicTypeArrayFields() throws Exception {

        addFieldTest("byteFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveByteArrayJsonDeserializer.class), result));
        addFieldTest("shortFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveShortArrayJsonDeserializer.class), result));
        addFieldTest("intFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveIntegerArrayJsonDeserializer.class), result));
        addFieldTest("longFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveLongArrayJsonDeserializer.class), result));
        addFieldTest("doubleFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveDoubleArrayJsonDeserializer.class), result));
        addFieldTest("floatFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveFloatArrayJsonDeserializer.class), result));
        addFieldTest("booleanFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveBooleanArrayJsonDeserializer.class), result));
        addFieldTest("charFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveCharacterArrayJsonDeserializer.class), result));


        addFieldTest("stringFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, StringJsonDeserializer.class, ArrayCreator.class, String.class, String.class), result));
        addFieldTest("boxedByteFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, ByteJsonDeserializer.class, ArrayCreator.class, Byte.class, Byte.class), result));
        addFieldTest("boxedShortFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, ShortJsonDeserializer.class, ArrayCreator.class, Short.class, Short.class), result));
        addFieldTest("boxedIntFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, IntegerJsonDeserializer.class, ArrayCreator.class, Integer.class, Integer.class), result));
        addFieldTest("boxedLongFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, LongJsonDeserializer.class, ArrayCreator.class, Long.class, Long.class), result));
        addFieldTest("boxedDoubleFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, DoubleJsonDeserializer.class, ArrayCreator.class, Double.class, Double.class), result));
        addFieldTest("boxedFloatFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, FloatJsonDeserializer.class, ArrayCreator.class, Float.class, Float.class), result));
        addFieldTest("boxedBooleanFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, BooleanJsonDeserializer.class, ArrayCreator.class, Boolean.class, Boolean.class), result));
        addFieldTest("boxedCharFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, CharacterJsonDeserializer.class, ArrayCreator.class, Character.class, Character.class), result));
        addFieldTest("bigIntegerFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, BigIntegerJsonDeserializer.class, ArrayCreator.class, BigInteger.class, BigInteger.class), result));
        addFieldTest("bigDecimalFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, BigDecimalJsonDeserializer.class, ArrayCreator.class, BigDecimal.class, BigDecimal.class), result));
        addFieldTest("dateFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, DateJsonDeserializer.class, ArrayCreator.class, Date.class, Date.class), result));
        addFieldTest("sqlDateFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, SqlDateJsonDeserializer.class, ArrayCreator.class, java.sql.Date.class, java.sql.Date.class), result));
        addFieldTest("timeFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, SqlTimeJsonDeserializer.class, ArrayCreator.class, Time.class, Time.class), result));
        addFieldTest("timestampFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, SqlTimestampJsonDeserializer.class, ArrayCreator.class, Timestamp.class, Timestamp.class), result));
        addFieldTest("voidFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, VoidJsonDeserializer.class, ArrayCreator.class, Void.class, Void.class), result));

        addFieldTest("enumArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.class), ($T<$T>) $T[]::new)", ArrayJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class, ArrayCreator.class, AnEnum.class, AnEnum.class), result));

        runTests();

    }

    @Test
    public void testBasicType2DArrayFields() throws Exception {

        addFieldTest("byteFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveByteArray2dJsonDeserializer.class), result));
        addFieldTest("shortFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveShortArray2dJsonDeserializer.class), result));
        addFieldTest("intFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveIntegerArray2dJsonDeserializer.class), result));
        addFieldTest("longFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveLongArray2dJsonDeserializer.class), result));
        addFieldTest("doubleFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveDoubleArray2dJsonDeserializer.class), result));
        addFieldTest("floatFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveFloatArray2dJsonDeserializer.class), result));
        addFieldTest("booleanFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveBooleanArray2dJsonDeserializer.class), result));
        addFieldTest("charFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveCharacterArray2dJsonDeserializer.class), result));


        addFieldTest("stringFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, StringJsonDeserializer.class, Array2dCreator.class, String.class, String.class), result));
        addFieldTest("boxedByteFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, ByteJsonDeserializer.class, Array2dCreator.class, Byte.class, Byte.class), result));
        addFieldTest("boxedShortFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, ShortJsonDeserializer.class, Array2dCreator.class, Short.class, Short.class), result));
        addFieldTest("boxedIntFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, IntegerJsonDeserializer.class, Array2dCreator.class, Integer.class, Integer.class), result));
        addFieldTest("boxedLongFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, LongJsonDeserializer.class, Array2dCreator.class, Long.class, Long.class), result));
        addFieldTest("boxedDoubleFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, DoubleJsonDeserializer.class, Array2dCreator.class, Double.class, Double.class), result));
        addFieldTest("boxedFloatFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, FloatJsonDeserializer.class, Array2dCreator.class, Float.class, Float.class), result));
        addFieldTest("boxedBooleanFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, BooleanJsonDeserializer.class, Array2dCreator.class, Boolean.class, Boolean.class), result));
        addFieldTest("boxedCharFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, CharacterJsonDeserializer.class, Array2dCreator.class, Character.class, Character.class), result));
        addFieldTest("bigIntegerFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, BigIntegerJsonDeserializer.class, Array2dCreator.class, BigInteger.class, BigInteger.class), result));
        addFieldTest("bigDecimalFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, BigDecimalJsonDeserializer.class, Array2dCreator.class, BigDecimal.class, BigDecimal.class), result));
        addFieldTest("dateFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, DateJsonDeserializer.class, Array2dCreator.class, Date.class, Date.class), result));
        addFieldTest("sqlDateFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, SqlDateJsonDeserializer.class, Array2dCreator.class, java.sql.Date.class, java.sql.Date.class), result));
        addFieldTest("timeFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, SqlTimeJsonDeserializer.class, Array2dCreator.class, Time.class, Time.class), result));
        addFieldTest("timestampFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, SqlTimestampJsonDeserializer.class, Array2dCreator.class, Timestamp.class, Timestamp.class), result));
        addFieldTest("voidFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, VoidJsonDeserializer.class, Array2dCreator.class, Void.class, Void.class), result));

        addFieldTest("enumArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.class), ($T<$T>) (first, second) -> new $T[first][second])", Array2dJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class, Array2dCreator.class, AnEnum.class, AnEnum.class), result));

        runTests();
    }

    @Test
    public void testCollectionTypeField() throws Exception {

        addFieldTest("abstractList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", AbstractListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("iterable", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", IterableJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractCollection", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", AbstractCollectionJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractQueue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", AbstractQueueJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractSequentialList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", AbstractSequentialListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", AbstractSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("arrayList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("collection", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("enumSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.class))", EnumSetJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class), result));
        addFieldTest("hashSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", HashSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("linkedHashSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", LinkedHashSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("linkedList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", LinkedListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("list", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("queue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", QueueJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("set", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", SetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("sortedSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", SortedSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("stack", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", StackJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("treeSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", TreeSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("vector", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", VectorJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("priorityQueue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", PriorityQueueJsonDeserializer.class, StringJsonDeserializer.class), result));

        runTests();
    }

    @Test
    public void testArrayCollectionTypeField() throws Exception {
        addFieldTest("abstractCollectionArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                AbstractCollectionJsonDeserializer.class, ArrayJsonDeserializer.class, StringJsonDeserializer.class,
                ArrayCreator.class, String.class, String.class), result));

        addFieldTest("abstractListArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                AbstractListJsonDeserializer.class, ArrayJsonDeserializer.class, IntegerJsonDeserializer.class,
                ArrayCreator.class, Integer.class, Integer.class), result));

        addFieldTest("abstractQueueArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                AbstractQueueJsonDeserializer.class, PrimitiveIntegerArrayJsonDeserializer.class), result));

        addFieldTest("abstractSequentialListArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                AbstractSequentialListJsonDeserializer.class, ArrayJsonDeserializer.class, DoubleJsonDeserializer.class,
                ArrayCreator.class, Double.class, Double.class), result));

        addFieldTest("abstractSetArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.newInstance($T.class), ($T<$T>) $T[]::new))",
                AbstractSetJsonDeserializer.class, ArrayJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class,
                ArrayCreator.class, AnEnum.class, AnEnum.class), result));

        addFieldTest("arrayListArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                ArrayListJsonDeserializer.class, ArrayJsonDeserializer.class, DateJsonDeserializer.class,
                ArrayCreator.class, Date.class, Date.class), result));

        addFieldTest("collectionArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                CollectionJsonDeserializer.class, ArrayJsonDeserializer.class, FloatJsonDeserializer.class,
                ArrayCreator.class, Float.class, Float.class), result));

        addFieldTest("hashSetArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                HashSetJsonDeserializer.class, ArrayJsonDeserializer.class, VoidJsonDeserializer.class,
                ArrayCreator.class, Void.class, Void.class), result));

        addFieldTest("iterableArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                IterableJsonDeserializer.class, ArrayJsonDeserializer.class, ShortJsonDeserializer.class,
                ArrayCreator.class, Short.class, Short.class), result));

        addFieldTest("linkedHashSetArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                LinkedHashSetJsonDeserializer.class, PrimitiveShortArrayJsonDeserializer.class), result));

        addFieldTest("linkedListArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                LinkedListJsonDeserializer.class, ArrayJsonDeserializer.class, SqlDateJsonDeserializer.class,
                ArrayCreator.class, java.sql.Date.class, java.sql.Date.class), result));

        addFieldTest("listArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                ListJsonDeserializer.class, ArrayJsonDeserializer.class, LongJsonDeserializer.class,
                ArrayCreator.class, Long.class, Long.class), result));

        addFieldTest("queueArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                QueueJsonDeserializer.class, ArrayJsonDeserializer.class, BooleanJsonDeserializer.class,
                ArrayCreator.class, Boolean.class, Boolean.class), result));

        addFieldTest("setArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                SetJsonDeserializer.class, PrimitiveBooleanArrayJsonDeserializer.class), result));

        addFieldTest("sortedSetArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                SortedSetJsonDeserializer.class, PrimitiveCharacterArrayJsonDeserializer.class), result));

        addFieldTest("stackArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                StackJsonDeserializer.class, ArrayJsonDeserializer.class, CharacterJsonDeserializer.class,
                ArrayCreator.class, Character.class, Character.class), result));

        addFieldTest("treeSetArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                TreeSetJsonDeserializer.class, ArrayJsonDeserializer.class, BigIntegerJsonDeserializer.class,
                ArrayCreator.class, BigInteger.class, BigInteger.class), result));

        addFieldTest("vectorArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                VectorJsonDeserializer.class, ArrayJsonDeserializer.class, SqlTimeJsonDeserializer.class,
                ArrayCreator.class, Time.class, Time.class), result));

        addFieldTest("priorityQueueArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                PriorityQueueJsonDeserializer.class, PrimitiveLongArrayJsonDeserializer.class), result));

        runTests();
    }

    @Test
    public void testArray2dCollectionTypeField() throws Exception {
        addFieldTest("abstractCollectionArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                AbstractCollectionJsonDeserializer.class, Array2dJsonDeserializer.class, StringJsonDeserializer.class,
                Array2dCreator.class, String.class, String.class), result));

        addFieldTest("abstractListArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                AbstractListJsonDeserializer.class, Array2dJsonDeserializer.class, IntegerJsonDeserializer.class,
                Array2dCreator.class, Integer.class, Integer.class), result));

        addFieldTest("abstractQueueArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                AbstractQueueJsonDeserializer.class, PrimitiveIntegerArray2dJsonDeserializer.class), result));

        addFieldTest("abstractSequentialListArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                AbstractSequentialListJsonDeserializer.class, Array2dJsonDeserializer.class, DoubleJsonDeserializer.class,
                Array2dCreator.class, Double.class, Double.class), result));

        addFieldTest("abstractSetArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.newInstance($T.class), ($T<$T>) (first, second) -> new $T[first][second]))",
                AbstractSetJsonDeserializer.class, Array2dJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class,
                Array2dCreator.class, AnEnum.class, AnEnum.class), result));

        addFieldTest("arrayListArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                ArrayListJsonDeserializer.class, Array2dJsonDeserializer.class, DateJsonDeserializer.class,
                Array2dCreator.class, Date.class, Date.class), result));

        addFieldTest("collectionArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                CollectionJsonDeserializer.class, Array2dJsonDeserializer.class, FloatJsonDeserializer.class,
                Array2dCreator.class, Float.class, Float.class), result));

        addFieldTest("hashSetArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                HashSetJsonDeserializer.class, Array2dJsonDeserializer.class, VoidJsonDeserializer.class,
                Array2dCreator.class, Void.class, Void.class), result));

        addFieldTest("iterableArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                IterableJsonDeserializer.class, Array2dJsonDeserializer.class, ShortJsonDeserializer.class,
                Array2dCreator.class, Short.class, Short.class), result));

        addFieldTest("linkedHashSetArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())", LinkedHashSetJsonDeserializer.class, PrimitiveShortArray2dJsonDeserializer.class), result));

        addFieldTest("linkedListArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                LinkedListJsonDeserializer.class, Array2dJsonDeserializer.class, SqlDateJsonDeserializer.class,
                Array2dCreator.class, java.sql.Date.class, java.sql.Date.class), result));

        addFieldTest("listArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                ListJsonDeserializer.class, Array2dJsonDeserializer.class, LongJsonDeserializer.class,
                Array2dCreator.class, Long.class, Long.class), result));

        addFieldTest("queueArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                QueueJsonDeserializer.class, Array2dJsonDeserializer.class, BooleanJsonDeserializer.class,
                Array2dCreator.class, Boolean.class, Boolean.class), result));

        addFieldTest("setArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())", SetJsonDeserializer.class, PrimitiveBooleanArray2dJsonDeserializer.class), result));

        addFieldTest("sortedSetArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                SortedSetJsonDeserializer.class, PrimitiveCharacterArray2dJsonDeserializer.class), result));

        addFieldTest("stackArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                StackJsonDeserializer.class, Array2dJsonDeserializer.class, CharacterJsonDeserializer.class,
                Array2dCreator.class, Character.class, Character.class), result));

        addFieldTest("treeSetArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                TreeSetJsonDeserializer.class, Array2dJsonDeserializer.class, BigIntegerJsonDeserializer.class,
                Array2dCreator.class, BigInteger.class, BigInteger.class), result));

        addFieldTest("vectorArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance(), ($T<$T>) (first, second) -> new $T[first][second]))",
                VectorJsonDeserializer.class, Array2dJsonDeserializer.class, SqlTimeJsonDeserializer.class,
                Array2dCreator.class, Time.class, Time.class), result));

        addFieldTest("priorityQueueArray2d", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance())",
                PriorityQueueJsonDeserializer.class, PrimitiveLongArray2dJsonDeserializer.class), result));

        runTests();
    }

    @Test
    public void testCollectionArrayTypeField() throws Exception {
        addFieldTest("arrayAbstractCollection", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, AbstractCollectionJsonDeserializer.class, StringJsonDeserializer.class,
                ArrayCreator.class, AbstractCollection.class, String.class, AbstractCollection.class), result));

        addFieldTest("arrayAbstractList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, AbstractListJsonDeserializer.class, IntegerJsonDeserializer.class,
                ArrayCreator.class, AbstractList.class, Integer.class, AbstractList.class), result));

        addFieldTest("arrayAbstractSequentialList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, AbstractSequentialListJsonDeserializer.class, DoubleJsonDeserializer.class,
                ArrayCreator.class, AbstractSequentialList.class, Double.class, AbstractSequentialList.class), result));

        addFieldTest("arrayAbstractSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.newInstance($T.class)), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, AbstractSetJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class,
                ArrayCreator.class, AbstractSet.class, AnEnum.class, AbstractSet.class), result));

        addFieldTest("arrayArrayList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, ArrayListJsonDeserializer.class, DateJsonDeserializer.class,
                ArrayCreator.class, ArrayList.class, Date.class, ArrayList.class), result));

        addFieldTest("arrayCollection", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, CollectionJsonDeserializer.class, FloatJsonDeserializer.class,
                ArrayCreator.class, Collection.class, Float.class, Collection.class), result));

        addFieldTest("arrayHashSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, HashSetJsonDeserializer.class, VoidJsonDeserializer.class,
                ArrayCreator.class, HashSet.class, Void.class, HashSet.class), result));

        addFieldTest("arrayIterable", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, IterableJsonDeserializer.class, ShortJsonDeserializer.class,
                ArrayCreator.class, Iterable.class, Short.class, Iterable.class), result));

        addFieldTest("arrayLinkedList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, LinkedListJsonDeserializer.class, SqlDateJsonDeserializer.class,
                ArrayCreator.class, LinkedList.class, java.sql.Date.class, LinkedList.class), result));

        addFieldTest("longArrayList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, ListJsonDeserializer.class, LongJsonDeserializer.class,
                ArrayCreator.class, List.class, Long.class, List.class), result));

        addFieldTest("arrayQueue", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, QueueJsonDeserializer.class, BooleanJsonDeserializer.class,
                ArrayCreator.class, Queue.class, Boolean.class, Queue.class), result));

        addFieldTest("arrayStack", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, StackJsonDeserializer.class, CharacterJsonDeserializer.class,
                ArrayCreator.class, Stack.class, Character.class, Stack.class), result));

        addFieldTest("arrayTreeSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, TreeSetJsonDeserializer.class, BigIntegerJsonDeserializer.class,
                ArrayCreator.class, TreeSet.class, BigInteger.class, TreeSet.class), result));

        addFieldTest("arrayVector", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) $T[]::new)",
                ArrayJsonDeserializer.class, VectorJsonDeserializer.class, SqlTimeJsonDeserializer.class,
                ArrayCreator.class, Vector.class, Time.class, Vector.class), result));

        runTests();
    }

    @Test
    public void testCollectionArray2dTypeField() throws Exception {
        addFieldTest("array2dAbstractCollection", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, AbstractCollectionJsonDeserializer.class, StringJsonDeserializer.class,
                Array2dCreator.class, AbstractCollection.class, String.class, AbstractCollection.class), result));

        addFieldTest("array2dAbstractList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, AbstractListJsonDeserializer.class, IntegerJsonDeserializer.class,
                Array2dCreator.class, AbstractList.class, Integer.class, AbstractList.class), result));

        addFieldTest("array2dAbstractSequentialList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, AbstractSequentialListJsonDeserializer.class, DoubleJsonDeserializer.class,
                Array2dCreator.class, AbstractSequentialList.class, Double.class, AbstractSequentialList.class), result));

        addFieldTest("array2dAbstractSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.newInstance($T.class)), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, AbstractSetJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class,
                Array2dCreator.class, AbstractSet.class, AnEnum.class, AbstractSet.class), result));

        addFieldTest("array2dArrayList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, ArrayListJsonDeserializer.class, DateJsonDeserializer.class,
                Array2dCreator.class, ArrayList.class, Date.class, ArrayList.class), result));

        addFieldTest("array2dCollection", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, CollectionJsonDeserializer.class, FloatJsonDeserializer.class,
                Array2dCreator.class, Collection.class, Float.class, Collection.class), result));

        addFieldTest("array2dHashSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, HashSetJsonDeserializer.class, VoidJsonDeserializer.class,
                Array2dCreator.class, HashSet.class, Void.class, HashSet.class), result));

        addFieldTest("array2dIterable", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, IterableJsonDeserializer.class, ShortJsonDeserializer.class,
                Array2dCreator.class, Iterable.class, Short.class, Iterable.class), result));

        addFieldTest("array2dLinkedList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, LinkedListJsonDeserializer.class, SqlDateJsonDeserializer.class,
                Array2dCreator.class, LinkedList.class, java.sql.Date.class, LinkedList.class), result));

        addFieldTest("array2dList", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, ListJsonDeserializer.class, LongJsonDeserializer.class,
                Array2dCreator.class, List.class, Long.class, List.class), result));

        addFieldTest("array2dQueue", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, QueueJsonDeserializer.class, BooleanJsonDeserializer.class,
                Array2dCreator.class, Queue.class, Boolean.class, Queue.class), result));

        addFieldTest("array2dStack", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, StackJsonDeserializer.class, CharacterJsonDeserializer.class,
                Array2dCreator.class, Stack.class, Character.class, Stack.class), result));

        addFieldTest("array2dTreeSet", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, TreeSetJsonDeserializer.class, BigIntegerJsonDeserializer.class,
                Array2dCreator.class, TreeSet.class, BigInteger.class, TreeSet.class), result));

        addFieldTest("array2dVector", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.getInstance()), ($T<$T<$T>>) (first, second) -> new $T[first][second])",
                Array2dJsonDeserializer.class, VectorJsonDeserializer.class, SqlTimeJsonDeserializer.class,
                Array2dCreator.class, Vector.class, Time.class, Vector.class), result));

        runTests();
    }

    @Test
    public void testCollectionOfCollectionTypeField() throws Exception {

        addFieldTest("abstractCollectionCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", AbstractCollectionJsonDeserializer.class, AbstractListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", AbstractListJsonDeserializer.class, AbstractQueueJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractQueueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", AbstractQueueJsonDeserializer.class, AbstractSequentialListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractSequentialListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", AbstractSequentialListJsonDeserializer.class, AbstractSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("abstractSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", AbstractSetJsonDeserializer.class, ArrayListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("arrayListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayListJsonDeserializer.class, CollectionJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("collectionCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonDeserializer.class, HashSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("hashSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", HashSetJsonDeserializer.class, IterableJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("iterableCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", IterableJsonDeserializer.class, LinkedHashSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("linkedHashSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", LinkedHashSetJsonDeserializer.class, LinkedListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("linkedListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", LinkedListJsonDeserializer.class, ListJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("listCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ListJsonDeserializer.class, PriorityQueueJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("priorityQueueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", PriorityQueueJsonDeserializer.class, QueueJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("queueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", QueueJsonDeserializer.class, SetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("setCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", SetJsonDeserializer.class, SortedSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("sortedSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", SortedSetJsonDeserializer.class, StackJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("stackCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", StackJsonDeserializer.class, TreeSetJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("treeSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", TreeSetJsonDeserializer.class, VectorJsonDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("vectorCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", VectorJsonDeserializer.class, AbstractCollectionJsonDeserializer.class, StringJsonDeserializer.class), result));

        runTests();
    }

    @Test
    public void testMapTypeField() throws Exception {
        addFieldTest("abstractMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", AbstractMapJsonDeserializer.class, StringKeyDeserializer.class, StringJsonDeserializer.class), result));
        addFieldTest("enumMap", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.class), $T.getInstance())", EnumMapJsonDeserializer.class, EnumKeyDeserializer.class, AnEnum.class, IntegerJsonDeserializer.class), result));
        addFieldTest("hashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", HashMapJsonDeserializer.class, BaseNumberKeyDeserializer.IntegerKeyDeserializer.class, DoubleJsonDeserializer.class), result));
        addFieldTest("identityHashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", IdentityHashMapJsonDeserializer.class, BaseNumberKeyDeserializer.LongKeyDeserializer.class, DateJsonDeserializer.class), result));
        addFieldTest("linkedHashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.class))", LinkedHashMapJsonDeserializer.class, BaseNumberKeyDeserializer.DoubleKeyDeserializer.class, EnumJsonDeserializer.class, AnEnum.class), result));
        addFieldTest("map", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonDeserializer.class, BaseNumberKeyDeserializer.ShortKeyDeserializer.class, SqlTimeJsonDeserializer.class), result));
        addFieldTest("sortedMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", SortedMapJsonDeserializer.class, StringKeyDeserializer.class, ShortJsonDeserializer.class), result));
        addFieldTest("treeMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", TreeMapJsonDeserializer.class, StringKeyDeserializer.class, BigIntegerJsonDeserializer.class), result));

        runTests();
    }

    @Test
    public void testArrayMapTypeField() throws Exception {
        addFieldTest("abstractMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                AbstractMapJsonDeserializer.class, StringKeyDeserializer.class, ArrayJsonDeserializer.class, StringJsonDeserializer.class,
                ArrayCreator.class, String.class, String.class), result));

        addFieldTest("enumMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.newInstance($T.class), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                EnumMapJsonDeserializer.class, EnumKeyDeserializer.class, AnEnum.class, ArrayJsonDeserializer.class, IntegerJsonDeserializer.class,
                ArrayCreator.class, Integer.class, Integer.class), result));

        addFieldTest("hashMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                HashMapJsonDeserializer.class, BaseNumberKeyDeserializer.IntegerKeyDeserializer.class, ArrayJsonDeserializer.class, DoubleJsonDeserializer.class,
                ArrayCreator.class, Double.class, Double.class), result));

        addFieldTest("identityHashMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                IdentityHashMapJsonDeserializer.class, BaseNumberKeyDeserializer.LongKeyDeserializer.class, ArrayJsonDeserializer.class, DateJsonDeserializer.class,
                ArrayCreator.class, Date.class, Date.class), result));

        addFieldTest("linkedHashMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.newInstance($T.class), ($T<$T>) $T[]::new))",
                LinkedHashMapJsonDeserializer.class, BaseNumberKeyDeserializer.DoubleKeyDeserializer.class, ArrayJsonDeserializer.class, EnumJsonDeserializer.class, AnEnum.class,
                ArrayCreator.class, AnEnum.class, AnEnum.class), result));

        addFieldTest("mapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                MapJsonDeserializer.class, BaseNumberKeyDeserializer.ShortKeyDeserializer.class, ArrayJsonDeserializer.class, SqlTimeJsonDeserializer.class,
                ArrayCreator.class, Time.class, Time.class), result));

        addFieldTest("sortedMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                SortedMapJsonDeserializer.class, StringKeyDeserializer.class, ArrayJsonDeserializer.class, ShortJsonDeserializer.class,
                ArrayCreator.class, Short.class, Short.class), result));

        addFieldTest("treeMapArray", result -> assertEquals(buildTestString(
                "$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance(), ($T<$T>) $T[]::new))",
                TreeMapJsonDeserializer.class, StringKeyDeserializer.class, ArrayJsonDeserializer.class, BigIntegerJsonDeserializer.class,
                ArrayCreator.class, BigInteger.class, BigInteger.class), result));

        runTests();
    }

    @Test
    public void testNestedBeanTypeField() throws Exception {

        ClassName deserializer = ClassName.bestGuess("org.dominokit.jacksonapt.processor.TestBeanBeanJsonDeserializerImpl");
        TypeRegistry.registerSerializer("org.dominokit.jacksonapt.processor.TestBean", deserializer);
        addFieldTest("testBean", result -> assertEquals(buildTestString("new $T()", deserializer), result));

        runTests();
    }

    @Test
    public void testInheritedObjectsDeserializer() throws Exception{
        String json = "{\"id\":1, \"name\":\"someone\",\"address\":\"amman - jordan\"}";
        ChildObject deserializationResult = ChildObject.MAPPER.read(json);
        ChildObject expected=new ChildObject();
        expected.setId(1);
        expected.setName("someone");
        expected.setAddress("amman - jordan");
        assertEquals(deserializationResult, expected);

        String jsonSubChild = "{\"id\":1, \"name\":\"someone\",\"address\":\"amman - jordan\",\"age\":10}";
        ChildOfChild subChildResult = ChildOfChild.MAPPER.read(jsonSubChild);
        ChildOfChild expectedSubChild=new ChildOfChild();
        expectedSubChild.setId(1);
        expectedSubChild.setName("someone");
        expectedSubChild.setAddress("amman - jordan");
        expectedSubChild.setAge(10);
        assertEquals(subChildResult, expectedSubChild);
    }

}
