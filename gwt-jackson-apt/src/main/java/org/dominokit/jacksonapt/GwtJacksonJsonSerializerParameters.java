/*
 * Copyright 2013 Nicolas Morel
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

package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;
import org.gwtproject.i18n.client.TimeZone;

import java.util.HashSet;
import java.util.Set;

/**
 * This class includes parameters defined through properties annotations like {@link JsonFormat}. They are specific to one
 * {@link JsonSerializer} and that's why they are not contained inside {@link JsonSerializationContext}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class GwtJacksonJsonSerializerParameters implements JsonSerializerParameters {

    /**
     * Constant <code>DEFAULT</code>
     */
    public static final JsonSerializerParameters DEFAULT = new GwtJacksonJsonSerializerParameters();

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific {@link JsonSerializer}
     */
    private String pattern;

    /**
     * Structure to use for serialization: definition of mapping depends on datatype,
     * but usually has straight-forward counterpart in data format (JSON).
     * Note that commonly only a subset of shapes is available; and if 'invalid' value
     * is chosen, defaults are usually used.
     */
    private Shape shape = Shape.ANY;

    /**
     * Locale to use for serialization (if needed).
     */
    private String locale;

    /**
     * Timezone to use for serialization (if needed).
     */
    private TimeZone timezone;

    /**
     * Names of properties to ignore.
     */
    private Set<String> ignoredProperties;

    /**
     * Inclusion rule to use.
     */
    private Include include;

    /**
     * Bean identity informations
     */
    private IdentitySerializationInfo identityInfo;

    /**
     * Bean type informations
     */
    private TypeSerializationInfo typeInfo;

    /**
     * If true, all the properties of an object will be serialized inside the current object.
     */
    private boolean unwrapped = false;

    /**
     * <p>Getter for the field <code>pattern</code>.</p>
     *
     * @return a {@link String} object.
     */
    @Override
    public String getPattern() {
        return pattern;
    }

    /**
     * <p>Setter for the field <code>pattern</code>.</p>
     *
     * @param pattern a {@link String} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * <p>Getter for the field <code>shape</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     */
    @Override
    public Shape getShape() {
        return shape;
    }

    /**
     * <p>Setter for the field <code>shape</code>.</p>
     *
     * @param shape a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    /**
     * <p>Getter for the field <code>locale</code>.</p>
     *
     * @return a {@link String} object.
     */
    @Override
    public String getLocale() {
        return locale;
    }

    /**
     * <p>Setter for the field <code>locale</code>.</p>
     *
     * @param locale a {@link String} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    /**
     * <p>Getter for the field <code>timezone</code>.</p>
     *
     * @return a {@link org.gwtproject.i18n.client.TimeZone} object.
     */
    @Override
    public TimeZone getTimezone() {
        return timezone;
    }

    /**
     * <p>Setter for the field <code>timezone</code>.</p>
     *
     * @param timezone a {@link org.gwtproject.i18n.client.TimeZone} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setTimezone(Object timezone) {
        this.timezone = (TimeZone) timezone;
        return this;
    }

    /**
     * <p>Getter for the field <code>ignoredProperties</code>.</p>
     *
     * @return a {@link Set} object.
     */
    @Override
    public Set<String> getIgnoredProperties() {
        return ignoredProperties;
    }

    /**
     * <p>addIgnoredProperty</p>
     *
     * @param ignoredProperty a {@link String} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters addIgnoredProperty(String ignoredProperty) {
        if (null == ignoredProperties) {
            ignoredProperties = new HashSet<String>();
        }
        ignoredProperties.add(ignoredProperty);
        return this;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     */
    @Override
    public Include getInclude() {
        return include;
    }

    /**
     * <p>Setter for the field <code>include</code>.</p>
     *
     * @param include a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setInclude(Include include) {
        this.include = include;
        return this;
    }

    /**
     * <p>Getter for the field <code>identityInfo</code>.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
     */
    @Override
    public IdentitySerializationInfo getIdentityInfo() {
        return identityInfo;
    }

    /**
     * <p>Setter for the field <code>identityInfo</code>.</p>
     *
     * @param identityInfo a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setIdentityInfo(IdentitySerializationInfo identityInfo) {
        this.identityInfo = identityInfo;
        return this;
    }

    /**
     * <p>Getter for the field <code>typeInfo</code>.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
     */
    @Override
    public TypeSerializationInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * <p>Setter for the field <code>typeInfo</code>.</p>
     *
     * @param typeInfo a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo) {
        this.typeInfo = typeInfo;
        return this;
    }

    /**
     * <p>isUnwrapped</p>
     *
     * @return a boolean.
     */
    @Override
    public boolean isUnwrapped() {
        return unwrapped;
    }

    /**
     * <p>Setter for the field <code>unwrapped</code>.</p>
     *
     * @param unwrapped a boolean.
     * @return a {@link JsonSerializerParameters} object.
     */
    @Override
    public JsonSerializerParameters setUnwrapped(boolean unwrapped) {
        this.unwrapped = unwrapped;
        return this;
    }
}
