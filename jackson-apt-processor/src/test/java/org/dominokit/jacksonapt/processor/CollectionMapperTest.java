package org.dominokit.jacksonapt.processor;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;
import org.dominokit.jacksonapt.registration.JsonRegistry;
import org.dominokit.jacksonapt.registration.Type;
import org.junit.Test;

public class CollectionMapperTest {

	@JSONMapper
	interface ListOfMapMapper extends ObjectMapper<List<Map<String, SimpleBeanObject>>> {
	}

	static ListOfMapMapper LISTOFMAPMAPPER = new CollectionMapperTest_ListOfMapMapperImpl();
	
	@JSONMapper
	interface SetMapper extends ObjectMapper<Set<SimpleBeanObject>> {
	}

	static SetMapper SETMAPPER = new CollectionMapperTest_SetMapperImpl();
	
	@JSONReader
	interface SetReader extends ObjectReader<Set<SimpleBeanObject>> {
	}
	
	@JSONWriter
	interface SetWriter extends ObjectWriter<Set<SimpleBeanObject>> {
	}
	
	
	@Test
	public void listOfMapMapperDeserializerTest() {
		List<Map<String, SimpleBeanObject>> simpleBeanObjectListOfMaps = LISTOFMAPMAPPER.read("[ {\"Dani\":{\"state\":1}; \"Lea\":{\"state\":2}}, {\"Teodor\":{\"state\":3}}]");
		assertThat(simpleBeanObjectListOfMaps.size()).isEqualTo(2);
		assertThat(simpleBeanObjectListOfMaps.get(0).get("Dani").state).isEqualTo(1);
		assertThat(simpleBeanObjectListOfMaps.get(0).get("Lea").state).isEqualTo(2);
		assertThat(simpleBeanObjectListOfMaps.get(1).get("Teodor").state).isEqualTo(3);
		assertThat(simpleBeanObjectListOfMaps.get(0).get("NotExists")).isNull();
		
	}
	
	@Test
	public void listOfMapMapperSerializerTest() {
		Map<String, SimpleBeanObject> stringToSimpleBeanObjectMap = new HashMap<>();
		stringToSimpleBeanObjectMap.put("Dani", new SimpleBeanObject(10));
		assertThat(LISTOFMAPMAPPER.write(Arrays.asList(stringToSimpleBeanObjectMap))).isEqualTo("[{\"Dani\":{\"state\":10}}]");
		
		stringToSimpleBeanObjectMap.put("Lea", new SimpleBeanObject(20));
		assertThat(LISTOFMAPMAPPER.write(Arrays.asList(stringToSimpleBeanObjectMap))).isEqualTo("[{\"Dani\":{\"state\":10},\"Lea\":{\"state\":20}}]");
		
	}
	
	@Test
	public void setMapperDeserializerTest() {
		Set<SimpleBeanObject> simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}]");
		assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
		assertThat(simpleBeanObjectSet.iterator().next().state).isEqualTo(1);
		
		simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}, {\"state\":2}]");
		assertThat(simpleBeanObjectSet.size()).isEqualTo(2);

		simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}, {\"state\":1}, {\"state\":1}]");
		assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
	}
	
	@Test
	public void setMapperSerializerTest() {
		Set<SimpleBeanObject> simpleBeanObjectSet = new HashSet<>();
		simpleBeanObjectSet.add(new SimpleBeanObject(1));
		simpleBeanObjectSet.add(new SimpleBeanObject(2));
		assertThat(SETMAPPER.write(simpleBeanObjectSet)).isEqualTo("[{\"state\":1},{\"state\":2}]");
		
		simpleBeanObjectSet.add(new SimpleBeanObject(2));
		simpleBeanObjectSet.add(new SimpleBeanObject(2));
		assertThat(SETMAPPER.write(simpleBeanObjectSet)).isEqualTo("[{\"state\":1},{\"state\":2}]");
	}
	
	@Test
	public void jsonRegistryTest() {
		JsonRegistry jsonRegistry = new TestJsonRegistry();
		
		ObjectMapper<List<Map<String, SimpleBeanObject>>> mapMapper = jsonRegistry.getMapper(Type.of(List.class).typeParam(Type.of(Map.class).typeParam(Type.of(String.class)).typeParam(Type.of(SimpleBeanObject.class)))); 
		assertThat(mapMapper).isNotNull();
		List<Map<String,SimpleBeanObject>> simpleBeanObjectListOfMaps = mapMapper.read("[{\"testObj\":{\"state\":10}}]");
		assertThat(simpleBeanObjectListOfMaps.size()).isEqualTo(1);
		assertThat(simpleBeanObjectListOfMaps.get(0).get("testObj")).isNotNull();
		assertThat(simpleBeanObjectListOfMaps.get(0).get("testObj").state).isEqualTo(10);
		
		ObjectReader<Set<SimpleBeanObject>> setReader = jsonRegistry.getReader(Type.of(Set.class).typeParam(Type.of(SimpleBeanObject.class)));
		assertThat(setReader).isNotNull();
		Set<SimpleBeanObject> simpleBeanObjectSet = setReader.read("[{\"state\":20}]");
		assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
		assertThat(simpleBeanObjectSet.iterator().next().state).isEqualTo(20);
		
		ObjectWriter<Set<SimpleBeanObject>> setWriter = jsonRegistry.getWriter(Type.of(Set.class).typeParam(Type.of(SimpleBeanObject.class)));
		assertThat(setWriter).isNotNull();
		assertThat(setWriter.write(new HashSet<>(Arrays.asList(new SimpleBeanObject(30))))).isEqualTo("[{\"state\":30}]");
		
	}
}
