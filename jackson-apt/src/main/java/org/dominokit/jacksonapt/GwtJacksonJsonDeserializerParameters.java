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

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo;
import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * This class includes parameters defined through properties annotations like {@link com.fasterxml.jackson.annotation.JsonIgnoreProperties}. They are specific to one
 * {@link org.dominokit.jacksonapt.JsonDeserializer} and that's why they are not contained inside {@link org.dominokit.jacksonapt.JsonDeserializationContext}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class GwtJacksonJsonDeserializerParameters implements JsonDeserializerParameters {

    /**
     * Constant <code>DEFAULT</code>
     */
    public static final JsonDeserializerParameters DEFAULT = new GwtJacksonJsonDeserializerParameters();

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific {@link JsonDeserializer}
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
     * Locale to use for deserialization (if needed).
     */
    private String locale;

    /**
     * Names of properties to ignore.
     */
    private Set<String> ignoredProperties;

    /**
     * Property that defines whether it is ok to just ignore any
     * unrecognized properties during deserialization.
     * If true, all properties that are unrecognized -- that is,
     * there are no setters or creators that accept them -- are
     * ignored without warnings (although handlers for unknown
     * properties, if any, will still be called) without
     * exception.
     */
    private boolean ignoreUnknown = false;

    /**
     * Bean identity informations
     */
    private IdentityDeserializationInfo identityInfo;

    /**
     * Bean type informations
     */
    private TypeDeserializationInfo typeInfo;

    public GwtJacksonJsonDeserializerParameters() {
    }

    public GwtJacksonJsonDeserializerParameters(JsonDeserializerParameters jsonDeserializerParameters) {
        this.identityInfo = jsonDeserializerParameters.getIdentityInfo();
        this.ignoredProperties = jsonDeserializerParameters.getIgnoredProperties();
        this.locale = jsonDeserializerParameters.getLocale();
        this.pattern = jsonDeserializerParameters.getPattern();
        this.shape = jsonDeserializerParameters.getShape();
        this.typeInfo = jsonDeserializerParameters.getTypeInfo();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>pattern</code>.</p>
     */
    @Override
    public String getPattern() {
        return pattern;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>pattern</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>shape</code>.</p>
     */
    @Override
    public Shape getShape() {
        return shape;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>shape</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>locale</code>.</p>
     */
    @Override
    public String getLocale() {
        return locale;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>locale</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>ignoredProperties</code>.</p>
     */
    @Override
    public Set<String> getIgnoredProperties() {
        return ignoredProperties;
    }

    /**
     * {@inheritDoc}
     *
     * <p>addIgnoredProperty</p>
     */
    @Override
    public JsonDeserializerParameters addIgnoredProperty(String ignoredProperty) {
        if (null == ignoredProperties) {
            ignoredProperties = new HashSet<String>();
        }
        ignoredProperties.add(ignoredProperty);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isIgnoreUnknown</p>
     */
    @Override
    public boolean isIgnoreUnknown() {
        return ignoreUnknown;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>ignoreUnknown</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setIgnoreUnknown(boolean ignoreUnknown) {
        this.ignoreUnknown = ignoreUnknown;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>identityInfo</code>.</p>
     */
    @Override
    public IdentityDeserializationInfo getIdentityInfo() {
        return identityInfo;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>identityInfo</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setIdentityInfo(IdentityDeserializationInfo identityInfo) {
        this.identityInfo = identityInfo;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field <code>typeInfo</code>.</p>
     */
    @Override
    public TypeDeserializationInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Setter for the field <code>typeInfo</code>.</p>
     */
    @Override
    public JsonDeserializerParameters setTypeInfo(TypeDeserializationInfo typeInfo) {
        this.typeInfo = typeInfo;
        return this;
    }
}
