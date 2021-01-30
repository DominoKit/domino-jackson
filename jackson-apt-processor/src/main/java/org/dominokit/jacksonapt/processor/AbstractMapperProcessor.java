/*
 * Copyright © 2019 Dominokit
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;

/**
 * Abstract AbstractMapperProcessor class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractMapperProcessor extends AbstractProcessor {

  /** Constant <code>messager</code> */
  public static Messager messager;
  /** Constant <code>typeUtils</code> */
  public static Types typeUtils;
  /** Constant <code>filer</code> */
  public static Filer filer;
  /** Constant <code>elementUtils</code> */
  public static Elements elementUtils;

  public static ProcessingEnvironment environment;
  protected Set<? extends Element> mappers;
  protected Set<? extends Element> readers;
  protected Set<? extends Element> writers;

  /** {@inheritDoc} */
  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    environment = processingEnv;
    filer = processingEnv.getFiler();
    messager = processingEnv.getMessager();
    typeUtils = processingEnv.getTypeUtils();
    elementUtils = processingEnv.getElementUtils();
  }

  /** {@inheritDoc} */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      TypeRegistry.resetTypeRegistry();
      return false;
    }

    mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
    readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
    writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
    return doProcess(annotations, roundEnv);
  }

  /**
   * doProcess.
   *
   * @param annotations a {@link java.util.Set} object.
   * @param roundEnv a {@link javax.annotation.processing.RoundEnvironment} object.
   * @return a boolean.
   */
  protected abstract boolean doProcess(
      Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

  /**
   * handleError.
   *
   * @param e a {@link java.lang.Exception} object.
   */
  protected void handleError(Exception e) {
    StringWriter out = new StringWriter();
    e.printStackTrace(new PrintWriter(out));
    messager.printMessage(
        Diagnostic.Kind.ERROR, "error while creating source file " + out.getBuffer().toString());
  }

  /** {@inheritDoc} */
  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return supportedAnnotations().stream().map(Class::getCanonicalName).collect(Collectors.toSet());
  }

  /** {@inheritDoc} */
  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  /**
   * supportedAnnotations.
   *
   * @return a {@link java.util.List} object.
   */
  protected abstract List<Class<?>> supportedAnnotations();
}
