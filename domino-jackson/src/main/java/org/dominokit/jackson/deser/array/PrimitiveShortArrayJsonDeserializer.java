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

package org.dominokit.jackson.deser.array;

import java.util.List;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.deser.BaseNumberJsonDeserializer;
import org.dominokit.jackson.stream.JsonReader;

/** Default {@link org.dominokit.jackson.JsonDeserializer} implementation for array of short. */
public class PrimitiveShortArrayJsonDeserializer extends AbstractArrayJsonDeserializer<short[]> {

  private static final PrimitiveShortArrayJsonDeserializer INSTANCE =
      new PrimitiveShortArrayJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jackson.deser.array.PrimitiveShortArrayJsonDeserializer}
   */
  public static PrimitiveShortArrayJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveShortArrayJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public short[] doDeserializeArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    List<Short> list =
        deserializeIntoList(
            reader, ctx, BaseNumberJsonDeserializer.ShortJsonDeserializer.getInstance(), params);

    short[] result = new short[list.size()];
    int i = 0;
    for (Short value : list) {
      if (null != value) {
        result[i] = value;
      }
      i++;
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected short[] doDeserializeSingleArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    return new short[] {
      BaseNumberJsonDeserializer.ShortJsonDeserializer.getInstance()
          .deserialize(reader, ctx, params)
    };
  }
}
