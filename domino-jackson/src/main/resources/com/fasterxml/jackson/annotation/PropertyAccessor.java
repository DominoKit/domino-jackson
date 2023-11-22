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
 * Enumeration used to define kinds of elements (called "property accessors") that annotations like
 * {@link JsonAutoDetect} apply to.
 *
 * <p>In addition to method types (GETTER/IS_GETTER, SETTER, CREATOR) and the field type (FIELD), 2
 * pseudo-types are defined for convenience: <code>ALWAYS</code> and <code>NONE</code>. These can be
 * used to indicate, all or none of available method types (respectively), for use by annotations
 * that takes <code>JsonMethod</code> argument.
 */
public enum PropertyAccessor {
  /**
   * Getters are methods used to get a POJO field value for serialization, or, under certain
   * conditions also for de-serialization. Latter can be used for effectively setting Collection or
   * Map values in absence of setters, iff returned value is not a copy but actual value of the
   * logical property.
   *
   * <p>Since version 1.3, this does <b>NOT</b> include "is getters" (methods that return boolean
   * and named 'isXxx' for property 'xxx'); instead, {@link #IS_GETTER} is used}.
   */
  GETTER,

  /** Setters are methods used to set a POJO value for deserialization. */
  SETTER,

  /**
   * Creators are constructors and (static) factory methods used to construct POJO instances for
   * deserialization
   */
  CREATOR,

  /**
   * Field refers to fields of regular Java objects. Although they are not really methods, addition
   * of optional field-discovery in version 1.1 meant that there was need to enable/disable their
   * auto-detection, and this is the place to add it in.
   */
  FIELD,

  /**
   * "Is getters" are getter-like methods that are named "isXxx" (instead of "getXxx" for getters)
   * and return boolean value (either primitive, or {@link Boolean}).
   */
  IS_GETTER,

  /** This pseudo-type indicates that none of accessors if affected. */
  NONE,

  /** This pseudo-type indicates that all accessors are affected. */
  ALL;

  private PropertyAccessor() {}

  public boolean creatorEnabled() {
    return (this == CREATOR) || (this == ALL);
  }

  public boolean getterEnabled() {
    return (this == GETTER) || (this == ALL);
  }

  public boolean isGetterEnabled() {
    return (this == IS_GETTER) || (this == ALL);
  }

  public boolean setterEnabled() {
    return (this == SETTER) || (this == ALL);
  }

  public boolean fieldEnabled() {
    return (this == FIELD) || (this == ALL);
  }
}
