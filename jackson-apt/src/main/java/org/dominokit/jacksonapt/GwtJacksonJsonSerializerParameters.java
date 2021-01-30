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

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashSet;
import java.util.Set;
import org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;
import org.gwtproject.i18n.client.TimeZone;

/**
 * This class includes parameters defined through properties annotations like {@link
 * com.fasterxml.jackson.annotation.JsonFormat}. They are specific to one {@link
 * org.dominokit.jacksonapt.JsonSerializer} and that's why they are not contained inside {@link
 * org.dominokit.jacksonapt.JsonSerializationContext}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class GwtJacksonJsonSerializerParameters implements JsonSerializerParameters {

  /** Constant <code>DEFAULT</code> */
  public static final JsonSerializerParameters DEFAULT = new GwtJacksonJsonSerializerParameters();

  /**
   * Datatype-specific additional piece of configuration that may be used to further refine
   * formatting aspects. This may, for example, determine low-level format String used for {@link
   * java.util.Date} serialization; however, exact use is determined by specific {@link
   * JsonSerializer}
   */
  private String pattern;

  /**
   * Structure to use for serialization: definition of mapping depends on datatype, but usually has
   * straight-forward counterpart in data format (JSON). Note that commonly only a subset of shapes
   * is available; and if 'invalid' value is chosen, defaults are usually used.
   */
  private Shape shape = Shape.ANY;

  /** Locale to use for serialization (if needed). */
  private String locale;

  /** Timezone to use for serialization (if needed). */
  private TimeZone timezone;

  /** Names of properties to ignore. */
  private Set<String> ignoredProperties;

  /** Inclusion rule to use. */
  private Include include;

  /** Bean identity informations */
  private IdentitySerializationInfo identityInfo;

  /** Bean type informations */
  private TypeSerializationInfo typeInfo;

  /** If true, all the properties of an object will be serialized inside the current object. */
  private boolean unwrapped = false;

  public GwtJacksonJsonSerializerParameters() {}

  public GwtJacksonJsonSerializerParameters(JsonSerializerParameters jsonSerializerParameters) {
    this.identityInfo = jsonSerializerParameters.getIdentityInfo();
    this.ignoredProperties = jsonSerializerParameters.getIgnoredProperties();
    this.include = jsonSerializerParameters.getInclude();
    this.locale = jsonSerializerParameters.getLocale();
    this.pattern = jsonSerializerParameters.getPattern();
    this.shape = jsonSerializerParameters.getShape();
    if (nonNull(jsonSerializerParameters.getTimezone())) {
      this.timezone = (TimeZone) jsonSerializerParameters.getTimezone();
    }
    this.typeInfo = jsonSerializerParameters.getTypeInfo();
    this.unwrapped = jsonSerializerParameters.isUnwrapped();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>pattern</code>.
   */
  @Override
  public String getPattern() {
    return pattern;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>pattern</code>.
   */
  @Override
  public JsonSerializerParameters setPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>shape</code>.
   */
  @Override
  public Shape getShape() {
    return shape;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>shape</code>.
   */
  @Override
  public JsonSerializerParameters setShape(Shape shape) {
    this.shape = shape;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>locale</code>.
   */
  @Override
  public String getLocale() {
    return locale;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>locale</code>.
   */
  @Override
  public JsonSerializerParameters setLocale(String locale) {
    this.locale = locale;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>timezone</code>.
   */
  @Override
  public TimeZone getTimezone() {
    return timezone;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>timezone</code>.
   */
  @Override
  public JsonSerializerParameters setTimezone(Object timezone) {
    this.timezone = (TimeZone) timezone;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>ignoredProperties</code>.
   */
  @Override
  public Set<String> getIgnoredProperties() {
    return ignoredProperties;
  }

  /**
   * {@inheritDoc}
   *
   * <p>addIgnoredProperty
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
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>include</code>.
   */
  @Override
  public Include getInclude() {
    return include;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>include</code>.
   */
  @Override
  public JsonSerializerParameters setInclude(Include include) {
    this.include = include;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>identityInfo</code>.
   */
  @Override
  public IdentitySerializationInfo getIdentityInfo() {
    return identityInfo;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>identityInfo</code>.
   */
  @Override
  public JsonSerializerParameters setIdentityInfo(IdentitySerializationInfo identityInfo) {
    this.identityInfo = identityInfo;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>typeInfo</code>.
   */
  @Override
  public TypeSerializationInfo getTypeInfo() {
    return typeInfo;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>typeInfo</code>.
   */
  @Override
  public JsonSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo) {
    this.typeInfo = typeInfo;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isUnwrapped
   */
  @Override
  public boolean isUnwrapped() {
    return unwrapped;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>unwrapped</code>.
   */
  @Override
  public JsonSerializerParameters setUnwrapped(boolean unwrapped) {
    this.unwrapped = unwrapped;
    return this;
  }
}
