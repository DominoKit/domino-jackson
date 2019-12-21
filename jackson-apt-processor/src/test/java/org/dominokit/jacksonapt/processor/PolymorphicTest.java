package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
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

public class PolymorphicTest {
	@JSONMapper
	interface PolymorphicBaseMapper extends ObjectMapper<PolymorphicBaseInterface> {
	}
	private PolymorphicBaseMapper BASEMAPPERINSTANCE = new PolymorphicTest_PolymorphicBaseMapperImpl();

	
	@JSONMapper
	interface ListPolymorphicParamMapper extends ObjectMapper<List<PolymorphicBaseInterface>> {
	}
	
	private ListPolymorphicParamMapper LISTMAPPER = new PolymorphicTest_ListPolymorphicParamMapperImpl();
	
	@JSONMapper
	interface PolymorphicGenericClassParamMapper extends ObjectMapper<PolymorphicGenericClass<PolymorphicBaseInterface>> {
	}
	
	private PolymorphicGenericClassParamMapper POLYMORPHICMAPPER = new PolymorphicTest_PolymorphicGenericClassParamMapperImpl();

	@Test
	public void testSubtypes() {
		PolymorphicChildClass pcc = new PolymorphicChildClass();
		pcc.i = 102;
		pcc.str = "test string";
		String json = BASEMAPPERINSTANCE.write(pcc);
		PolymorphicBaseInterface pcc_processed = BASEMAPPERINSTANCE.read(json);
		assertEquals(pcc_processed.getClass(), PolymorphicChildClass.class);
		assertEquals(pcc.str, ((PolymorphicChildClass)pcc_processed).str);
		
		PolymorphicChildClass2 pcc2= new PolymorphicChildClass2();
		pcc2.i = 222;
		pcc2.ii = 2233;
		json = BASEMAPPERINSTANCE.write(pcc2);
		pcc_processed = BASEMAPPERINSTANCE.read(json);
		assertEquals(pcc_processed.getClass(), PolymorphicChildClass2.class);
		assertEquals(pcc2.ii, ((PolymorphicChildClass2)pcc_processed).ii);
	}
	
	@Test
	public void testSubtypesWithList() {
		List<PolymorphicBaseInterface> list = new ArrayList<>();
		
		PolymorphicChildClass pcc = new PolymorphicChildClass();
		pcc.i = 102;
		pcc.str = "test string";
		
		list.add(pcc);
		
		PolymorphicChildClass2 pcc2= new PolymorphicChildClass2();
		pcc2.i = 222;
		pcc2.ii = 2233;
		
		list.add(pcc2);
		
		String json = LISTMAPPER.write(list);
		List<PolymorphicBaseInterface> list_processed = LISTMAPPER.read(json);
		assertEquals(list_processed.get(0).getClass(), PolymorphicChildClass.class);
		assertEquals(pcc.str, ((PolymorphicChildClass)list_processed.get(0)).str);
		
		
		assertEquals(list_processed.get(1).getClass(), PolymorphicChildClass2.class);
		assertEquals(pcc2.ii, ((PolymorphicChildClass2)list_processed.get(1)).ii);
	}
	
