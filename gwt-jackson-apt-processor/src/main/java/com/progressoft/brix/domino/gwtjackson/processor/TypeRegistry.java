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
package com.progressoft.brix.domino.gwtjackson.processor;

import com.google.gwt.core.client.JavaScriptObject;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.JsonSerializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseDateJsonDeserializer.DateJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseDateJsonDeserializer.SqlDateJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseDateJsonDeserializer.SqlTimeJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseDateJsonDeserializer.SqlTimestampJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseNumberJsonDeserializer.*;
import com.progressoft.brix.domino.gwtjackson.deser.*;
import com.progressoft.brix.domino.gwtjackson.deser.array.*;
import com.progressoft.brix.domino.gwtjackson.deser.array.dd.*;
import com.progressoft.brix.domino.gwtjackson.deser.collection.*;
import com.progressoft.brix.domino.gwtjackson.deser.map.*;
import com.progressoft.brix.domino.gwtjackson.deser.map.key.*;
import com.progressoft.brix.domino.gwtjackson.ser.BaseDateJsonSerializer.DateJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.BaseDateJsonSerializer.SqlDateJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.BaseDateJsonSerializer.SqlTimeJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.BaseDateJsonSerializer.SqlTimestampJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.BaseNumberJsonSerializer.*;
import com.progressoft.brix.domino.gwtjackson.ser.*;
import com.progressoft.brix.domino.gwtjackson.ser.array.*;
import com.progressoft.brix.domino.gwtjackson.ser.array.dd.*;
import com.progressoft.brix.domino.gwtjackson.ser.map.MapJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.map.key.*;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static com.progressoft.brix.domino.gwtjackson.processor.ObjectMapperProcessor.typeUtils;
public final class TypeRegistry {

    private static Map<String, ClassMapper> simpleTypes = new HashMap<>();
    private static Map<String, ClassMapper> keysMappers = new HashMap<>();
    private static Map<String, Class> collectionsDeserializers = new HashMap<>();
    private static Map<String, Class> mapDeserializers=new HashMap<>();

    static final ClassMapperFactory<JsonSerializer, JsonDeserializer> SIMPLE_MAPPER = new ClassMapperFactory<>();
    static final ClassMapperFactory<KeySerializer, KeyDeserializer> KEY_MAPPER = new ClassMapperFactory<>();

