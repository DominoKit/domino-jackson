package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.junit.Test;


public class GenericTypesMapperTest {
	@JSONMapper
	interface GenericMapperWithString extends ObjectMapper<SimpleGenericBeanObject<String>> {
	}
	
	static GenericMapperWithString GENERICMAPPERWITHSTRING = new  GenericTypesMapperTest_GenericMapperWithStringImpl();
	

	@JSONMapper
	interface GenericMapperWithInteger extends ObjectMapper<SimpleGenericBeanObject<Integer>> {
	}
	
	static GenericMapperWithInteger GENERICMAPPERWITHINTEGER = new  GenericTypesMapperTest_GenericMapperWithIntegerImpl();
	
	
	@JSONMapper
	interface GenericMapperWithListOfInteger extends ObjectMapper<SimpleGenericBeanObject<List<Integer>>> {
	}
	
	static GenericMapperWithListOfInteger GENERICMAPPERWITHLISTOFINETEGR = new  GenericTypesMapperTest_GenericMapperWithListOfIntegerImpl();
	
	@JSONMapper
	interface ChildOfGenericMapper extends ObjectMapper<ChildOfGenericObject> {
	}
	
	static ChildOfGenericMapper CHILDOFGENERICMAPPER = new GenericTypesMapperTest_ChildOfGenericMapperImpl();
	
	@JSONMapper
	interface GenericChildOfGenericMapper extends ObjectMapper<GenericChildOfGenericObject<String, Integer>> {
	}
	
	static GenericChildOfGenericMapper GENERICHILDOFGENERICOBJECTMAPPER = new GenericTypesMapperTest_GenericChildOfGenericMapperImpl();
	
	@Test
	public void testGenericObject() {
		SimpleGenericBeanObject<String> genericWithString = new SimpleGenericBeanObject<>(11, "String test");
		genericWithString.genericArr = (List<String>[]) Array.newInstance(List.class, 2);
		genericWithString.genericArr[0] = Arrays.asList("hi", "there");
		genericWithString.genericArr[1] = Arrays.asList("my", "name", "is", "gwt-jackson-apt");
		assertEquals("{\"intField\":11,\"typeField\":\"String test\",\"genericArr\":[[\"hi\",\"there\"],[\"my\",\"name\",\"is\",\"gwt-jackson-apt\"]],\"str\":\"str\"}", GENERICMAPPERWITHSTRING.write(genericWithString));		
		
		SimpleGenericBeanObject<Integer> genericWithInteger = new SimpleGenericBeanObject<>(201, 42);
		genericWithInteger.genericArr = (List<Integer>[]) Array.newInstance(List.class, 2);
		genericWithInteger.genericArr[0] = Arrays.asList(101, 202);
		genericWithInteger.genericArr[1] = null;
		
		assertEquals("{\"intField\":201,\"typeField\":42,\"genericArr\":[[101,202],null],\"str\":\"str\"}", GENERICMAPPERWITHINTEGER.write(genericWithInteger));
		
		SimpleGenericBeanObject<Integer> genericWithIntegerDeser = GENERICMAPPERWITHINTEGER.read("{\"intField\":12,\"typeField\":142,\"genericArr\":[[1010, 2020], null],\"str\":\"str\"}");
		assertEquals(12, genericWithIntegerDeser.intField);
		assertEquals(142, genericWithIntegerDeser.typeField.intValue());
		assertEquals(Arrays.asList(1010, 2020), genericWithIntegerDeser.genericArr[0]);
		assertNull(genericWithIntegerDeser.genericArr[1]);
		
	}
	
