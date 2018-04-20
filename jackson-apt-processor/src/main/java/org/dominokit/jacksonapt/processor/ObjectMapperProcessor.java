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
package org.dominokit.jacksonapt.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.WildcardTypeName;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoService(Processor.class)
public class ObjectMapperProcessor extends AbstractProcessor {

    public static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf(Object.class);
    static Messager messager;
    public static Types typeUtils;
    static Filer filer;
    static Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
        mappers.forEach(this::generateMappers);

        Set<? extends Element> readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
        readers.forEach(this::generateMapperForReader);

        Set<? extends Element> writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
        writers.forEach(this::generateMapperForWriter);


        return true;
    }

    private void handleError(Exception e) {
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        messager.printMessage(Kind.ERROR, "error while creating source file " + out.getBuffer().toString());
    }

    private void generateMappers(Element element) {
        try {
            new BeanMapperGenerator().generate(element);
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void generateMapperForReader(Element element) {
        try {
            new BeanReaderGenerator().generate(element);
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void generateMapperForWriter(Element element) {
        try {
            new BeanWriterGenerator().generate(element);
        } catch (Exception e) {
            handleError(e);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream
                .of(JSONReader.class, JSONWriter.class, JSONMapper.class)
                .map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
