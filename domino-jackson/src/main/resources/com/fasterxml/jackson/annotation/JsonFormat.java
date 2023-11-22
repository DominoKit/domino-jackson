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
 * General-purpose annotation used for configuring details of how values of properties are to be
 * serialized. Unlike most other Jackson annotations, annotation does not have specific universal
 * interpretation: instead, effect depends on datatype of property being annotated (or more
 * specifically, deserializer and serializer being used).
 *
 * <p>Common uses include choosing between alternate representations -- for example, whether {@link
 * java.util.Date} is to be serialized as number (Java timestamp) or String (such as ISO-8601
 * compatible time value) -- as well as configuring exact details with {@link #pattern} property.
 *
 * <p>As of Jackson 2.6, known special handling includes:
 *
 * <ul>
 *   <li>{@link java.util.Date}: Shape can be {@link Shape#STRING} or {@link Shape#NUMBER}; pattern
 *       may contain {@link java.text.SimpleDateFormat}-compatible pattern definition.
 *   <li>Can be used on Classes (types) as well, for modified default behavior, possibly overridden
 *       by per-property annotation
 *   <li>{@link Enum}s: Shapes {@link Shape#STRING} and {@link Shape#NUMBER} can be used to change
 *       between numeric (index) and textual (name or <code>toString()</code>); but it is also
 *       possible to use {@link Shape#OBJECT} to serialize (but not deserialize) {@link Enum}s as
 *       JSON Objects (as if they were POJOs). NOTE: serialization as JSON Object only works with
 *       class annotation; will not work as per-property annotation.
 *   <li>{@link java.util.Collection}s can be serialized as (and deserialized from) JSON Objects, if
 *       {@link Shape#OBJECT} is used. NOTE: can ONLY be used as class annotation; will not work as
 *       per-property annotation.
 *   <li>{@link Number} subclasses can be serialized as full objects if {@link Shape#OBJECT} is
 *       used. Otherwise the default behavior of serializing to a scalar number value will be
 *       preferred. NOTE: can ONLY be used as class annotation; will not work as per-property
 *       annotation.
 * </ul>
 *
 * @since 2.0
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.FIELD,
  ElementType.METHOD,
  ElementType.PARAMETER,
  ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonFormat {
  /**
   * Value that indicates that default {@link java.util.Locale} (from deserialization or
   * serialization context) should be used: annotation does not define value to use.
   */
  public static final String DEFAULT_LOCALE = "##default";

  /**
   * Value that indicates that default {@link java.util.TimeZone} (from deserialization or
   * serialization context) should be used: annotation does not define value to use.
   *
   * <p>NOTE: default here does NOT mean JVM defaults but Jackson databindings default, usually UTC,
   * but may be changed on <code>ObjectMapper</code>.
   */
  public static final String DEFAULT_TIMEZONE = "##default";

  /**
   * Datatype-specific additional piece of configuration that may be used to further refine
   * formatting aspects. This may, for example, determine low-level format String used for {@link
   * java.util.Date} serialization; however, exact use is determined by specific <code>
   * JsonSerializer</code>
   *
   * @return {@link String}
   */
  public String pattern() default "";

  /**
   * Structure to use for serialization: definition of mapping depends on datatype, but usually has
   * straight-forward counterpart in data format (JSON). Note that commonly only a subset of shapes
   * is available; and if 'invalid' value is chosen, defaults are usually used.
   *
   * @return {@link Shape}
   */
  public Shape shape() default Shape.ANY;

  /**
   * {@link java.util.Locale} to use for serialization (if needed). Special value of {@link
   * #DEFAULT_LOCALE} can be used to mean "just use the default", where default is specified by the
   * serialization context, which in turn defaults to system defaults ({@link
   * java.util.Locale#getDefault()}) unless explicitly set to another locale.
   *
   * @return {@link String}
   */
  public String locale() default DEFAULT_LOCALE;

  /**
   * {@link java.util.TimeZone} to use for serialization (if needed). Special value of {@link
   * #DEFAULT_TIMEZONE} can be used to mean "just use the default", where default is specified by
   * the serialization context, which in turn defaults to system default (UTC) unless explicitly set
   * to another timezone.
   *
   * @return {@link String}
   */
  public String timezone() default DEFAULT_TIMEZONE;

  /**
   * Property that indicates whether "lenient" handling should be enabled or disabled. This is
   * relevant mostly for deserialization of some textual datatypes, especially date/time types.
   *
   * <p>Note that underlying default setting depends on datatype (or more precisely deserializer for
   * it): for most date/time types, default is for leniency to be enabled.
   *
   * @since 2.9
   * @return {@link OptBoolean}
   */
  public OptBoolean lenient() default OptBoolean.DEFAULT;

  /**
   * Set of {@link Feature}s to explicitly enable with respect to handling of annotated property.
   * This will have precedence over possible global configuration.
   *
   * @since 2.6
   * @return {@link Feature[]}
   */
  public Feature[] with() default {};

  /**
   * Set of {@link Feature}s to explicitly disable with respect to handling of annotated property.
   * This will have precedence over possible global configuration.
   *
   * @since 2.6
   * @return {@link Feature[]}
   */
  public Feature[] without() default {};

  /*
  /**********************************************************
  /* Value enumeration(s), value class(es)
  /**********************************************************
   */

  /**
   * Value enumeration used for indicating preferred Shape; translates loosely to JSON types, with
   * some extra values to indicate less precise choices (i.e. allowing one of multiple actual
   * shapes)
   */
  public enum Shape {
    /**
     * Marker enum value that indicates "whatever" choice, meaning that annotation does NOT specify
     * shape to use. Note that this is different from {@link Shape#NATURAL}, which specifically
     * instructs use of the "natural" shape for datatype.
     */
    ANY,

    /**
     * Marker enum value that indicates the "default" choice for given datatype; for example, JSON
     * String for {@link String}, or JSON Number for Java numbers. Note that this is different from
     * {@link Shape#ANY} in that this is actual explicit choice that overrides possible default
     * settings.
     *
     * @since 2.8
     */
    NATURAL,

    /**
     * Value that indicates shape should not be structural (that is, not {@link #ARRAY} or {@link
     * #OBJECT}, but can be any other shape.
     */
    SCALAR,

    /** Value that indicates that (JSON) Array type should be used. */
    ARRAY,

    /** Value that indicates that (JSON) Object type should be used. */
    OBJECT,

    /**
     * Value that indicates that a numeric (JSON) type should be used (but does not specify whether
     * integer or floating-point representation should be used)
     */
    NUMBER,

    /** Value that indicates that floating-point numeric type should be used */
    NUMBER_FLOAT,

    /**
     * Value that indicates that integer number type should be used (and not {@link #NUMBER_FLOAT}).
     */
    NUMBER_INT,

    /** Value that indicates that (JSON) String type should be used. */
    STRING,

    /** Value that indicates that (JSON) boolean type (true, false) should be used. */
    BOOLEAN;

    public boolean isNumeric() {
      return (this == NUMBER) || (this == NUMBER_INT) || (this == NUMBER_FLOAT);
    }

    public boolean isStructured() {
      return (this == OBJECT) || (this == ARRAY);
    }
  }

  /**
   * Set of features that can be enabled/disabled for property annotated. These often relate to
   * specific <code>SerializationFeature</code> or <code>DeserializationFeature</code>, as noted by
   * entries.
   *
   * <p>Note that whether specific setting has an effect depends on whether <code>JsonSerializer
   * </code> / <code>JsonDeserializer</code> being used takes the format setting into account. If
   * not, please file an issue for adding support via issue tracker for package that has handlers
   * (if you know which one; if not, just use `jackson-databind`).
   *
   * @since 2.6
   */
  public enum Feature {
    /**
     * Override for <code>DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY</code> which will
     * allow deserialization of JSON non-array values into single-element Java arrays and {@link
     * java.util.Collection}s.
     */
    ACCEPT_SINGLE_VALUE_AS_ARRAY,

    /**
     * Override for <code>MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES</code>, which allows
     * case-insensitive matching of property names (but NOT values, see {@link
     * #ACCEPT_CASE_INSENSITIVE_VALUES} for that).
     *
     * <p>Only affects deserialization, has no effect on serialization.
     *
     * @since 2.8
     */
    ACCEPT_CASE_INSENSITIVE_PROPERTIES,

    /**
     * Override for <code>DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL</code>, which
     * allows unknown Enum values to be parsed as null values.
     *
     * @since 2.15
     */
    READ_UNKNOWN_ENUM_VALUES_AS_NULL,

    /**
     * Override for <code>DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE</code>
     * , which allows unknown Enum values to be ignored and a predefined value specified through
     * {@link com.fasterxml.jackson.annotation.JsonEnumDefaultValue @JsonEnumDefaultValue}
     * annotation.
     *
     * @since 2.15
     */
    READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE,

    /**
     * Override for {@code DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE},
     * (counterpart to {@link #WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS}), similar constraints apply.
     *
     * @since 2.15
     */
    READ_DATE_TIMESTAMPS_AS_NANOSECONDS,

    /**
     * Override for <code>MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES</code>, which allows
     * case-sensitive matching of (some) property values, such as {@code Enum}s. Only affects
     * deserialization, has no effect on serialization.
     *
     * @since 2.10
     */
    ACCEPT_CASE_INSENSITIVE_VALUES,

    /**
     * Override for <code>SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS</code>, similar
     * constraints apply.
     */
    WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,

    /**
     * Override for <code>SerializationFeature.WRITE_DATES_WITH_ZONE_ID</code>, similar constraints
     * apply.
     */
    WRITE_DATES_WITH_ZONE_ID,

    /**
     * Override for <code>SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED</code> which will
     * force serialization of single-element arrays and {@link java.util.Collection}s as that single
     * element and excluding array wrapper.
     */
    WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,

    /**
     * Override for <code>SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS</code>, enabling of which
     * will force sorting of {@link java.util.Map} keys before serialization.
     */
    WRITE_SORTED_MAP_ENTRIES,

    /**
     * Override for <code>DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIMEZONE</code> that
     * specifies whether context provided timezone <code>DeserializationContext.getTimeZone()</code>
     * should be used to adjust Date/Time values on deserialization, even if value itself contains
     * timezone information
     *
     * <p>NOTE: due to limitations of "old" JDK date/time types (that is, {@link java.util.Date} and
     * {@link java.util.Calendar}), this setting is only applicable to <code>Joda</code> and <code>
     * Java 8 date/time</code> values, but not to <code>java.util.Date</code> or <code>
     * java.util.Calendar</code>.
     *
     * @since 2.8
     */
    ADJUST_DATES_TO_CONTEXT_TIME_ZONE
  }
}
