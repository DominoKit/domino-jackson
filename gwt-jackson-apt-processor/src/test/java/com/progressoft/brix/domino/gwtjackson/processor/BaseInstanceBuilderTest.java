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

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import com.squareup.javapoet.CodeBlock;
import junit.framework.TestCase;
import org.junit.Before;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class BaseInstanceBuilderTest extends TestCase {

    private Set<TestSubject> testSubjects;

    @Before
    public void setUp() throws Exception {
        testSubjects = new LinkedHashSet<>();
    }

    String buildTestString(String format, Object... classes) {
        return CodeBlock.builder().add(format, classes).build().toString();
    }


    void addFieldTest(String fieldName, FieldTester tester) throws Exception {
        testSubjects.add(new TestSubject(fieldName, tester));
    }

    void runTests() throws Exception {
        TestProcessor testProcessor = new TestProcessor();
        final boolean[] executed = {false};
        testProcessor.setDelegate((annotations, roundEnv, typeUtils, elementUtils, filer, messager) -> {
            Optional<? extends Element> element = roundEnv.getElementsAnnotatedWith(TestAnnotation.class).stream().findFirst();
            if (element.isPresent()) {
                executed[0] = true;
                ObjectMapperProcessor.typeUtils = typeUtils;
                ObjectMapperProcessor.elementUtils = elementUtils;
                ObjectMapperProcessor.messager = messager;

                TypeElement typeElement = (TypeElement) typeUtils.asElement(element.get().asType());
                Map<String, ? extends Element> elements = typeElement.getEnclosedElements().stream().filter(e -> ElementKind.FIELD.equals(e.getKind())).collect(Collectors.toMap(o -> o.getSimpleName().toString(), o -> o));
                testSubjects.forEach(testSubject -> {
                    if (elements.containsKey(testSubject.fieldName)) {
                        CodeBlock instance = getMappersChainBuilder(element.get().asType()).getInstance(elements.get(testSubject.fieldName));
                        testSubject.tester.testField(instance.toString());
                    } else {
                        fail("field not found [" + testSubject.fieldName + "]");
                    }
                });
            }
        });

        Compilation compilation = javac().withProcessors(testProcessor).compile(JavaFileObjects.forResource("TestBean.java"));
        assertThat(compilation.status()).isEqualTo(Compilation.Status.SUCCESS);
        assertTrue("the processor did not execute.", executed[0]);
    }

    abstract MappersChainBuilder getMappersChainBuilder(TypeMirror beanType);

    public interface FieldTester {
        void testField(String result);
    }

    protected static class TestSubject {
        private String fieldName;
        private FieldTester tester;

        public TestSubject(String fieldName, FieldTester tester) {
            this.fieldName = fieldName;
            this.tester = tester;
        }
    }

}
