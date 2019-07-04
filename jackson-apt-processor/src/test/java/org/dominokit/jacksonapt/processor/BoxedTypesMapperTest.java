package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.junit.Test;

public class BoxedTypesMapperTest {
	@JSONMapper
	interface BoxedVoidMapper extends ObjectMapper<Void> {
		
	}
	
	static BoxedVoidMapper BOXEDVOIDMAPPER = new BoxedTypesMapperTest_BoxedVoidMapperImpl();
	
	@JSONMapper
	interface BoxedIntegerMapper extends ObjectMapper<Integer> {
		
	}
	
	static BoxedIntegerMapper BOXEDINTEGERMAPPER = new BoxedTypesMapperTest_BoxedIntegerMapperImpl();
	
	@JSONMapper
	interface ListOfMapMapper extends ObjectMapper<List<Map<Integer, SimpleGenericBeanObject<Double>>>> {
	}
	
	static  ListOfMapMapper LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER = new BoxedTypesMapperTest_ListOfMapMapperImpl();
	
	@Test
	public void testBoxedTypesMapper() {
		String jsonStr = BOXEDVOIDMAPPER.write(null);
		assertEquals(null, BOXEDVOIDMAPPER.read(jsonStr));
		
		
		jsonStr = BOXEDINTEGERMAPPER.write(new Integer(20));
		assertEquals(new Integer(20), BOXEDINTEGERMAPPER.read(jsonStr));
		
		Map<Integer, SimpleGenericBeanObject<Double>> map = new HashMap<>();
		map.put(new Integer(15), new SimpleGenericBeanObject<Double>(222, 18.1));
		
		List<Map<Integer, SimpleGenericBeanObject<Double>>> list = new ArrayList<>();
		list.add(map);
		
		jsonStr = LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER.write(list);
		
		List<Map<Integer, SimpleGenericBeanObject<Double>>> deserializedList = LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER.read(jsonStr);
		assertEquals(1, deserializedList.size());
		assertNotNull(deserializedList.get(0).get(15));
		assertEquals(222, deserializedList.get(0).get(15).intField);
		assertEquals(Double.valueOf(18.1), deserializedList.get(0).get(15).typeField);
	}
}


