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
 * Annotation used with {@link JsonTypeInfo} to indicate sub types of serializable polymorphic
 * types, and to associate logical names used within JSON content (which is more portable than using
 * physical Java class names).
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.TYPE,
  ElementType.FIELD,
  ElementType.METHOD,
  ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonSubTypes {
  /**
   * Subtypes of the annotated type (annotated class, or property value type associated with the
   * annotated method). These will be checked recursively so that types can be defined by only
   * including direct subtypes.
   *
   * @return {@link Type[]}
   */
  public Type[] value();

  /**
   * Subtypes of the annotated type may have logical type name and names properties. When set to
   * true, logical type name and names are going to be checked for repeated values. Repeated values
   * are considered a definition violation during that check.
   *
   * @since 2.14
   */
  public boolean failOnRepeatedNames() default false;

  /**
   * Definition of a subtype, along with optional name. If name is missing, class of the type will
   * be checked for JsonTypeName annotation; and if that is also missing or empty, a default name
   * will be constructed by type id mechanism. Default name is usually based on class name.
   */
  public @interface Type {
    /**
     * Class of the subtype
     *
     * @return {@link Class}
     */
    public Class<?> value();

    /**
     * Logical type name used as the type identifier for the class
     *
     * @return String
     */
    public String name() default "";

    /**
     * (optional) Logical type names used as the type identifier for the class: used if more than
     * one type name should be associated with the same type.
     *
     * @since 2.12
     */
    public String[] names() default {};
  }
}
