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

package org.dominokit.jackson.ser;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/**
 * Dummy {@link org.dominokit.jackson.JsonSerializer} that will just output raw values by calling
 * toString() on value to serialize.
 */
public class RawValueJsonSerializer<T> extends JsonSerializer<T> {

  private static final RawValueJsonSerializer<?> INSTANCE = new RawValueJsonSerializer();

  /**
   * getInstance
   *
   * @param <T> a T object.
   * @return an instance of {@link org.dominokit.jackson.ser.RawValueJsonSerializer}
   */
  @SuppressWarnings("unchecked")
  public static <T> RawValueJsonSerializer<T> getInstance() {
    return (RawValueJsonSerializer<T>) INSTANCE;
  }

  private RawValueJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected void doSerialize(
      JsonWriter writer,
      Object value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    writer.rawValue(value);
  }
}
