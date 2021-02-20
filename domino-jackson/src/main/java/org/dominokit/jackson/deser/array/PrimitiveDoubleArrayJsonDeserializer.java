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

/** Default {@link org.dominokit.jackson.JsonDeserializer} implementation for array of double. */
public class PrimitiveDoubleArrayJsonDeserializer extends AbstractArrayJsonDeserializer<double[]> {

  private static final PrimitiveDoubleArrayJsonDeserializer INSTANCE =
      new PrimitiveDoubleArrayJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jackson.deser.array.PrimitiveDoubleArrayJsonDeserializer}
   */
  public static PrimitiveDoubleArrayJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveDoubleArrayJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public double[] doDeserializeArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    List<Double> list =
        deserializeIntoList(
            reader, ctx, BaseNumberJsonDeserializer.DoubleJsonDeserializer.getInstance(), params);

    double[] result = new double[list.size()];
    int i = 0;
    for (Double value : list) {
      if (null != value) {
        result[i] = value;
      }
      i++;
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected double[] doDeserializeSingleArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    return new double[] {
      BaseNumberJsonDeserializer.DoubleJsonDeserializer.getInstance()
          .deserialize(reader, ctx, params)
    };
  }
}