	@Test
	public void testGenericObjectWithCollection() {
		SimpleGenericBeanObject<List<Integer>> genericListOfInteger = new SimpleGenericBeanObject<>();
		genericListOfInteger.intField = 201;
		genericListOfInteger.typeField = Arrays.asList(1, 3, 5);
		genericListOfInteger.genericArr = (List<List<Integer>>[]) Array.newInstance(List.class, 2);
		genericListOfInteger.genericArr[0] = Arrays.asList(Arrays.asList(1,2), Arrays.asList(10, 20));
		genericListOfInteger.genericArr[1] = Arrays.asList(Arrays.asList(1122,2211));
		
		assertEquals("{\"intField\":201,\"typeField\":[1,3,5],\"genericArr\":[[[1,2],[10,20]],[[1122,2211]]],\"str\":\"str\"}", GENERICMAPPERWITHLISTOFINETEGR.write(genericListOfInteger));
		
		genericListOfInteger = GENERICMAPPERWITHLISTOFINETEGR.read("{\"intField\":8080,\"typeField\":[201,3,188,4],\"genericArr\":[[[1,2],[10,20, 30]],[[101]]],\"str\":\"test string\"}");
		assertEquals(8080, genericListOfInteger.intField);
		assertEquals(4, genericListOfInteger.typeField.size());
		assertEquals(201, genericListOfInteger.typeField.get(0).intValue());
		assertEquals(3, genericListOfInteger.typeField.get(1).intValue());
		assertEquals(188, genericListOfInteger.typeField.get(2).intValue());
		assertEquals(4, genericListOfInteger.typeField.get(3).intValue());
		assertEquals(2, genericListOfInteger.genericArr.length);
		assertEquals(Arrays.asList(1,2), genericListOfInteger.genericArr[0].get(0));
		assertEquals(Arrays.asList(10,20, 30), genericListOfInteger.genericArr[0].get(1));
		assertEquals(Arrays.asList(101), genericListOfInteger.genericArr[1].get(0));
		assertEquals("test string", genericListOfInteger.str);
		
	}
	
	@Test
	public void testGenericObjectWithInheritance() {
		ChildOfGenericObject childOfGenericObjectWithString = new ChildOfGenericObject();
		childOfGenericObjectWithString.intField = 201;
		childOfGenericObjectWithString.typeField = "generic type field";
		childOfGenericObjectWithString.str = "string field";
		childOfGenericObjectWithString.subField ="field in child class";
		assertEquals("{\"subField\":\"field in child class\",\"intField\":201,\"typeField\":\"generic type field\",\"genericArr\":null,\"str\":\"string field\"}", CHILDOFGENERICMAPPER.write(childOfGenericObjectWithString));
		
		childOfGenericObjectWithString = CHILDOFGENERICMAPPER.read("{\"subField\":\"child class field\",\"intField\":2012,\"typeField\":\"field generic type\",\"str\":\"field string\"}");
		assertEquals(2012, childOfGenericObjectWithString.intField);
		assertEquals("child class field", childOfGenericObjectWithString.subField);
		assertEquals("field generic type", childOfGenericObjectWithString.typeField);
		assertEquals("field string", childOfGenericObjectWithString.str);
	}
	
	
	@Test
	public void testGenericChildOfGenericObjectInheritance() {
		GenericChildOfGenericObject<String, Integer> object = new GenericChildOfGenericObject<>();
		object.intField = 11;
		object.subField = "generic child field";
		object.typeField  = 2012;
		object.str = "string field";
		object.genericMap = new HashMap<>();
		object.genericMap.put("key", Arrays.asList(1,2, 3));
		
		assertEquals("{\"subField\":\"generic child field\",\"genericMap\":{\"key\":[1,2,3]},\"genericList\":null,\"intField\":11,\"typeField\":2012,\"genericArr\":null,\"str\":\"string field\"}", GENERICHILDOFGENERICOBJECTMAPPER.write(object));		
		
		GenericChildOfGenericObject<String, Integer> objectDeser = GENERICHILDOFGENERICOBJECTMAPPER.read("{\"subField\":\"child field generic\",\"genericMap\":{\"key\":[1,2,3]},\"genericList\":null,\"intField\":1101,\"typeField\":12,\"genericArr\":null,\"str\":\"string field\"}");
		assertEquals(1101, objectDeser.intField);
		assertEquals("child field generic", objectDeser.subField);
		assertEquals(12, objectDeser.typeField.intValue());
		assertEquals("string field", objectDeser.str);
		assertEquals(Arrays.asList(1, 2 ,3), objectDeser.genericMap.get("key"));
	}
}
