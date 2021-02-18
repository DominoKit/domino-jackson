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
import java.util.Set;
import org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo;
import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;

/** JsonDeserializerParameters interface. */
public interface JsonDeserializerParameters {
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
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setPattern(String pattern);

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
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setShape(JsonFormat.Shape shape);

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
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setLocale(String locale);

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
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters addIgnoredProperty(String ignoredProperty);

  /**
   * isIgnoreUnknown.
   *
   * @return a boolean.
   */
  boolean isIgnoreUnknown();

  /**
   * setIgnoreUnknown.
   *
   * @param ignoreUnknown a boolean.
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setIgnoreUnknown(boolean ignoreUnknown);

  /**
   * getIdentityInfo.
   *
   * @return a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo} object.
   */
  IdentityDeserializationInfo getIdentityInfo();

  /**
   * setIdentityInfo.
   *
   * @param identityInfo a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo}
   *     object.
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setIdentityInfo(IdentityDeserializationInfo identityInfo);

  /**
   * getTypeInfo.
   *
   * @return a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
   */
  TypeDeserializationInfo getTypeInfo();

  /**
   * setTypeInfo.
   *
   * @param typeInfo a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters setTypeInfo(TypeDeserializationInfo typeInfo);
}
