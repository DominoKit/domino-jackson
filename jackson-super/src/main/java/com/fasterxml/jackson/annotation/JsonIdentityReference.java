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
 * Optional annotation that can be used for customizing details of a reference to Objects for which
 * "Object Identity" is enabled (see {@link JsonIdentityInfo}). The main use case is that of
 * enforcing use of Object Id even for the first time an Object is referenced, instead of first
 * instance being serialized as full POJO.
 *
 * @since 2.1
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
public @interface JsonIdentityReference {
  /**
   * Marker to indicate whether all referenced values are to be serialized as ids (true); or by
   * serializing the first encountered reference as POJO and only then as id (false).
   *
   * <p>Note that if value of 'true' is used, deserialization may require additional contextual
   * information, and possibly using a custom id resolver -- the default handling may not be
   * sufficient.
   *
   * @since 2.1
   */
  public boolean alwaysAsId() default false;
}
