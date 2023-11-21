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
 * Marker annotation that indicates that the value of annotated accessor (either field or "getter"
 * method [a method with non-void return type, no args]) is to be used as the single value to
 * serialize for the instance, instead of the usual method of collecting properties of value.
 * Usually value will be of a simple scalar type (String or Number), but it can be any serializable
 * type (Collection, Map or Bean).
 *
 * <p>At most one accessor of a <code>Class</code> can be annotated with this annotation; if more
 * than one is found, an exception may be thrown. Also, if method signature of annotated method is
 * not compatible with Getters, an exception may be thrown (whether exception is thrown or not is an
 * implementation detail (due to filtering during introspection, some annotations may be skipped)
 * and applications should not rely on specific behavior).
 *
 * <p>A typical usage is that of annotating <code>toString()</code> method so that returned String
 * value is used as the JSON serialization; and if deserialization is needed, there is matching
 * constructor or factory method annotated with {@link JsonCreator} annotation.
 *
 * <p>Boolean argument is only used so that sub-classes can "disable" annotation if necessary.
 *
 * <p>NOTE: when use for Java <code>enum</code>s, one additional feature is that value returned by
 * annotated method is also considered to be the value to deserialize from, not just JSON String to
 * serialize as. This is possible since set of Enum values is constant and it is possible to define
 * mapping, but can not be done in general for POJO types; as such, this is not used for POJO
 * deserialization.
 *
 * @see JsonCreator
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.METHOD,
  ElementType.FIELD // since 2.9
})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonValue {
  /**
   * Optional argument that defines whether this annotation is active or not. The only use for value
   * 'false' if for overriding purposes. Overriding may be necessary when used with "mix-in
   * annotations" (aka "annotation overrides"). For most cases, however, default value of "true" is
   * just fine and should be omitted.
   *
   * @return boolean
   */
  boolean value() default true;
}
