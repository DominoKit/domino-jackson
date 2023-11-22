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

/**
 * Optional Boolean value ("nullean"). Needed just because Java annotations can not take 'null' as a
 * value (even as default), so there is no way to distinguish between explicit `true` and `false`,
 * and lack of choice (related: annotations are limited to primitives, so {@link Boolean} not
 * allowed as solution).
 *
 * <p>Note: although use of `true` and `false` would be more convenient, they can not be chosen
 * since they are Java keyword and compiler won't allow the choice. And since enum naming convention
 * suggests all-upper-case, that is what is done here.
 *
 * @since 2.6
 */
public enum OptBoolean {
  /**
   * Value that indicates that the annotation property is explicitly defined to be enabled, or true.
   */
  TRUE,

  /**
   * Value that indicates that the annotation property is explicitly defined to be disabled, or
   * false.
   */
  FALSE,

  /**
   * Value that indicates that the annotation property does NOT have an explicit definition of
   * enabled/disabled (or true/false); instead, a higher-level configuration value is used; or
   * lacking higher-level global setting, default.
   */
  DEFAULT;

  public Boolean asBoolean() {
    if (this == DEFAULT) return null;
    return (this == TRUE) ? Boolean.TRUE : Boolean.FALSE;
  }

  public boolean asPrimitive() {
    return (this == TRUE);
  }

  public static OptBoolean fromBoolean(Boolean b) {
    if (b == null) {
      return DEFAULT;
    }
    return b.booleanValue() ? TRUE : FALSE;
  }

  public static boolean equals(Boolean b1, Boolean b2) {
    if (b1 == null) {
      return (b2 == null);
    }
    return b1.equals(b2);
  }
}
