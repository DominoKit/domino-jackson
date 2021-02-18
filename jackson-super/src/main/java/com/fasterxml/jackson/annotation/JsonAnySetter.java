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
 * Marker annotation that can be used to define a logical "any setter" mutator -- either using
 * non-static two-argument method (first argument name of property, second value to set) or a field
 * (of type {@link java.util.Map} or POJO) - to be used as a "fallback" handler for all otherwise
 * unrecognized properties found from JSON content. It is similar to {@link
 * javax.xml.bind.annotation.XmlAnyElement} in behavior; and can only be used to denote a single
 * property per type.
 *
 * <p>If used, all otherwise unmapped key-value pairs from JSON Object values are added using
 * mutator.
 *
 * <p>NOTE: ability to annotated fields was added in version 2.8; earlier only methods could be
 * annotated.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonAnySetter {
  /**
   * Optional argument that defines whether this annotation is active or not. The only use for value
   * 'false' if for overriding purposes. Overriding may be necessary when used with "mix-in
   * annotations" (aka "annotation overrides"). For most cases, however, default value of "true" is
   * just fine and should be omitted.
   *
   * @since 2.9
   * @return boolean
   */
  boolean enabled() default true;
}
