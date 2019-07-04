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
import org.dominokit.jacksonapt.processor.serialization.FieldSerializerChainBuilder;
import org.dominokit.jacksonapt.ser.*;
import org.dominokit.jacksonapt.ser.array.*;
import org.dominokit.jacksonapt.ser.array.dd.*;
import org.dominokit.jacksonapt.ser.map.MapJsonSerializer;
import org.dominokit.jacksonapt.ser.map.key.EnumKeySerializer;
import org.dominokit.jacksonapt.ser.map.key.NumberKeySerializer;
import org.dominokit.jacksonapt.ser.map.key.ToStringKeySerializer;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;

public class SerializerInstanceBuilderTest extends BaseInstanceBuilderTest {

    @Override
    MappersChainBuilder getMappersChainBuilder(TypeMirror beanType) {
        return new FieldSerializerChainBuilder(Type.getPackage(beanType), beanType);
    }

    @Test
    public void testBasicTypeFields() throws Exception {

        addFieldTest("stringField", result -> assertEquals(buildTestString("$T.getInstance()", StringJsonSerializer.class), result));

        addFieldTest("byteField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.ByteJsonSerializer.class), result));
        addFieldTest("boxedByteField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.ByteJsonSerializer.class), result));

        addFieldTest("shortField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("boxedShortField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.ShortJsonSerializer.class), result));

        addFieldTest("intField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("boxedIntField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));

