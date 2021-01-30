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

package org.dominokit.jacksonapt.deser.array;

import java.util.List;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.BooleanJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for array of boolean.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveBooleanArrayJsonDeserializer
    extends AbstractArrayJsonDeserializer<boolean[]> {

  private static final PrimitiveBooleanArrayJsonDeserializer INSTANCE =
      new PrimitiveBooleanArrayJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jacksonapt.deser.array.PrimitiveBooleanArrayJsonDeserializer}
   */
  public static PrimitiveBooleanArrayJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveBooleanArrayJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public boolean[] doDeserializeArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    List<Boolean> list =
        deserializeIntoList(reader, ctx, BooleanJsonDeserializer.getInstance(), params);

    boolean[] result = new boolean[list.size()];
    int i = 0;
    for (Boolean value : list) {
      if (null != value) {
        result[i] = value;
      }
      i++;
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean[] doDeserializeSingleArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    return new boolean[] {BooleanJsonDeserializer.getInstance().deserialize(reader, ctx, params)};
  }
}
