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

import com.progressoft.brix.domino.jacksonapt.annotation.JSONMapper;
import com.progressoft.brix.domino.jacksonapt.annotation.JSONReader;
import com.progressoft.brix.domino.jacksonapt.annotation.JSONWriter;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class ServerJacksonAptProcessorTest extends JacksonTestCase {

    private SimpleBeanJsonMapperTester tester = SimpleBeanJsonMapperTester.INSTANCE;
    private PropertyNamingBean_MapperImpl PROPERTY_NAMING_MAPPER = new PropertyNamingBean_MapperImpl();

    @JSONMapper
    public interface SimpleBeanMapper extends ObjectReader<SimpleBean>, ObjectReaderTester<SimpleBean>,
            ObjectWriterTester<SimpleBean> {
        SimpleBeanMapper INSTANCE = new ServerJacksonAptProcessorTest_SimpleBeanMapperImpl();
    }

    @JSONWriter
    public interface SimpleBeanWriter extends ObjectWriter<SimpleBean>, ObjectWriterTester<SimpleBean> {
        SimpleBeanWriter INSTANCE = new ServerJacksonAptProcessorTest_SimpleBeanWriterImpl();
    }

    @JSONReader
    public interface SimpleBeanReader extends ObjectReader<SimpleBean>, ObjectReaderTester<SimpleBean> {
        SimpleBeanReader INSTANCE = new ServerJacksonAptProcessorTest_SimpleBeanReaderImpl();
    }

    @JSONMapper
    public interface BeanWithCollectionsTypeMapper extends ObjectReader<BeanWithCollectionsType>, ObjectWriter<BeanWithCollectionsType> {
        BeanWithCollectionsTypeMapper INSTANCE = new ServerJacksonAptProcessorTest_BeanWithCollectionsTypeMapperImpl();
    }
//
//    @JSONMapper
//    public interface PropertyNamingBeanMapper extends ObjectReaderTester<PropertyNamingBean>, ObjectWriterTester<PropertyNamingBean>, ObjectMapper<PropertyNamingBean> {
//
//    }

    @JSONMapper
    public interface BeanWithMapsTypeMapper extends ObjectReader<BeanWithMapsType>, ObjectWriter<BeanWithMapsType> {
        BeanWithMapsTypeMapper INSTANCE = new ServerJacksonAptProcessorTest_BeanWithMapsTypeMapperImpl();
    }

    @Test
    public void testDeserializeValue() {
        tester.testDeserializeValue(SimpleBeanMapper.INSTANCE);
        tester.testDeserializeValue(SimpleBeanReader.INSTANCE);
    }

    @Test
    public void testSerializeValue() {
        tester.testSerializeValue(SimpleBeanMapper.INSTANCE);
        tester.testSerializeValue(SimpleBeanWriter.INSTANCE);
    }

    @Test
    public void testValue() {
        final ObjectMapperTester<PropertyNamingBean> mapper = createMapper(PROPERTY_NAMING_MAPPER);
        PropertyNamingTester.INSTANCE.testValue(mapper);
        PropertyNamingTester.INSTANCE.testRead(mapper);
    }

    @Test
    public void testMapsMapper() {
        testMapsDeserializeValue();
        testMapsSerializeValue();
    }

    @Test
    public void testCollectionsDeserializeValue() {
        String input = "{" +
                "\"abstractCollection\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractQueue\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractSequentialList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"arrayList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"collection\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"enumSet\":[\"B\",null,\"C\",\"A\"]," +
                "\"hashSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"iterable\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"linkedHashSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"linkedList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"list\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"priorityQueue\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"queue\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"set\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"sortedSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"stack\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"treeSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"vector\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"listSet\":[[\"Hello\"],[\"World\"],[]]" +
                "}";

        BeanWithCollectionsType bean = BeanWithCollectionsTypeMapper.INSTANCE.read(input);
        assertThat(bean).isNotNull();

        Collection<String> baseExpectedList = Arrays.asList("Hello", null, "World", "!");
        Collection<String> baseExpectedCollectionWithoutNull = Arrays.asList("Hello", "World", "!");
        Collection<String> baseExpectedSet = new LinkedHashSet<>(Arrays.asList("Hello", null, "World", "!"));
        Collection<String> baseExpectedSortedSet = new TreeSet<>(Arrays.asList("!", "Hello", "World"));

        assertThat(baseExpectedList).isEqualTo(bean.abstractCollection);
        assertThat(baseExpectedList).isEqualTo(bean.abstractList);
        assertThat(baseExpectedList).isEqualTo(bean.abstractSequentialList);
        assertThat(baseExpectedList).isEqualTo(bean.arrayList);
        assertThat(baseExpectedList).isEqualTo(bean.collection);
        assertThat(baseExpectedList).isEqualTo(bean.iterable);
        assertThat(baseExpectedList).isEqualTo(bean.linkedList);
        assertThat(baseExpectedList).isEqualTo(bean.list);
        assertThat(baseExpectedList).isEqualTo(bean.stack);
        assertThat(baseExpectedList).isEqualTo(bean.vector);

        // LinkedList by default and we don't add null element
        assertThat(baseExpectedCollectionWithoutNull).isEqualTo(bean.queue);

        assertThat(baseExpectedSet).isEqualTo(bean.set);
        assertThat(baseExpectedSet).isEqualTo(bean.abstractSet);
        assertThat(baseExpectedSet).isEqualTo(bean.hashSet);
        assertThat(baseExpectedSet).isEqualTo(bean.linkedHashSet);

        assertThat(EnumSet.copyOf(Arrays.asList(AnEnum.A, AnEnum.B, AnEnum.C))).isEqualTo(bean.enumSet);

        assertThat(Arrays.deepEquals(new PriorityQueue<>(baseExpectedCollectionWithoutNull).toArray(), bean.abstractQueue
                .toArray())).isTrue();
        assertThat(Arrays.deepEquals(new PriorityQueue<>(baseExpectedCollectionWithoutNull).toArray(), bean.priorityQueue
                .toArray())).isTrue();

        assertThat(baseExpectedSortedSet).isEqualTo(bean.sortedSet);
        assertThat(baseExpectedSortedSet).isEqualTo(bean.treeSet);

        List<Set<String>> expectedListSet = Arrays.asList(Collections.singleton("Hello"), Collections
                .singleton("World"), Collections.<String>emptySet());
        assertThat(expectedListSet).isEqualTo(bean.listSet);
    }

    @Test
    public void testCollectionsSerializeValue() {

        ArrayList<String> list = new ArrayList<>(Arrays.asList("Hello", null, "World", "!"));
        LinkedList<String> linkedList = new LinkedList<>(list);
        List<Set<String>> listSet = Arrays.asList(Collections.singleton("Hello"), Collections
                .singleton("World"), Collections.<String>emptySet());

        BeanWithCollectionsType bean = new BeanWithCollectionsType();
        bean.abstractCollection = list;
        bean.abstractList = list;
        bean.abstractSequentialList = linkedList;
        bean.arrayList = list;
        bean.collection = list;
        bean.iterable = list;
        bean.linkedList = linkedList;
        bean.list = list;
        bean.stack = new Stack<>();
        bean.stack.add("Hello");
        bean.stack.add(null);
        bean.stack.add("World");
        bean.stack.add("!");
        bean.vector = new Vector<>(list);

        PriorityQueue<String> queue = new PriorityQueue<>(Arrays.asList("!", "World", "Hello"));
        bean.queue = queue;
        bean.abstractQueue = queue;
        bean.priorityQueue = queue;

        bean.enumSet = EnumSet.copyOf(Arrays.asList(AnEnum.A));

        bean.linkedHashSet = new LinkedHashSet<>(list);
        bean.abstractSet = bean.linkedHashSet;
        bean.set = bean.linkedHashSet;
        bean.hashSet = bean.linkedHashSet;

        bean.treeSet = new TreeSet<>(Arrays.asList("Hello", "World", "!"));
        bean.sortedSet = bean.treeSet;

        bean.listSet = listSet;

        String expected = "{" +
                "\"abstractCollection\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractQueue\":[\"!\",\"World\",\"Hello\"]," +
                "\"abstractSequentialList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"abstractSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"arrayList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"collection\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"enumSet\":[\"A\"]," +
                "\"hashSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"iterable\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"linkedHashSet\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"linkedList\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"list\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"priorityQueue\":[\"!\",\"World\",\"Hello\"]," +
                "\"queue\":[\"!\",\"World\",\"Hello\"]," +
                "\"set\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"sortedSet\":[\"!\",\"Hello\",\"World\"]," +
                "\"stack\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"treeSet\":[\"!\",\"Hello\",\"World\"]," +
                "\"vector\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"listSet\":[[\"Hello\"],[\"World\"],[]]" +
                "}";

        assertThat(expected).isEqualTo(BeanWithCollectionsTypeMapper.INSTANCE.write(bean));
    }

    public void testMapsDeserializeValue() {
        String input = "{" +
                "\"abstractMap\":{\"one\":1,\"four\":2,\"two\":2,\"three\":3,\"four\":4}," +
                "\"enumMap\":{\"A\":1,\"C\":3,\"B\":2,\"D\":4,\"D\":5}," +
                "\"hashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"identityHashMap\":{\"three\":3}," +
                "\"linkedHashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"map\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"sortedMap\":{\"four\":4,\"two\":2,\"one\":1,\"three\":3}," +
                "\"treeMap\":{\"one\":1,\"three\":3,\"four\":4,\"two\":2}" +
                "}";

        BeanWithMapsType bean = BeanWithMapsTypeMapper.INSTANCE.read(input);
        assertThat(bean).isNotNull();

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("one", 1);
        linkedHashMap.put("two", 2);
        linkedHashMap.put("three", 3);
        linkedHashMap.put("four", 4);

        assertThat(linkedHashMap).isEqualTo(bean.abstractMap);
        assertThat(linkedHashMap).isEqualTo(bean.hashMap);
        assertThat(linkedHashMap).isEqualTo(bean.linkedHashMap);
        assertThat(linkedHashMap).isEqualTo(bean.map);

        Map.Entry<String, Integer> entry = bean.identityHashMap.entrySet().iterator().next();
        assertThat("three").isEqualTo(entry.getKey());
        assertThat(3).isEqualTo((int) entry.getValue());

        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("one", 1);
        treeMap.put("two", 2);
        treeMap.put("three", 3);
        treeMap.put("four", 4);
        assertThat(treeMap).isEqualTo(bean.treeMap);
        assertThat(treeMap).isEqualTo(bean.sortedMap);

        EnumMap<AnEnum, Integer> enumMap = new EnumMap<>(AnEnum.class);
        enumMap.put(AnEnum.A, 1);
        enumMap.put(AnEnum.D, 5);
        enumMap.put(AnEnum.C, 3);
        enumMap.put(AnEnum.B, 2);
        assertThat(enumMap).isEqualTo(bean.enumMap);
    }

    public void testMapsSerializeValue() {

        BeanWithMapsType bean = new BeanWithMapsType();

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("one", 1);
        linkedHashMap.put("two", 2);
        linkedHashMap.put("three", 3);
        linkedHashMap.put("four", 4);

        bean.abstractMap = linkedHashMap;
        bean.hashMap = linkedHashMap;
        bean.linkedHashMap = linkedHashMap;
        bean.map = linkedHashMap;

        IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put("one", 1);

        bean.identityHashMap = identityHashMap;

        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("one", 1);
        treeMap.put("two", 2);
        treeMap.put("three", 3);
        treeMap.put("four", 4);

        bean.sortedMap = treeMap;
        bean.treeMap = treeMap;

        EnumMap<AnEnum, Integer> enumMap = new EnumMap<AnEnum, Integer>(AnEnum.class);
        enumMap.put(AnEnum.A, 1);
        enumMap.put(AnEnum.D, 4);
        enumMap.put(AnEnum.C, 3);
        enumMap.put(AnEnum.B, 2);

        bean.enumMap = enumMap;

        String expected = "{" +
                "\"abstractMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"enumMap\":{\"A\":1,\"B\":2,\"C\":3,\"D\":4}," +
                "\"hashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"identityHashMap\":{\"one\":1}," +
                "\"linkedHashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"map\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"sortedMap\":{\"four\":4,\"one\":1,\"three\":3,\"two\":2}," +
                "\"treeMap\":{\"four\":4,\"one\":1,\"three\":3,\"two\":2}" +
                "}";

        assertThat(expected).isEqualTo(BeanWithMapsTypeMapper.INSTANCE.write(bean));
    }


}
