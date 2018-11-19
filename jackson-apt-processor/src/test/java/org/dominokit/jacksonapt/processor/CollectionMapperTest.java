package org.dominokit.jacksonapt.processor;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionMapperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void DeserializerTest() {
		List<Map<String, SimpleBeanObject>> lmp = SimpleBeanObject.MAPPER.read("[ {\"Dani\":{\"d\":5}; \"Lea\":{\"d\":15}}, {\"Teodor\":{\"d\":10}}]");
		assertThat(lmp.size()).isEqualTo(2);
		assertThat(lmp.get(0).get("Dani").d).isEqualTo(5);
		assertThat(lmp.get(0).get("Lea").d).isEqualTo(15);
		assertThat(lmp.get(0).get("None")).isNull();
		assertThat(lmp.get(1).get("Teodor").d).isEqualTo(10);
	}
	
	@Test
	public void SerializerTest() {
		Map<String, SimpleBeanObject> entity = new HashMap<>();
		entity.put("Dani", new SimpleBeanObject());
		assertThat(SimpleBeanObject.MAPPER.write(Arrays.asList(entity))).isEqualTo("[{\"Dani\":{\"d\":5}}]");
		
		
	}
}
