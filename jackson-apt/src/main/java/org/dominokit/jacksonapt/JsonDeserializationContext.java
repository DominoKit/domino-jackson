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

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.dominokit.jacksonapt.stream.JsonReader;

/** JsonDeserializationContext interface. */
public interface JsonDeserializationContext extends JsonMappingContext {
  /**
   * isFailOnUnknownProperties.
   *
   * @return a boolean.
   */
  boolean isFailOnUnknownProperties();

  /**
   * isUnwrapRootValue.
   *
   * @return a boolean.
   */
  boolean isUnwrapRootValue();

  /**
   * isAcceptSingleValueAsArray.
   *
   * @return a boolean.
   */
  boolean isAcceptSingleValueAsArray();

  /**
   * isUseSafeEval.
   *
   * @return a boolean.
   */
  boolean isUseSafeEval();

  /**
   * isReadUnknownEnumValuesAsNull.
   *
   * @return a boolean.
   */
  boolean isReadUnknownEnumValuesAsNull();

  /**
   * isUseBrowserTimezone.
   *
   * @return a boolean.
   */
  boolean isUseBrowserTimezone();

  /**
   * newJsonReader.
   *
   * @param input a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   */
  JsonReader newJsonReader(String input);

  /**
   * traceError.
   *
   * @param message a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.exception.JsonDeserializationException} object.
   */
  JsonDeserializationException traceError(String message);

  /**
   * traceError.
   *
   * @param message a {@link java.lang.String} object.
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @return a {@link org.dominokit.jacksonapt.exception.JsonDeserializationException} object.
   */
  JsonDeserializationException traceError(String message, JsonReader reader);

  /**
   * traceError.
   *
   * @param cause a {@link java.lang.RuntimeException} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(RuntimeException cause);

  /**
   * traceError.
   *
   * @param cause a {@link java.lang.RuntimeException} object.
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(RuntimeException cause, JsonReader reader);

  /**
   * addObjectId.
   *
   * @param id a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
   * @param instance a {@link java.lang.Object} object.
   */
  void addObjectId(ObjectIdGenerator.IdKey id, Object instance);

  /**
   * getObjectWithId.
   *
   * @param id a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
   * @return a {@link java.lang.Object} object.
   */
  Object getObjectWithId(ObjectIdGenerator.IdKey id);

  /**
   * defaultParameters.
   *
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters defaultParameters();
}
