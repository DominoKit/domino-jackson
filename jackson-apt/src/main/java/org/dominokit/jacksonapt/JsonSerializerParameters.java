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
package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;

/**
 * JsonSerializerParameters interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonSerializerParameters {
  /**
   * getPattern.
   *
   * @return a {@link java.lang.String} object.
   */
  String getPattern();

  /**
   * setPattern.
   *
   * @param pattern a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setPattern(String pattern);

  /**
   * getShape.
   *
   * @return a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
   */
  JsonFormat.Shape getShape();

  /**
   * setShape.
   *
   * @param shape a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setShape(JsonFormat.Shape shape);

  /**
   * getLocale.
   *
   * @return a {@link java.lang.String} object.
   */
  String getLocale();

  /**
   * setLocale.
   *
   * @param locale a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setLocale(String locale);

  /**
   * getTimezone.
   *
   * @return a {@link java.lang.Object} object.
   */
  Object getTimezone();

  /**
   * setTimezone.
   *
   * @param timezone a {@link java.lang.Object} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setTimezone(Object timezone);

  /**
   * getIgnoredProperties.
   *
   * @return a {@link java.util.Set} object.
   */
  Set<String> getIgnoredProperties();

  /**
   * addIgnoredProperty.
   *
   * @param ignoredProperty a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters addIgnoredProperty(String ignoredProperty);

  /**
   * getInclude.
   *
   * @return a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
   */
  JsonInclude.Include getInclude();

  /**
   * setInclude.
   *
   * @param include a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setInclude(JsonInclude.Include include);

  /**
   * getIdentityInfo.
   *
   * @return a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
   */
  IdentitySerializationInfo getIdentityInfo();

  /**
   * setIdentityInfo.
   *
   * @param identityInfo a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo}
   *     object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setIdentityInfo(IdentitySerializationInfo identityInfo);

  /**
   * getTypeInfo.
   *
   * @return a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
   */
  TypeSerializationInfo getTypeInfo();

  /**
   * setTypeInfo.
   *
   * @param typeInfo a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo);

  /**
   * isUnwrapped.
   *
   * @return a boolean.
   */
  boolean isUnwrapped();

  /**
   * setUnwrapped.
   *
   * @param unwrapped a boolean.
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters setUnwrapped(boolean unwrapped);
}
