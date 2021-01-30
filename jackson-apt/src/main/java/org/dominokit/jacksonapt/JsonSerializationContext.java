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
import org.dominokit.jacksonapt.exception.JsonSerializationException;
import org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * JsonSerializationContext interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonSerializationContext extends JsonMappingContext {
  /**
   * isSerializeNulls.
   *
   * @return a boolean.
   */
  boolean isSerializeNulls();

  /**
   * isWriteDatesAsTimestamps.
   *
   * @return a boolean.
   */
  boolean isWriteDatesAsTimestamps();

  /**
   * isWriteDateKeysAsTimestamps.
   *
   * @return a boolean.
   */
  boolean isWriteDateKeysAsTimestamps();

  /**
   * isWrapRootValue.
   *
   * @return a boolean.
   */
  boolean isWrapRootValue();

  /**
   * isWriteCharArraysAsJsonArrays.
   *
   * @return a boolean.
   */
  boolean isWriteCharArraysAsJsonArrays();

  /**
   * isWriteNullMapValues.
   *
   * @return a boolean.
   */
  boolean isWriteNullMapValues();

  /**
   * isWriteEmptyJsonArrays.
   *
   * @return a boolean.
   */
  boolean isWriteEmptyJsonArrays();

  /**
   * isOrderMapEntriesByKeys.
   *
   * @return a boolean.
   */
  boolean isOrderMapEntriesByKeys();

  /**
   * isWriteSingleElemArraysUnwrapped.
   *
   * @return a boolean.
   */
  boolean isWriteSingleElemArraysUnwrapped();

  /**
   * newJsonWriter.
   *
   * @return a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
   */
  JsonWriter newJsonWriter();

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param message a {@link java.lang.String} object.
   * @return a {@link org.dominokit.jacksonapt.exception.JsonSerializationException} object.
   */
  JsonSerializationException traceError(Object value, String message);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param message a {@link java.lang.String} object.
   * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
   * @return a {@link org.dominokit.jacksonapt.exception.JsonSerializationException} object.
   */
  JsonSerializationException traceError(Object value, String message, JsonWriter writer);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param cause a {@link java.lang.RuntimeException} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(Object value, RuntimeException cause);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param cause a {@link java.lang.RuntimeException} object.
   * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(Object value, RuntimeException cause, JsonWriter writer);

  /**
   * addObjectId.
   *
   * @param object a {@link java.lang.Object} object.
   * @param id a {@link org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer} object.
   */
  void addObjectId(Object object, ObjectIdSerializer<?> id);

  /**
   * getObjectId.
   *
   * @param object a {@link java.lang.Object} object.
   * @return a {@link org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer} object.
   */
  ObjectIdSerializer<?> getObjectId(Object object);

  /**
   * addGenerator.
   *
   * @param generator a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
   */
  @SuppressWarnings("UnusedDeclaration")
  void addGenerator(ObjectIdGenerator<?> generator);

  /**
   * findObjectIdGenerator.
   *
   * @param gen a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
   * @param <T> a T object.
   * @return a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
   */
  @SuppressWarnings({"UnusedDeclaration", "unchecked"})
  <T> ObjectIdGenerator<T> findObjectIdGenerator(ObjectIdGenerator<T> gen);

  /**
   * defaultParameters.
   *
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
   */
  JsonSerializerParameters defaultParameters();
}