    //bas types mappers
    static {
        ArrayJsonDeserializer.ArrayCreator<String> arrayCreator = String[]::new;

        SIMPLE_MAPPER
                .forType(boolean.class)
                .serializer(BooleanJsonSerializer.class)
                .deserializer(BooleanJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(char.class)
                .serializer(CharacterJsonSerializer.class)
                .deserializer(CharacterJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(byte.class)
                .serializer(ByteJsonSerializer.class)
                .deserializer(ByteJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(double.class)
                .serializer(DoubleJsonSerializer.class)
                .deserializer(DoubleJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(float.class)
                .serializer(FloatJsonSerializer.class)
                .deserializer(FloatJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(int.class)
                .serializer(IntegerJsonSerializer.class)
                .deserializer(IntegerJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(long.class)
                .serializer(LongJsonSerializer.class)
                .deserializer(LongJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(short.class)
                .serializer(ShortJsonSerializer.class)
                .deserializer(ShortJsonDeserializer.class)
                .register(simpleTypes);

        // Common mappers
        SIMPLE_MAPPER
                .forType(String.class)
                .serializer(StringJsonSerializer.class)
                .deserializer(StringJsonDeserializer.class)
                .register(simpleTypes);
        SIMPLE_MAPPER
                .forType(Boolean.class)
                .serializer(BooleanJsonSerializer.class)
                .deserializer(BooleanJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Character.class)
                .serializer(CharacterJsonSerializer.class)
                .deserializer(CharacterJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(UUID.class)
                .serializer(UUIDJsonSerializer.class)
                .deserializer(UUIDJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Void.class)
                .serializer(VoidJsonSerializer.class)
                .deserializer(VoidJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(JavaScriptObject.class)
                .serializer(JavaScriptObjectJsonSerializer.class)
                .deserializer(JavaScriptObjectJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Enum.class)
                .serializer(EnumJsonSerializer.class)
                .deserializer(EnumJsonDeserializer.class)
                .register(simpleTypes);

        // Number mappers
        SIMPLE_MAPPER
                .forType(BigDecimal.class)
                .serializer(BigDecimalJsonSerializer.class)
                .deserializer(BigDecimalJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(BigInteger.class)
                .serializer(BigIntegerJsonSerializer.class)
                .deserializer(BigIntegerJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Byte.class)
                .serializer(ByteJsonSerializer.class)
                .deserializer(ByteJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Double.class)
                .serializer(DoubleJsonSerializer.class)
                .deserializer(DoubleJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Float.class)
                .serializer(FloatJsonSerializer.class)
                .deserializer(FloatJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Integer.class)
                .serializer(IntegerJsonSerializer.class)
                .deserializer(IntegerJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Long.class)
                .serializer(LongJsonSerializer.class)
                .deserializer(LongJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Short.class)
                .serializer(ShortJsonSerializer.class)
                .deserializer(ShortJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Number.class)
                .serializer(NumberJsonSerializer.class)
                .deserializer(NumberJsonDeserializer.class)
                .register(simpleTypes);

        // Date mappers
        SIMPLE_MAPPER
                .forType(Date.class)
                .serializer(DateJsonSerializer.class)
                .deserializer(DateJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(java.sql.Date.class)
                .serializer(SqlDateJsonSerializer.class)
                .deserializer(SqlDateJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Time.class)
                .serializer(SqlTimeJsonSerializer.class)
                .deserializer(SqlTimeJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Timestamp.class)
                .serializer(SqlTimestampJsonSerializer.class)
                .deserializer(SqlTimestampJsonDeserializer.class)
                .register(simpleTypes);

        // Iterable mappers
        SIMPLE_MAPPER
                .forType(Iterable.class)
                .serializer(IterableJsonSerializer.class)
                .deserializer(IterableJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Collection.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(CollectionJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(AbstractCollection.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(AbstractCollectionJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(AbstractList.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(AbstractListJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(AbstractQueue.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(AbstractQueueJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(AbstractSequentialList.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(AbstractSequentialListJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(AbstractSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(AbstractSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(ArrayList.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(ArrayListJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(EnumSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(EnumSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(HashSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(HashSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(LinkedHashSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(LinkedHashSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(LinkedList.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(LinkedListJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(List.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(ListJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(PriorityQueue.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(PriorityQueueJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Queue.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(QueueJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Set.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(SetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(SortedSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(SortedSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Stack.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(StackJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(TreeSet.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(TreeSetJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(Vector.class)
                .serializer(CollectionJsonSerializer.class)
                .deserializer(VectorJsonDeserializer.class)
                .register(simpleTypes);

        // Map mappers
        SIMPLE_MAPPER
                .forType(Map.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(MapJsonDeserializer.class)
                .register(simpleTypes);
        SIMPLE_MAPPER
                .forType(AbstractMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(AbstractMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(EnumMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(EnumMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(HashMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(HashMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(IdentityHashMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(IdentityHashMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(LinkedHashMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(LinkedHashMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(SortedMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(SortedMapJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(TreeMap.class)
                .serializer(MapJsonSerializer.class)
                .deserializer(TreeMapJsonDeserializer.class)
                .register(simpleTypes);

        // Primitive array mappers
        SIMPLE_MAPPER
                .forType(boolean[].class)
                .serializer(PrimitiveBooleanArrayJsonSerializer.class)
                .deserializer(PrimitiveBooleanArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(byte[].class)
                .serializer(PrimitiveByteArrayJsonSerializer.class)
                .deserializer(PrimitiveByteArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(char[].class)
                .serializer(PrimitiveCharacterArrayJsonSerializer.class)
                .deserializer(PrimitiveCharacterArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(double[].class)
                .serializer(PrimitiveDoubleArrayJsonSerializer.class)
                .deserializer(PrimitiveDoubleArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(float[].class)
                .serializer(PrimitiveFloatArrayJsonSerializer.class)
                .deserializer(PrimitiveFloatArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(int[].class)
                .serializer(PrimitiveIntegerArrayJsonSerializer.class)
                .deserializer(PrimitiveIntegerArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(long[].class)
                .serializer(PrimitiveLongArrayJsonSerializer.class)
                .deserializer(PrimitiveLongArrayJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(short[].class)
                .serializer(PrimitiveShortArrayJsonSerializer.class)
                .deserializer(PrimitiveShortArrayJsonDeserializer.class)
                .register(simpleTypes);

        // Primitive 2D Array mappers
        SIMPLE_MAPPER
                .forType(boolean[][].class)
                .serializer(PrimitiveBooleanArray2dJsonSerializer.class)
                .deserializer(PrimitiveBooleanArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(byte[][].class)
                .serializer(PrimitiveByteArray2dJsonSerializer.class)
                .deserializer(PrimitiveByteArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(char[][].class)
                .serializer(PrimitiveCharacterArray2dJsonSerializer.class)
                .deserializer(PrimitiveCharacterArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(double[][].class)
                .serializer(PrimitiveDoubleArray2dJsonSerializer.class)
                .deserializer(PrimitiveDoubleArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(float[][].class)
                .serializer(PrimitiveFloatArray2dJsonSerializer.class)
                .deserializer(PrimitiveFloatArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(int[][].class)
                .serializer(PrimitiveIntegerArray2dJsonSerializer.class)
                .deserializer(PrimitiveIntegerArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(long[][].class)
                .serializer(PrimitiveLongArray2dJsonSerializer.class)
                .deserializer(PrimitiveLongArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(short[][].class)
                .serializer(PrimitiveShortArray2dJsonSerializer.class)
                .deserializer(PrimitiveShortArray2dJsonDeserializer.class)
                .register(simpleTypes);

        SIMPLE_MAPPER
                .forType(String[].class)
                .serializer(ArrayJsonSerializer.class)
                .deserializer(PrimitiveShortArray2dJsonDeserializer.class)
                .register(simpleTypes);
        SIMPLE_MAPPER
                .forType(String[][].class)
                .serializer(Array2dJsonSerializer.class)
                .deserializer(Array2dJsonDeserializer.class)
                .register(simpleTypes);
    }

    // Map's key mappers
    static {

        KEY_MAPPER
                .forType(Object.class)
                .serializer(ObjectKeySerializer.class)
                .deserializer(StringKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Serializable.class)
                .serializer(ObjectKeySerializer.class)
                .deserializer(StringKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(BigDecimal.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.BigDecimalKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(BigInteger.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.BigIntegerKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Boolean.class)
                .serializer(BooleanKeySerializer.class)
                .deserializer(BooleanKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Byte.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.ByteKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Character.class)
                .serializer(ToStringKeySerializer.class)
                .deserializer(CharacterKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Date.class)
                .serializer(DateKeySerializer.class)
                .deserializer(BaseDateKeyDeserializer.DateKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Double.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.DoubleKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Enum.class)
                .serializer(EnumKeySerializer.class)
                .deserializer(EnumKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Float.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.FloatKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Integer.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.IntegerKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Long.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.LongKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Short.class)
                .serializer(NumberKeySerializer.class)
                .deserializer(BaseNumberKeyDeserializer.ShortKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(java.sql.Date.class)
                .serializer(DateKeySerializer.class)
                .deserializer(BaseDateKeyDeserializer.SqlDateKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Time.class)
                .serializer(DateKeySerializer.class)
                .deserializer(BaseDateKeyDeserializer.SqlTimeKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(Timestamp.class)
                .serializer(DateKeySerializer.class)
                .deserializer(BaseDateKeyDeserializer.SqlTimestampKeyDeserializer.class)
                .register(keysMappers);

        KEY_MAPPER
                .forType(String.class)
                .serializer(ToStringKeySerializer.class)
                .deserializer(StringKeyDeserializer.class)
                .register(keysMappers);


        KEY_MAPPER
                .forType(UUID.class)
                .serializer(UUIDKeySerializer.class)
                .deserializer(UUIDKeyDeserializer.class)
                .register(keysMappers);
    }

    // Collections deserializers
    static {

        collectionsDeserializers.put( AbstractCollection.class.getCanonicalName(), AbstractCollectionJsonDeserializer.class );
        collectionsDeserializers.put( AbstractList.class.getCanonicalName(),  AbstractListJsonDeserializer.class );
        collectionsDeserializers.put( AbstractQueue.class.getCanonicalName(),  AbstractQueueJsonDeserializer.class );
        collectionsDeserializers.put( AbstractSequentialList.class.getCanonicalName(),  AbstractSequentialListJsonDeserializer.class );
        collectionsDeserializers.put( AbstractSet.class.getCanonicalName(),  AbstractSetJsonDeserializer.class );
        collectionsDeserializers.put( ArrayList.class.getCanonicalName(),  ArrayListJsonDeserializer.class );
        collectionsDeserializers.put( Collection.class.getCanonicalName(),  CollectionJsonDeserializer.class );
        collectionsDeserializers.put( EnumSet.class.getCanonicalName(),  EnumSetJsonDeserializer.class );
        collectionsDeserializers.put( HashSet.class.getCanonicalName(),  HashSetJsonDeserializer.class );
        collectionsDeserializers.put( Iterable.class.getCanonicalName(),  IterableJsonDeserializer.class );
        collectionsDeserializers.put( LinkedHashSet.class.getCanonicalName(),  LinkedHashSetJsonDeserializer.class );
        collectionsDeserializers.put( LinkedList.class.getCanonicalName(),  LinkedListJsonDeserializer.class );
        collectionsDeserializers.put( List.class.getCanonicalName(),  ListJsonDeserializer.class );
        collectionsDeserializers.put( PriorityQueue.class.getCanonicalName(),  PriorityQueueJsonDeserializer.class );
        collectionsDeserializers.put( Queue.class.getCanonicalName(),  QueueJsonDeserializer.class );
        collectionsDeserializers.put( Set.class.getCanonicalName(),  SetJsonDeserializer.class );
        collectionsDeserializers.put( SortedSet.class.getCanonicalName(),  SortedSetJsonDeserializer.class );
        collectionsDeserializers.put( Stack.class.getCanonicalName(),  StackJsonDeserializer.class );
        collectionsDeserializers.put( TreeSet.class.getCanonicalName(),  TreeSetJsonDeserializer.class );
        collectionsDeserializers.put( Vector.class.getCanonicalName(),  VectorJsonDeserializer.class );

    }


    //Maps deserializers
    static {

        mapDeserializers.put(Map.class.getName(), MapJsonDeserializer.class);
        mapDeserializers.put(AbstractMap.class.getName(), AbstractMapJsonDeserializer.class);
        mapDeserializers.put(EnumMap.class.getName(), EnumMapJsonDeserializer.class);
        mapDeserializers.put(HashMap.class.getName(), HashMapJsonDeserializer.class);
        mapDeserializers.put(IdentityHashMap.class.getName(), IdentityHashMapJsonDeserializer.class);
        mapDeserializers.put(LinkedHashMap.class.getName(), LinkedHashMapJsonDeserializer.class);
        mapDeserializers.put(SortedMap.class.getName(), SortedMapJsonDeserializer.class);
        mapDeserializers.put(TreeMap.class.getName(), TreeMapJsonDeserializer.class);
    }

    static void register(ClassMapper mapper) {
        mapper.register(simpleTypes);
    }

    public static ClassMapper get(String typeName) {
        return simpleTypes.get(typeName);
    }

    public static Class<?> getSerializer(TypeMirror typeMirror) {
       return getSerializer(typeUtils.erasure(typeMirror).toString());
    }

    private static Class<?> getSerializer(String typeName) {
        if (simpleTypes.containsKey(typeName))
            return get(typeName).getSerializerMapper().serializer;
        throw new TypeSerializerNotFoundException(typeName);
    }

    public static Class<? extends KeySerializer> getKeySerializer(String typeName) {
        if (keysMappers.containsKey(typeName))
            return keysMappers.get(typeName).getSerializerMapper().serializer;
        throw new TypeSerializerNotFoundException(typeName);
    }

    public static Class<? extends KeyDeserializer> getKeyDeserializer(String typeName) {
        if (keysMappers.containsKey(typeName))
            return keysMappers.get(typeName).getDeserializerMapper().deserializer;
        throw new TypeDeserializerNotFoundException(typeName);
    }

    public static Class<?> getDeserializer(TypeMirror typeMirror) {
        return getDeserializer(typeUtils.erasure(typeMirror).toString());
    }

    private static Class<?> getDeserializer(String typeName) {
        if (simpleTypes.containsKey(typeName))
            return simpleTypes.get(typeName).getDeserializerMapper().deserializer;
        throw new TypeDeserializerNotFoundException(typeName);
    }

    public static Class<?> getCollectionDeserializer(TypeMirror typeMirror) {
        return getCollectionDeserializer(asNoneGeneric(typeMirror));
    }

    private static String asNoneGeneric(TypeMirror type) {
        return typeUtils.asElement(type).toString().replace("<E>", "");
    }

    private static Class<?> getCollectionDeserializer(String collectionType) {
        if (collectionsDeserializers.containsKey(collectionType))
            return collectionsDeserializers.get(collectionType);
        throw new TypeDeserializerNotFoundException(collectionType);
    }

    public static Class<?> getMapDeserializer(TypeMirror typeMirror) {
        return getMapDeserializer(asNoneGeneric(typeMirror));
    }


    private static Class<?> getMapDeserializer(String mapType) {
        if (mapDeserializers.containsKey(mapType))
            return mapDeserializers.get(mapType);
        throw new TypeDeserializerNotFoundException(mapType);
    }



    public interface HasSerializer<S> {
        HasDeserializer serializer(Class<? extends S> serializer);
    }

    public interface HasDeserializer<D> {
        CanRegister deserializer(Class<? extends D> deserializer);
    }

    public interface CanRegister {

        ClassMapper register(Map<String, ClassMapper> registry);
        ClassMapper build();
    }


    static class DeserializerMapper<D> {

        Class<? extends D> deserializer;

        DeserializerMapper(Class<? extends D> deserializer) {
            this.deserializer = deserializer;
        }

    }

    private static class SerializerMapper<S> {

        private Class<? extends S> serializer;

        private SerializerMapper(Class<? extends S> serializer) {
            this.serializer = serializer;
        }
    }

    static class ClassMapperFactory<S, D> {
        ClassMapper<S, D> forType(Class<?> clazz) {
            return new ClassMapper<>(clazz);
        }

        ClassMapper<S, D> forType(TypeName typeName) {
            return new ClassMapper<>(typeName);
        }

        ClassMapper<S, D> forType(String typeName) {
            return new ClassMapper<>(typeName);
        }
    }


    public static class ClassMapper<S, D> implements HasSerializer<S>, HasDeserializer<D>, CanRegister {

        private final String clazz;

        private SerializerMapper<S> serializer;

        private DeserializerMapper<D> deserializer;

        private ClassMapper(Class clazz) {
            this.clazz = clazz.getCanonicalName();
        }

        private ClassMapper(TypeName typeName) {
            this.clazz = typeName.toString();
        }

        private ClassMapper(String typeName) {
            this.clazz = typeName;
        }

        @Override
        public HasDeserializer<D> serializer(Class<? extends S> serializer) {
            this.serializer = new SerializerMapper<>(serializer);
            return this;
        }

        @Override
        public CanRegister deserializer(Class<? extends D> deserializer) {
            this.deserializer = new DeserializerMapper<>(deserializer);
            return this;
        }

        @Override
        public ClassMapper<S, D> register(Map<String, ClassMapper> registry) {
            registry.put(this.clazz, this);
            return this;
        }

        @Override
        public ClassMapper<S, D> build() {
            return this;
        }

        SerializerMapper<S> getSerializerMapper() {
            return serializer;
        }

        DeserializerMapper<D> getDeserializerMapper() {
            return deserializer;
        }

    }

    private static class TypeSerializerNotFoundException extends RuntimeException {
        TypeSerializerNotFoundException(String typeName) {
            super(typeName);
        }
    }

    private static class TypeDeserializerNotFoundException extends RuntimeException {
        TypeDeserializerNotFoundException(String typeName) {
            super(typeName);
        }
    }
}
