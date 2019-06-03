package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

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
	interface PolymorphicBaseMapper extends ObjectMapper<PolymorphicBaseClass> {
	}
	private PolymorphicBaseMapper BASEMAPPERINSTANCE = new PolymorphicTest_PolymorphicBaseMapperImpl();

	@Test
	public void test() {
		PolymorphicChildClass pcc = new PolymorphicChildClass();
		pcc.i = 102;
		pcc.str = "test string";
		String json = BASEMAPPERINSTANCE.write(pcc);
		PolymorphicBaseClass pcc_processed = BASEMAPPERINSTANCE.read(json);
		assertEquals(pcc.i, pcc_processed.i);
		assertEquals(pcc_processed.getClass(), PolymorphicChildClass.class);
		assertEquals(pcc.str, ((PolymorphicChildClass)pcc_processed).str);
		
		PolymorphicChildClass2 pcc2= new PolymorphicChildClass2();
		pcc2.i = 222;
		pcc2.ii = 2233;
		json = BASEMAPPERINSTANCE.write(pcc2);
		pcc_processed = BASEMAPPERINSTANCE.read(json);
		assertEquals(pcc2.i, pcc_processed.i);
		assertEquals(pcc_processed.getClass(), PolymorphicChildClass2.class);
		assertEquals(pcc2.ii, ((PolymorphicChildClass2)pcc_processed).ii);
	}
}
