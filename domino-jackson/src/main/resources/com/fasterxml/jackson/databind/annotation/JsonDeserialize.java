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
package com.fasterxml.jackson.databind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Super source for {@link com.fasterxml.jackson.databind.annotation.JsonDeserialize} to remove the
 * use of java.lang.reflect.* classes
 *
 * <p>Annotation use for configuring deserialization aspects, by attaching to "setter" methods or
 * fields, or to value classes. When annotating value classes, configuration is used for instances
 * of the value class but can be overridden by more specific annotations (ones that attach to
 * methods or fields).
 *
 * <p>An example annotation would be:
 *
 * <pre>
 *  &#64;JsonDeserialize(using=MySerializer.class,
 *    as=MyHashMap.class,
 *    keyAs=MyHashKey.class,
 *    contentAs=MyHashValue.class
 *  )
 * </pre>
 *
 * <p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.TYPE,
  ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@com.fasterxml.jackson.annotation.JacksonAnnotation
public @interface JsonDeserialize {
  /**
   * <b>NOTE: </b>This is not yet used in GwtJackson. It is here to avoid GWT compilation errors
   * when using annotated shared classes.
   *
   * <p>Deserializer class to use for deserializing associated value. Depending on what is
   * annotated, value is either an instance of annotated class (used globablly anywhere where class
   * deserializer is needed); or only used for deserializing property access via a setter method.
   *
   * @return {@link Class}
   */
  Class<?> using() default Void.class;

  /**
   * <b>NOTE: </b>This is not yet used in GwtJackson. It is here to avoid GWT compilation errors
   * when using annotated shared classes.
   *
   * <p>Deserializer class to use for deserializing contents (elements of a Collection/array, values
   * of Maps) of annotated property. Can only be used on instances (methods, fields, constructors),
   * and not value classes themselves.
   *
   * @return {@link Class}
   */
  Class<?> contentUsing() default Void.class;

  /**
   * <b>NOTE: </b>This is not yet used in GwtJackson. It is here to avoid GWT compilation errors
   * when using annotated shared classes.
   *
   * <p>Deserializer class to use for deserializing Map keys of annotated property. Can only be used
   * on instances (methods, fields, constructors), and not value classes themselves.
   *
   * @return {@link Class}
   */
  Class<?> keyUsing() default Void.class;

  // // // Annotations for specifying intermediate Converters (2.2+)

  /**
   * <b>NOTE: </b>This is not yet used in GwtJackson. It is here to avoid GWT compilation errors
   * when using annotated shared classes.
   *
   * <p>Which helper object (if any) is to be used to convert from Jackson-bound intermediate type
   * (source type of converter) into actual property type (which must be same as result type of
   * converter). This is often used for two-step deserialization; Jackson binds data into suitable
   * intermediate type (like Tree representation), and converter then builds actual property type.
   *
   * @since 2.2
   * @return {@link Class}
   */
  Class<?> converter() default Void.class;

  /**
   * <b>NOTE: </b>This is not yet used in GwtJackson. It is here to avoid GWT compilation errors
   * when using annotated shared classes.
   *
   * <p>Similar to {@link #converter}, but used for values of structures types (List, arrays, Maps).
   *
   * @since 2.2
   * @return {@link Class}
   */
  Class<?> contentConverter() default Void.class;

  /**
   * Concrete type to deserialize values as, instead of type otherwise declared. Must be a subtype
   * of declared type; otherwise an exception may be thrown by deserializer.
   *
   * <p>Bogus type {@link Void} can be used to indicate that declared type is used as is (i.e. this
   * annotation property has no setting); this since annotation properties are not allowed to have
   * null value.
   *
   * <p>Note: if {@link #using} is also used it has precedence (since it directly specified
   * deserializer, whereas this would only be used to locate the deserializer) and value of this
   * annotation property is ignored.
   *
   * @return {@link Class}
   */
  Class<?> as() default Void.class;

  /**
   * Concrete type to deserialize keys of {@link java.util.Map} as, instead of type otherwise
   * declared. Must be a subtype of declared type; otherwise an exception may be thrown by
   * deserializer.
   *
   * @return {@link Class}
   */
  Class<?> keyAs() default Void.class;

  /**
   * Concrete type to deserialize content (elements of a Collection/array, values of Maps) values
   * as, instead of type otherwise declared. Must be a subtype of declared type; otherwise an
   * exception may be thrown by deserializer.
   *
   * @return {@link Class}
   */
  Class<?> contentAs() default Void.class;

  /**
   * Annotation for specifying if an external Builder class is to be used for building up
   * deserialized instances of annotated class. If so, an instance of referenced class is first
   * constructed (possibly using a Creator method; or if none defined, using default constructor),
   * and its "with-methods" are used for populating fields; and finally "build-method" is invoked to
   * complete deserialization.
   *
   * @return {@link Class}
   */
  Class<?> builder() default Void.class;
}
