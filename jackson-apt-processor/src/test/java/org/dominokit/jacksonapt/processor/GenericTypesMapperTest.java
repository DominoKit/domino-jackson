package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
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
		assertEquals("{\"intField\":11,\"typeField\":\"String test\",\"str\":\"str\"}", GENERICMAPPERWITHSTRING.write(genericWithString));		
		
		SimpleGenericBeanObject<Integer> genericWithInteger = new SimpleGenericBeanObject<>(201, 42);
		assertEquals("{\"intField\":201,\"typeField\":42,\"str\":\"str\"}", GENERICMAPPERWITHINTEGER.write(genericWithInteger));
		
		genericWithInteger = GENERICMAPPERWITHINTEGER.read("{\"intField\":12,\"typeField\":142,\"str\":\"str\"}");
		assertEquals(12, genericWithInteger.intField);
		assertEquals(142, genericWithInteger.typeField.intValue());
		
	}
	
	@Test
	public void testGenericObjectWithCollection() {
		SimpleGenericBeanObject<List<Integer>> genericListOfInteger = new SimpleGenericBeanObject<>();
		genericListOfInteger.intField = 201;
		genericListOfInteger.typeField = Arrays.asList(1, 3, 5);
		assertEquals("{\"intField\":201,\"typeField\":[1,3,5],\"str\":\"str\"}", GENERICMAPPERWITHLISTOFINETEGR.write(genericListOfInteger));
		
		genericListOfInteger = GENERICMAPPERWITHLISTOFINETEGR.read("{\"intField\":8080,\"typeField\":[201,3,188,4],\"str\":\"test string\"}");
		assertEquals(8080, genericListOfInteger.intField);
		assertEquals(4, genericListOfInteger.typeField.size());
		assertEquals(201, genericListOfInteger.typeField.get(0).intValue());
		assertEquals(3, genericListOfInteger.typeField.get(1).intValue());
		assertEquals(188, genericListOfInteger.typeField.get(2).intValue());
		assertEquals(4, genericListOfInteger.typeField.get(3).intValue());
		assertEquals("test string", genericListOfInteger.str);
		
	}
	
	@Test
	public void testGenericObjectWithInheritance() {
		ChildOfGenericObject childOfGenericObjectWithString = new ChildOfGenericObject();
		childOfGenericObjectWithString.intField = 201;
		childOfGenericObjectWithString.typeField = "generic type field";
		childOfGenericObjectWithString.str = "string field";
		childOfGenericObjectWithString.subField ="field in child class";
		assertEquals("{\"subField\":\"field in child class\",\"intField\":201,\"typeField\":\"generic type field\",\"str\":\"string field\"}", CHILDOFGENERICMAPPER.write(childOfGenericObjectWithString));
		
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
		
		assertEquals("{\"subField\":\"generic child field\",\"intField\":11,\"typeField\":2012,\"str\":\"string field\"}", GENERICHILDOFGENERICOBJECTMAPPER.write(object));		
		
		object = GENERICHILDOFGENERICOBJECTMAPPER.read("{\"subField\":\"child field generic\",\"intField\":1101,\"typeField\":12,\"str\":\"field string\"}");
		assertEquals(1101, object.intField);
		assertEquals("child field generic", object.subField);
		assertEquals(12, object.typeField.intValue());
		assertEquals("field string", object.str);
	}
}
