package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

/**
 * General-purpose annotation used for configuring details of how
 * values of properties are to be serialized.
 * Unlike most other Jackson annotations, annotation does not
 * have specific universal interpretation: instead, effect depends on datatype
 * of property being annotated (or more specifically, deserializer
 * and serializer being used).
 *<p>
 * Common uses include choosing between alternate representations -- for example,
 * whether {@link java.util.Date} is to be serialized as number (Java timestamp)
 * or String (such as ISO-8601 compatible time value) -- as well as configuring
 * exact details with {@link #pattern} property.
 *<p>
 * As of Jackson 2.6, known special handling includes:
 *<ul>
 * <li>{@link java.util.Date}: Shape can  be {@link Shape#STRING} or {@link Shape#NUMBER};
 *    pattern may contain {@link java.text.SimpleDateFormat}-compatible pattern definition.
 *   </li>
 * <li>Can be used on Classes (types) as well, for modified default behavior, possibly
 *   overridden by per-property annotation
 *   </li>
 * <li>{@link Enum}s: Shapes {@link Shape#STRING} and {@link Shape#NUMBER} can be
 *    used to change between numeric (index) and textual (name or <code>toString()</code>);
 *    but it is also possible to use {@link Shape#OBJECT} to serialize (but not deserialize)
 *    {@link Enum}s as JSON Objects (as if they were POJOs). NOTE: serialization
 *     as JSON Object only works with class annotation; 
 *    will not work as per-property annotation.
 *   </li>
 * <li>{@link java.util.Collection}s can be serialized as (and deserialized from) JSON Objects,
 *    if {@link Shape#OBJECT} is used. NOTE: can ONLY be used as class annotation;
 *    will not work as per-property annotation.
 *   </li>
 * <li>{@link Number} subclasses can be serialized as full objects if
 *    {@link Shape#OBJECT} is used. Otherwise the default behavior of serializing to a
 *    scalar number value will be preferred. NOTE: can ONLY be used as class annotation;
 *    will not work as per-property annotation.
 *   </li>
 *</ul>
 *
 * @since 2.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
    ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonFormat
{
    /**
     * Value that indicates that default {@link java.util.Locale}
     * (from deserialization or serialization context) should be used:
     * annotation does not define value to use.
     */
    public final static String DEFAULT_LOCALE = "##default";

    /**
     * Value that indicates that default {@link java.util.TimeZone}
     * (from deserialization or serialization context) should be used:
     * annotation does not define value to use.
     *<p>
     * NOTE: default here does NOT mean JVM defaults but Jackson databindings
     * default, usually UTC, but may be changed on <code>ObjectMapper</code>.
     */
    public final static String DEFAULT_TIMEZONE = "##default";
    
    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific <code>JsonSerializer</code>
     */
    public String pattern() default "";

    /**
     * Structure to use for serialization: definition of mapping depends on datatype,
     * but usually has straight-forward counterpart in data format (JSON).
     * Note that commonly only a subset of shapes is available; and if 'invalid' value
     * is chosen, defaults are usually used.
     */
    public Shape shape() default Shape.ANY;

    /**
     * {@link java.util.Locale} to use for serialization (if needed).
     * Special value of {@link #DEFAULT_LOCALE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * defaults ({@link java.util.Locale#getDefault()}) unless explicitly
     * set to another locale.
     */
    public String locale() default DEFAULT_LOCALE;
    
    /**
     * {@link java.util.TimeZone} to use for serialization (if needed).
     * Special value of {@link #DEFAULT_TIMEZONE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * default (UTC) unless explicitly set to another timezone.
     */
    public String timezone() default DEFAULT_TIMEZONE;

    /**
     * Property that indicates whether "lenient" handling should be enabled or
     * disabled. This is relevant mostly for deserialization of some textual
     * datatypes, especially date/time types.
     *<p>
     * Note that underlying default setting depends on datatype (or more precisely
     * deserializer for it): for most date/time types, default is for leniency
     * to be enabled.
     * 
     * @since 2.9
     */
    public OptBoolean lenient() default OptBoolean.DEFAULT;

    /**
     * Set of {@link Feature}s to explicitly enable with respect
     * to handling of annotated property. This will have precedence over possible
     * global configuration.
     *
     * @since 2.6
     */
    public Feature[] with() default { };

    /**
     * Set of {@link Feature}s to explicitly disable with respect
     * to handling of annotated property. This will have precedence over possible
     * global configuration.
     *
     * @since 2.6
     */
    public Feature[] without() default { };

    /*
    /**********************************************************
    /* Value enumeration(s), value class(es)
    /**********************************************************
     */

    /**
     * Value enumeration used for indicating preferred Shape; translates
     * loosely to JSON types, with some extra values to indicate less precise
     * choices (i.e. allowing one of multiple actual shapes)
     */
    public enum Shape
    {
        /**
         * Marker enum value that indicates "whatever" choice, meaning that annotation
         * does NOT specify shape to use.
         * Note that this is different from {@link Shape#NATURAL}, which
         * specifically instructs use of the "natural" shape for datatype.
         */
        ANY,

        /**
         * Marker enum value that indicates the "default" choice for given datatype;
         * for example, JSON String for {@link String}, or JSON Number
         * for Java numbers.
         * Note that this is different from {@link Shape#ANY} in that this is actual
         * explicit choice that overrides possible default settings.
         *
         * @since 2.8
         */
        NATURAL,
        
        /**
         * Value that indicates shape should not be structural (that is, not
         * {@link #ARRAY} or {@link #OBJECT}, but can be any other shape.
         */
        SCALAR,

        /**
         * Value that indicates that (JSON) Array type should be used.
         */
        ARRAY,
        
        /**
         * Value that indicates that (JSON) Object type should be used.
         */
        OBJECT,

        /**
         * Value that indicates that a numeric (JSON) type should be used
         * (but does not specify whether integer or floating-point representation
         * should be used)
         */
        NUMBER,

        /**
         * Value that indicates that floating-point numeric type should be used
         */
        NUMBER_FLOAT,

        /**
         * Value that indicates that integer number type should be used
         * (and not {@link #NUMBER_FLOAT}).
         */
        NUMBER_INT,

        /**
         * Value that indicates that (JSON) String type should be used.
         */
        STRING,
        
        /**
         * Value that indicates that (JSON) boolean type
         * (true, false) should be used.
         */
        BOOLEAN
        ;

        public boolean isNumeric() {
            return (this == NUMBER) || (this == NUMBER_INT) || (this == NUMBER_FLOAT);
        }

        public boolean isStructured() {
            return (this == OBJECT) || (this == ARRAY);
        }
    }

    /**
     * Set of features that can be enabled/disabled for property annotated.
     * These often relate to specific <code>SerializationFeature</code>
     * or <code>DeserializationFeature</code>, as noted by entries.
     *<p>
     * Note that whether specific setting has an effect depends on whether
     * <code>JsonSerializer</code> / <code>JsonDeserializer</code> being used
     * takes the format setting into account. If not, please file an issue
     * for adding support via issue tracker for package that has handlers
     * (if you know which one; if not, just use `jackson-databind`).
     *
     * @since 2.6
     */
    public enum Feature {
        /**
         * Override for <code>DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY</code>
         * which will allow deserialization of JSON non-array values into single-element
         * Java arrays and {@link java.util.Collection}s.
         */
        ACCEPT_SINGLE_VALUE_AS_ARRAY,

        /**
         * Override for <code>MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES</code>.
         * Only affects deserialization, has no effect on serialization.
         *<p>
         * NOTE: starting with 2.9 can also effect Enum handling (and potentially other
         * places where case-insensitive property values are accepted).
         * 
         * @since 2.8
         */
        ACCEPT_CASE_INSENSITIVE_PROPERTIES,

        /**
         * Override for <code>SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS</code>,
         * similar constraints apply.
         */
        WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,

        /**
         * Override for <code>SerializationFeature.WRITE_DATES_WITH_ZONE_ID</code>,
         * similar constraints apply.
         */
        WRITE_DATES_WITH_ZONE_ID,

        /**
         * Override for <code>SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED</code>
         * which will force serialization of single-element arrays and {@link java.util.Collection}s
         * as that single element and excluding array wrapper.
         */
        WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,

        /**
         * Override for <code>SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS</code>,
         * enabling of which will force sorting of {@link java.util.Map} keys before
         * serialization.
         */
        WRITE_SORTED_MAP_ENTRIES,;
    }
}
