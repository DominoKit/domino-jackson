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
package org.dominokit.jackson.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.WildcardTypeName;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.dominokit.jackson.annotation.JSONMapper;
import org.dominokit.jackson.annotation.JSONReader;
import org.dominokit.jackson.annotation.JSONWriter;

/**
 * ObjectMapperProcessor class. a Delegate class to generate different types of mappers for all
 * annotated types.
 */
@AutoService(Processor.class)
public class ObjectMapperProcessor extends AbstractMapperProcessor {

  /** Constant <code>DEFAULT_WILDCARD</code> */
  public static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf(Object.class);

  /** {@inheritDoc} */
  @Override
  protected boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    mappers.forEach(this::generateMappers);
    readers.forEach(this::generateMapperForReader);
    writers.forEach(this::generateMapperForWriter);
    return false;
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

  /** {@inheritDoc} */
  @Override
  protected List<Class<?>> supportedAnnotations() {
    return Arrays.asList(JSONReader.class, JSONWriter.class, JSONMapper.class);
  }
}
