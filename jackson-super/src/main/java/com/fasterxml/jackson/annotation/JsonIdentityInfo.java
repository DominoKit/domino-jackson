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
 * Annotation used for indicating that values of annotated type or property should be serializing so
 * that instances either contain additional object identifier (in addition actual object
 * properties), or as a reference that consists of an object id that refers to a full serialization.
 * In practice this is done by serializing the first instance as full object and object identity,
 * and other references to the object as reference values.
 *
 * <p>There are two main approaches to generating object identifier: either using a generator
 * (either one of standard ones, or a custom generator), or using a value of a property. The latter
 * case is indicated by using a placeholder generator marker {@link
 * ObjectIdGenerators.PropertyGenerator}; former by using explicit generator. Object id has to be
 * serialized as a property in case of POJOs; object identity is currently NOT support for JSON
 * Array types (Java arrays or Lists) or Java Map types.
 *
 * <p>Finally, note that generator type of {@link ObjectIdGenerators.None} indicates that no Object
 * Id should be included or used: it is included to allow suppressing Object Ids using mix-in
 * annotations.
 *
 * @since 2.0
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
public @interface JsonIdentityInfo {
  /**
   * Name of JSON property in which Object Id will reside: also, if "from property" marker generator
   * is used, identifies property that will be accessed to get type id. If a property is used, name
   * must match its external name (one defined by annotation, or derived from accessor name as per
   * Java Bean Introspection rules).
   *
   * <p>Default value is <code>@id</code>.
   *
   * @return {@link String}
   */
  public String property() default "@id";

  /**
   * Generator to use for producing Object Identifier for objects: either one of pre-defined
   * generators from {@link ObjectIdGenerator}, or a custom generator. Defined as class to
   * instantiate.
   *
   * <p>Note that special type {@link ObjectIdGenerators.None} can be used to disable inclusion of
   * Object Ids.
   *
   * @return {@link Class} that extends from {@link ObjectIdGenerator}
   */
  public Class<? extends ObjectIdGenerator<?>> generator();

  /**
   * Resolver to use for producing POJO from Object Identifier.
   *
   * <p>Default value is {@link SimpleObjectIdResolver}
   *
   * @since 2.4
   * @return {@link Class}
   */
  public Class<? extends ObjectIdResolver> resolver() default SimpleObjectIdResolver.class;

  /**
   * Scope is used to define applicability of an Object Id: all ids must be unique within their
   * scope; where scope is defined as combination of this value and generator type. Comparison is
   * simple equivalence, meaning that both type generator type and scope class must be the same.
   *
   * <p>Scope is used for determining how many generators are needed; more than one scope is
   * typically only needed if external Object Ids have overlapping value domains (i.e. are only
   * unique within some limited scope)
   *
   * @return {@link Class}
   */
  public Class<?> scope() default Object.class;
}
