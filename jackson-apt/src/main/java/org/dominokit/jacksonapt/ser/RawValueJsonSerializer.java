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

package org.dominokit.jacksonapt.ser;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Dummy {@link org.dominokit.jacksonapt.JsonSerializer} that will just output raw values by calling
 * toString() on value to serialize.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class RawValueJsonSerializer<T> extends JsonSerializer<T> {

  private static final RawValueJsonSerializer<?> INSTANCE = new RawValueJsonSerializer();

  /**
   * getInstance
   *
   * @param <T> a T object.
   * @return an instance of {@link org.dominokit.jacksonapt.ser.RawValueJsonSerializer}
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
