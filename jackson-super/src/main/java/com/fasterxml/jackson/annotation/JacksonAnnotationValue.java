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
package com.fasterxml.jackson.annotation;

import java.lang.annotation.Annotation;

/**
 * Marker interface used by value classes like {@link JsonFormat} that are used to contain
 * information from one of Jackson annotations, and can be directly instantiated from those
 * annotations, as well as programmatically constructed and possibly merged. The reason for such
 * marker is to allow generic handling of some of the annotations, as well as to allow easier
 * injection of configuration from sources other than annotations.
 *
 * @since 2.6
 */
public interface JacksonAnnotationValue<A extends Annotation> {
  /**
   * Introspection method that may be used to find actual annotation that may be used as the source
   * for value instance.
   *
   * @return {@link Class}
   */
  Class<A> valueFor();
}
