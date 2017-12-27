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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import com.progressoft.brix.domino.gwtjackson.processor.Person;

@TestAnnotation
public class TestBean {

    public String stringField;
    public byte byteField;
    public Byte boxedByteField;
    public short shortField;
    public Short boxedShortField;
    public int intField;
    public Integer boxedIntField;
    public long longField;
    public Long boxedLongField;
    public double doubleField;
    public Double boxedDoubleField;
    public float floatField;
    public Float boxedFloatField;
    public boolean booleanField;
    public Boolean boxedBooleanField;
    public char charField;
    public Character boxedCharField;
    public BigInteger bigIntegerField;
    public BigDecimal bigDecimalField;
    public Date dateField;
    public java.sql.Date sqlDateField;
    public Time timeField;
    public Timestamp timestampField;
    public Void voidField;
    public Person person;

    public AnEnum enumField;

    public byte[] byteFieldArray;
    public short[] shortFieldArray;
    public int[] intFieldArray;
    public long[] longFieldArray;
    public double[] doubleFieldArray;
    public float[] floatFieldArray;
    public boolean[] booleanFieldArray;
    public char[] charFieldArray;

    public byte[][] byteFieldArray2d;
    public short[][] shortFieldArray2d;
    public int[][] intFieldArray2d;
    public long[][] longFieldArray2d;
    public double[][] doubleFieldArray2d;
    public float[][] floatFieldArray2d;
    public boolean[][] booleanFieldArray2d;
    public char[][] charFieldArray2d;

    public String[] stringFieldArray;
    public Byte[] boxedByteFieldArray;
    public Short[] boxedShortFieldArray;
    public Integer[] boxedIntFieldArray;
    public Long[] boxedLongFieldArray;
    public Double[] boxedDoubleFieldArray;
    public Float[] boxedFloatFieldArray;
    public Boolean[] boxedBooleanFieldArray;
    public Character[] boxedCharFieldArray;
    public BigInteger[] bigIntegerFieldArray;
    public BigDecimal[] bigDecimalFieldArray;
    public Date[] dateFieldArray;
    public java.sql.Date[] sqlDateFieldArray;
    public Time[] timeFieldArray;
    public Timestamp[] timestampFieldArray;
    public Void[] voidFieldArray;

    public String[][] stringFieldArray2d;
    public Byte[][] boxedByteFieldArray2d;
    public Short[][] boxedShortFieldArray2d;
    public Integer[][] boxedIntFieldArray2d;
    public Long[][] boxedLongFieldArray2d;
    public Double[][] boxedDoubleFieldArray2d;
    public Float[][] boxedFloatFieldArray2d;
    public Boolean[][] boxedBooleanFieldArray2d;
    public Character[][] boxedCharFieldArray2d;
    public BigInteger[][] bigIntegerFieldArray2d;
    public BigDecimal[][] bigDecimalFieldArray2d;
    public Date[][] dateFieldArray2d;
    public java.sql.Date[][] sqlDateFieldArray2d;
    public Time[][] timeFieldArray2d;
    public Timestamp[][] timestampFieldArray2d;
    public Void[][] voidFieldArray2d;

    public AnEnum[] enumArray;
    public AnEnum[][] enumArray2d;

    public AbstractCollection<String> abstractCollection;
    public AbstractList<String> abstractList;
    public AbstractQueue<String> abstractQueue;
    public AbstractSequentialList<String> abstractSequentialList;
    public AbstractSet<String> abstractSet;
    public ArrayList<String> arrayList;
    public Collection<String> collection;
    public EnumSet<AnEnum> enumSet;
    public HashSet<String> hashSet;
    public Iterable<String> iterable;
    public LinkedHashSet<String> linkedHashSet;
    public LinkedList<String> linkedList;
    public List<String> list;
    public PriorityQueue<String> priorityQueue;
    public Queue<String> queue;
    public Set<String> set;
    public SortedSet<String> sortedSet;
    public Stack<String> stack;
    public TreeSet<String> treeSet;
    public Vector<String> vector;

    public AbstractCollection<String[]> abstractCollectionArray;
    public AbstractList<Integer[]> abstractListArray;
    public AbstractQueue<int[]> abstractQueueArray;
    public AbstractSequentialList<Double[]> abstractSequentialListArray;
    public AbstractSet<AnEnum[]> abstractSetArray;
    public ArrayList<Date[]> arrayListArray;
    public Collection<Float[]> collectionArray;
    public HashSet<Void[]> hashSetArray;
    public Iterable<Short[]> iterableArray;
    public LinkedHashSet<short[]> linkedHashSetArray;
    public LinkedList<java.sql.Date[]> linkedListArray;
    public List<Long[]> listArray;
    public PriorityQueue<long[]> priorityQueueArray;
    public Queue<Boolean[]> queueArray;
    public Set<boolean[]> setArray;
    public SortedSet<char[]> sortedSetArray;
    public Stack<Character[]> stackArray;
    public TreeSet<BigInteger[]> treeSetArray;
    public Vector<Time[]> vectorArray;