        addFieldTest("longField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("boxedLongField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.LongJsonSerializer.class), result));

        addFieldTest("doubleField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("boxedDoubleField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));

        addFieldTest("floatField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("boxedFloatField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.FloatJsonSerializer.class), result));

        addFieldTest("booleanField", result -> assertEquals(buildTestString("$T.getInstance()", BooleanJsonSerializer.class), result));
        addFieldTest("boxedBooleanField", result -> assertEquals(buildTestString("$T.getInstance()", BooleanJsonSerializer.class), result));

        addFieldTest("charField", result -> assertEquals(buildTestString("$T.getInstance()", CharacterJsonSerializer.class), result));
        addFieldTest("boxedCharField", result -> assertEquals(buildTestString("$T.getInstance()", CharacterJsonSerializer.class), result));

        addFieldTest("bigIntegerField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));

        addFieldTest("bigDecimalField", result -> assertEquals(buildTestString("$T.getInstance()", BaseNumberJsonSerializer.BigDecimalJsonSerializer.class), result));

        addFieldTest("dateField", result -> assertEquals(buildTestString("$T.getInstance()", BaseDateJsonSerializer.DateJsonSerializer.class), result));

        addFieldTest("sqlDateField", result -> assertEquals(buildTestString("$T.getInstance()", BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));

        addFieldTest("timeField", result -> assertEquals(buildTestString("$T.getInstance()", BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));

        addFieldTest("timestampField", result -> assertEquals(buildTestString("$T.getInstance()", BaseDateJsonSerializer.SqlTimestampJsonSerializer.class), result));

        addFieldTest("voidField", result -> assertEquals(buildTestString("$T.getInstance()", VoidJsonSerializer.class), result));

        runTests();

    }

    @Test
    public void testEnumTypeField() throws Exception {
        addFieldTest("enumField", result -> assertEquals(buildTestString("$T.getInstance()", EnumJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testBasicTypeArrayFields() throws Exception {

        addFieldTest("byteFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveByteArrayJsonSerializer.class), result));
        addFieldTest("shortFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveShortArrayJsonSerializer.class), result));
        addFieldTest("intFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveIntegerArrayJsonSerializer.class), result));
        addFieldTest("longFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveLongArrayJsonSerializer.class), result));
        addFieldTest("doubleFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveDoubleArrayJsonSerializer.class), result));
        addFieldTest("floatFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveFloatArrayJsonSerializer.class), result));
        addFieldTest("booleanFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveBooleanArrayJsonSerializer.class), result));
        addFieldTest("charFieldArray", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveCharacterArrayJsonSerializer.class), result));


        addFieldTest("stringFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("boxedByteFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.ByteJsonSerializer.class), result));
        addFieldTest("boxedShortFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("boxedIntFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("boxedLongFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("boxedDoubleFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("boxedFloatFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("boxedBooleanFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("boxedCharFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("bigIntegerFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("bigDecimalFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseNumberJsonSerializer.BigDecimalJsonSerializer.class), result));
        addFieldTest("dateFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("sqlDateFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("timeFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("timestampFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlTimestampJsonSerializer.class), result));
        addFieldTest("voidFieldArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, VoidJsonSerializer.class), result));

        addFieldTest("enumArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", ArrayJsonSerializer.class, EnumJsonSerializer.class), result));

        runTests();

    }

    @Test
    public void testBasicType2DArrayFields() throws Exception {

        addFieldTest("byteFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveByteArray2dJsonSerializer.class), result));
        addFieldTest("shortFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveShortArray2dJsonSerializer.class), result));
        addFieldTest("intFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveIntegerArray2dJsonSerializer.class), result));
        addFieldTest("longFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveLongArray2dJsonSerializer.class), result));
        addFieldTest("doubleFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveDoubleArray2dJsonSerializer.class), result));
        addFieldTest("floatFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveFloatArray2dJsonSerializer.class), result));
        addFieldTest("booleanFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveBooleanArray2dJsonSerializer.class), result));
        addFieldTest("charFieldArray2d", result -> assertEquals(buildTestString("$T.getInstance()", PrimitiveCharacterArray2dJsonSerializer.class), result));


        addFieldTest("stringFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("boxedByteFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.ByteJsonSerializer.class), result));
        addFieldTest("boxedShortFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("boxedIntFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("boxedLongFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("boxedDoubleFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("boxedFloatFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("boxedBooleanFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("boxedCharFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("bigIntegerFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("bigDecimalFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseNumberJsonSerializer.BigDecimalJsonSerializer.class), result));
        addFieldTest("dateFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("sqlDateFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("timeFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("timestampFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, BaseDateJsonSerializer.SqlTimestampJsonSerializer.class), result));
        addFieldTest("voidFieldArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, VoidJsonSerializer.class), result));

        addFieldTest("enumArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", Array2dJsonSerializer.class, EnumJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testCollectionTypeField() throws Exception {

        addFieldTest("abstractList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("iterable", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", IterableJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractCollection", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractQueue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractSequentialList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("arrayList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("collection", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("enumSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("hashSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("linkedHashSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("linkedList", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("list", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("queue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("set", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("sortedSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("stack", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("treeSet", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("vector", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("priorityQueue", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, StringJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testArrayCollectionTypeField() throws Exception {
        addFieldTest("abstractCollectionArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractListArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("abstractQueueArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveIntegerArrayJsonSerializer.class), result));
        addFieldTest("abstractSequentialListArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("abstractSetArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("arrayListArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("collectionArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("hashSetArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, VoidJsonSerializer.class), result));
        addFieldTest("iterableArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", IterableJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("linkedHashSetArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveShortArrayJsonSerializer.class), result));
        addFieldTest("linkedListArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("listArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("queueArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("setArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveBooleanArrayJsonSerializer.class), result));
        addFieldTest("sortedSetArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveCharacterArrayJsonSerializer.class), result));
        addFieldTest("stackArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("treeSetArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("vectorArray", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("priorityQueueArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveLongArrayJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testArray2dCollectionTypeField() throws Exception {
        addFieldTest("abstractCollectionArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractListArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("abstractQueueArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveIntegerArray2dJsonSerializer.class), result));
        addFieldTest("abstractSequentialListArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("abstractSetArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("arrayListArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("collectionArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("hashSetArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, VoidJsonSerializer.class), result));
        addFieldTest("iterableArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", IterableJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("linkedHashSetArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveShortArray2dJsonSerializer.class), result));
        addFieldTest("linkedListArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("listArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("queueArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("setArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveBooleanArray2dJsonSerializer.class), result));
        addFieldTest("sortedSetArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveCharacterArray2dJsonSerializer.class), result));
        addFieldTest("stackArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("treeSetArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("vectorArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, Array2dJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("priorityQueueArray2d", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance())", CollectionJsonSerializer.class, PrimitiveLongArray2dJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testCollectionArrayTypeField() throws Exception {
        addFieldTest("arrayAbstractCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("arrayAbstractList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("arrayAbstractSequentialList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("arrayAbstractSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("arrayArrayList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("arrayCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("arrayHashSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, VoidJsonSerializer.class), result));
        addFieldTest("arrayIterable", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, IterableJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("arrayLinkedList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("longArrayList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("arrayQueue", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("arrayStack", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("arrayTreeSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("arrayVector", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", ArrayJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testCollectionArray2dTypeField() throws Exception {
        addFieldTest("array2dAbstractCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("array2dAbstractList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("array2dAbstractSequentialList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("array2dAbstractSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("array2dArrayList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("array2dCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.FloatJsonSerializer.class), result));
        addFieldTest("array2dHashSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, VoidJsonSerializer.class), result));
        addFieldTest("array2dIterable", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, IterableJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("array2dLinkedList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.SqlDateJsonSerializer.class), result));
        addFieldTest("array2dList", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.LongJsonSerializer.class), result));
        addFieldTest("array2dQueue", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BooleanJsonSerializer.class), result));
        addFieldTest("array2dStack", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, CharacterJsonSerializer.class), result));
        addFieldTest("array2dTreeSet", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));
        addFieldTest("array2dVector", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", Array2dJsonSerializer.class, CollectionJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testCollectionOfCollectionTypeField() throws Exception {

        addFieldTest("abstractListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("iterableCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", IterableJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractCollectionCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractQueueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractSequentialListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("abstractSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("arrayListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("collectionCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("hashSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, IterableJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("linkedHashSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("linkedListCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("listCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("queueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("setCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("sortedSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("stackCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("treeSetCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("vectorCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("priorityQueueCollection", result -> assertEquals(buildTestString("$T.newInstance($T.newInstance($T.getInstance()))", CollectionJsonSerializer.class, CollectionJsonSerializer.class, StringJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testMapTypeField() throws Exception {
        addFieldTest("abstractMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, ToStringKeySerializer.class, StringJsonSerializer.class), result));
        addFieldTest("enumMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, EnumKeySerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("hashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, NumberKeySerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("identityHashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, NumberKeySerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("linkedHashMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, NumberKeySerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("map", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, NumberKeySerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("sortedMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, ToStringKeySerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("treeMap", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.getInstance())", MapJsonSerializer.class, ToStringKeySerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testArrayMapTypeField() throws Exception {
        addFieldTest("abstractMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, ToStringKeySerializer.class, ArrayJsonSerializer.class, StringJsonSerializer.class), result));
        addFieldTest("enumMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, EnumKeySerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.IntegerJsonSerializer.class), result));
        addFieldTest("hashMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, NumberKeySerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.DoubleJsonSerializer.class), result));
        addFieldTest("identityHashMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, NumberKeySerializer.class, ArrayJsonSerializer.class, BaseDateJsonSerializer.DateJsonSerializer.class), result));
        addFieldTest("linkedHashMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, NumberKeySerializer.class, ArrayJsonSerializer.class, EnumJsonSerializer.class), result));
        addFieldTest("mapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, NumberKeySerializer.class, ArrayJsonSerializer.class, BaseDateJsonSerializer.SqlTimeJsonSerializer.class), result));
        addFieldTest("sortedMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, ToStringKeySerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.ShortJsonSerializer.class), result));
        addFieldTest("treeMapArray", result -> assertEquals(buildTestString("$T.newInstance($T.getInstance(), $T.newInstance($T.getInstance()))", MapJsonSerializer.class, ToStringKeySerializer.class, ArrayJsonSerializer.class, BaseNumberJsonSerializer.BigIntegerJsonSerializer.class), result));

        runTests();
    }

    @Test
    public void testNestedBeanTypeField() throws Exception {

        ClassName serializer = ClassName.bestGuess("org.dominokit.jacksonapt.processor.TestBeanBeanJsonSerializerImpl");
        TypeRegistry.registerSerializer("org.dominokit.jacksonapt.processor.TestBean", serializer);
        addFieldTest("testBean", result -> assertEquals(buildTestString("new $T()", serializer), result));

        runTests();
    }

    @Test
    public void testInheritedObjectsDeserializer() throws Exception{
        String json = "{\"address\":\"amman - jordan\",\"id\":1,\"name\":\"someone\"}";
        ChildObject target=new ChildObject();
        target.setId(1);
        target.setName("someone");
        target.setAddress("amman - jordan");
        String result = ChildObject.MAPPER.write(target);
        assertEquals(result, json);


        String jsonSubChild = "{\"age\":10,\"address\":\"amman - jordan\",\"id\":1,\"name\":\"someone\"}";
        ChildOfChild targetSubChild=new ChildOfChild();
        targetSubChild.setId(1);
        targetSubChild.setName("someone");
        targetSubChild.setAddress("amman - jordan");
        targetSubChild.setAge(10);
        String resultSubChild = ChildOfChild.MAPPER.write(targetSubChild);
        assertEquals(jsonSubChild, resultSubChild);
    }
}
