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

package org.dominokit.jacksonapt.exception;

/** Base exception for deserialization process */
public class JsonDeserializationException extends JsonMappingException {

  /** Constructor for JsonDeserializationException. */
  public JsonDeserializationException() {}

  /**
   * Constructor for JsonDeserializationException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public JsonDeserializationException(String message) {
    super(message);
  }

  /**
   * Constructor for JsonDeserializationException.
   *
   * @param message a {@link java.lang.String} object.
   * @param cause a {@link java.lang.Throwable} object.
   */
  public JsonDeserializationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for JsonDeserializationException.
   *
   * @param cause a {@link java.lang.Throwable} object.
   */
  public JsonDeserializationException(Throwable cause) {
    super(cause);
  }
}