	@Test
	public void testSubtypeWithWildcards() {
		PolymorphicGenericClass<PolymorphicBaseInterface> pgc= new PolymorphicGenericClass<>();
		pgc.data = new PolymorphicChildClass();
		((PolymorphicChildClass)pgc.data).str = "hello";
	
		PolymorphicBaseInterface dataListItem1 = new PolymorphicChildClass();
		((PolymorphicChildClass)dataListItem1).str = "hello class";
		((PolymorphicChildClass)dataListItem1).i = 101;
		
		PolymorphicBaseInterface dataListItem2 = new PolymorphicChildClass2();
		((PolymorphicChildClass2)dataListItem2).ii = 1022;
		((PolymorphicChildClass2)dataListItem2).i = 101;
		
		pgc.dataList = Arrays.asList(dataListItem1, dataListItem2);

		SecondPolymorphicBaseClass dataListSecondItem = new SecondPolymorphicChildClass();
		((SecondPolymorphicChildClass)dataListSecondItem).str = "hello second class";
		pgc.dataListSecond = Arrays.asList(dataListSecondItem);
		
		Map<Integer, PolymorphicBaseInterface> map = new HashMap<Integer, PolymorphicBaseInterface>();
		PolymorphicBaseInterface dataMapItem1 = new PolymorphicChildClass();
		((PolymorphicChildClass)dataMapItem1).str = "hello object";
		((PolymorphicChildClass)dataMapItem1).i = 1010;
		
		PolymorphicBaseInterface dataMapItem2 = new PolymorphicChildClass2();
		((PolymorphicChildClass2)dataMapItem2).ii = 10223;
		((PolymorphicChildClass2)dataMapItem2).i = 1012;
		map.put(101, dataMapItem1);
		map.put(102, dataMapItem2);
		pgc.dataMap = map;
		
		String jsonStr = POLYMORPHICMAPPER.write(pgc);
		System.out.println(jsonStr);
		
		PolymorphicGenericClass<PolymorphicBaseInterface> pgc_processed = POLYMORPHICMAPPER.read(jsonStr);
		assertEquals(PolymorphicChildClass.class, pgc_processed.data.getClass());
		assertEquals(((PolymorphicChildClass)pgc.data).str,((PolymorphicChildClass)pgc_processed.data).str);
		
		assertEquals(2, pgc_processed.dataList.size());
		assertEquals(PolymorphicChildClass.class, pgc_processed.dataList.get(0).getClass());
		assertEquals(((PolymorphicChildClass)pgc.dataList.get(0)).str, ((PolymorphicChildClass)pgc_processed.dataList.get(0)).str);
		assertEquals(((PolymorphicChildClass)pgc.dataList.get(0)).i, ((PolymorphicChildClass)pgc_processed.dataList.get(0)).i);
		assertEquals(((PolymorphicChildClass2)pgc.dataList.get(1)).ii, ((PolymorphicChildClass2)pgc_processed.dataList.get(1)).ii);
		assertEquals(((PolymorphicChildClass2)pgc.dataList.get(1)).i, ((PolymorphicChildClass2)pgc_processed.dataList.get(1)).i);
		
		assertEquals(1, pgc_processed.dataListSecond.size());
		assertEquals(SecondPolymorphicChildClass.class, pgc_processed.dataListSecond.get(0).getClass());
		assertEquals(((SecondPolymorphicChildClass)pgc.dataListSecond.get(0)).str, ((SecondPolymorphicChildClass)pgc_processed.dataListSecond.get(0)).str);
		
		assertEquals(pgc.dataMap.size(), pgc_processed.dataMap.size());
		assertEquals(pgc.dataMap.get(101).getClass(), pgc_processed.dataMap.get(101).getClass());
		assertEquals(((PolymorphicChildClass)pgc.dataMap.get(101)).str, ((PolymorphicChildClass)pgc_processed.dataMap.get(101)).str);
		assertEquals(pgc.dataMap.get(102).getClass(), pgc_processed.dataMap.get(102).getClass());
		assertEquals(((PolymorphicChildClass2)pgc.dataMap.get(102)).ii, ((PolymorphicChildClass2)pgc_processed.dataMap.get(102)).ii);
	}
	
	
//  Use code bellow only when debugging the annotation processor	
//	@Test
	public void run() throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Path tempFolder = Files.createTempDirectory("gwt-jackson-apt-tmp", new FileAttribute[0]);
		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(tempFolder.toFile()));
		fileManager.setLocation(StandardLocation.SOURCE_OUTPUT, Arrays.asList(tempFolder.toFile()));
		CompilationTask task = compiler.getTask(
				new PrintWriter(System.out), 
				fileManager, 
				null, 
				null, 
				null, 
				fileManager.getJavaFileObjects(
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicTest.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicBaseInterface.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicBaseClass.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicChildClass.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicChildClass2.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/SecondPolymorphicBaseClass.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/SecondPolymorphicChildClass.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/SimpleGenericBeanObject.java"),
						new File("src/test/java/org/dominokit/jacksonapt/processor/PolymorphicGenericClass.java")));


		task.setProcessors(Arrays.asList(new ObjectMapperProcessor()));
		task.call();
	}

}
