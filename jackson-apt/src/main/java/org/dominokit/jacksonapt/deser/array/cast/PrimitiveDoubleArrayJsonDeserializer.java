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

package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer;
import org.dominokit.jacksonapt.deser.array.AbstractArrayJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for array of double.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveDoubleArrayJsonDeserializer extends AbstractArrayJsonDeserializer<double[]> {

  private static final PrimitiveDoubleArrayJsonDeserializer INSTANCE =
      new PrimitiveDoubleArrayJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jacksonapt.deser.array.cast.PrimitiveDoubleArrayJsonDeserializer}
   */
  public static PrimitiveDoubleArrayJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveDoubleArrayJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public double[] doDeserializeArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    return JacksonContextProvider.get().doubleArrayReader().readArray(reader);
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
