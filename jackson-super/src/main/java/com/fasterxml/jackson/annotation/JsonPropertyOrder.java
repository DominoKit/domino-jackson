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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be used to define ordering (possibly partial) to use when serializing object
 * properties. Properties included in annotation declaration will be serialized first (in defined
 * order), followed by any properties not included in the definition. Annotation definition will
 * override any implicit orderings (such as guarantee that Creator-properties are serialized before
 * non-creator properties)
 *
 * <p>Examples:
 *
 * <pre>
 *  // ensure that "id" and "name" are output before other properties
 *  &#64;JsonPropertyOrder({ "id", "name" })
 *  // order any properties that don't have explicit setting using alphabetic order
 *  &#64;JsonPropertyOrder(alphabetic=true)
 * </pre>
 *
 * <p>This annotation may or may not have effect on deserialization: for basic JSON handling there
 * is no effect, but for other supported data types (or structural conventions) there may be.
 *
 * <p>NOTE: annotation is allowed for properties, starting with 2.4, mostly to support alphabetic
 * ordering of {@link java.util.Map} entries.
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.TYPE,
  ElementType.METHOD,
  ElementType.CONSTRUCTOR,
  ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonPropertyOrder {
  /** Order in which properties of annotated object are to be serialized in. */
  public String[] value() default {};

  /**
   * Property that defines what to do regarding ordering of properties not explicitly included in
   * annotation instance. If set to true, they will be alphabetically ordered; if false, order is
   * undefined (default setting)
   */
  public boolean alphabetic() default false;
}