    public AbstractCollection<String[][]> abstractCollectionArray2d;
    public AbstractList<Integer[][]> abstractListArray2d;
    public AbstractQueue<int[][]> abstractQueueArray2d;
    public AbstractSequentialList<Double[][]> abstractSequentialListArray2d;
    public AbstractSet<AnEnum[][]> abstractSetArray2d;
    public ArrayList<Date[][]> arrayListArray2d;
    public Collection<Float[][]> collectionArray2d;
    public HashSet<Void[][]> hashSetArray2d;
    public Iterable<Short[][]> iterableArray2d;
    public LinkedHashSet<short[][]> linkedHashSetArray2d;
    public LinkedList<java.sql.Date[][]> linkedListArray2d;
    public List<Long[][]> listArray2d;
    public PriorityQueue<long[][]> priorityQueueArray2d;
    public Queue<Boolean[][]> queueArray2d;
    public Set<boolean[][]> setArray2d;
    public SortedSet<char[][]> sortedSetArray2d;
    public Stack<Character[][]> stackArray2d;
    public TreeSet<BigInteger[][]> treeSetArray2d;
    public Vector<Time[][]> vectorArray2d;


    public AbstractCollection<String>[] arrayAbstractCollection;
    public AbstractList<Integer>[] arrayAbstractList;
    public AbstractSequentialList<Double>[] arrayAbstractSequentialList;
    public AbstractSet<AnEnum>[] arrayAbstractSet;
    public ArrayList<Date>[] arrayArrayList;
    public Collection<Float>[] arrayCollection;
    public HashSet<Void>[] arrayHashSet;
    public Iterable<Short>[] arrayIterable;
    public LinkedList<java.sql.Date>[] arrayLinkedList;
    public List<Long>[] longArrayList;
    public Queue<Boolean>[] arrayQueue;
    public Stack<Character>[] arrayStack;
    public TreeSet<BigInteger>[] arrayTreeSet;
    public Vector<Time>[] arrayVector;

    public AbstractCollection<String>[][] array2dAbstractCollection;
    public AbstractList<Integer>[][] array2dAbstractList;
    public AbstractSequentialList<Double>[][] array2dAbstractSequentialList;
    public AbstractSet<AnEnum>[][] array2dAbstractSet;
    public ArrayList<Date>[][] array2dArrayList;
    public Collection<Float>[][] array2dCollection;
    public HashSet<Void>[][] array2dHashSet;
    public Iterable<Short>[][] array2dIterable;
    public LinkedList<java.sql.Date>[][] array2dLinkedList;
    public List<Long>[][] array2dList;
    public Queue<Boolean>[][] array2dQueue;
    public Stack<Character>[][] array2dStack;
    public TreeSet<BigInteger>[][] array2dTreeSet;
    public Vector<Time>[][] array2dVector;


    public AbstractCollection<AbstractList<String>> abstractCollectionCollection;
    public AbstractList<AbstractQueue<String>> abstractListCollection;
    public AbstractQueue<AbstractSequentialList<String>> abstractQueueCollection;
    public AbstractSequentialList<AbstractSet<String>> abstractSequentialListCollection;
    public AbstractSet<ArrayList<String>> abstractSetCollection;
    public ArrayList<Collection<String>> arrayListCollection;
    public Collection<HashSet<String>> collectionCollection;
    public HashSet<Iterable<String>> hashSetCollection;
    public Iterable<LinkedHashSet<String>> iterableCollection;
    public LinkedHashSet<LinkedList<String>> linkedHashSetCollection;
    public LinkedList<List<String>> linkedListCollection;
    public List<PriorityQueue<String>> listCollection;
    public PriorityQueue<Queue<String>> priorityQueueCollection;
    public Queue<Set<String>> queueCollection;
    public Set<SortedSet<String>> setCollection;
    public SortedSet<Stack<String>> sortedSetCollection;
    public Stack<TreeSet<String>> stackCollection;
    public TreeSet<Vector<String>> treeSetCollection;
    public Vector<AbstractCollection<String>> vectorCollection;

    public AbstractMap<String, String> abstractMap;
    public EnumMap<AnEnum, Integer> enumMap;
    public HashMap<Integer, Double> hashMap;
    public IdentityHashMap<Long, Date> identityHashMap;
    public LinkedHashMap<Double, AnEnum> linkedHashMap;
    public Map<Short, Time> map;
    public SortedMap<String, Short> sortedMap;
    public TreeMap<String, BigInteger> treeMap;

    public AbstractMap<String, String[]> abstractMapArray;
    public EnumMap<AnEnum, Integer[]> enumMapArray;
    public HashMap<Integer, Double[]> hashMapArray;
    public IdentityHashMap<Long, Date[]> identityHashMapArray;
    public LinkedHashMap<Double, AnEnum[]> linkedHashMapArray;
    public Map<Short, Time[]> mapArray;
    public SortedMap<String, Short[]> sortedMapArray;
    public TreeMap<String, BigInteger[]> treeMapArray;

    public TestBean testBean;

}
