/*
 * Copyright 2014 Nicolas Morel
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

package org.dominokit.jacksonapt.deser.array.dd;

import java.util.ArrayList;
import java.util.List;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;
import org.dominokit.jacksonapt.utils.Base64Utils;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for 2D array of byte.
 */
public class PrimitiveByteArray2dJsonDeserializer
    extends AbstractArray2dJsonDeserializer<byte[][]> {

  private static final PrimitiveByteArray2dJsonDeserializer INSTANCE =
      new PrimitiveByteArray2dJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jacksonapt.deser.array.dd.PrimitiveByteArray2dJsonDeserializer}
   */
  public static PrimitiveByteArray2dJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveByteArray2dJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public byte[][] doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {

    byte[][] result;

    reader.beginArray();
    JsonToken token = reader.peek();

    if (JsonToken.END_ARRAY == token) {

      // empty array
      result = new byte[0][0];

    } else if (JsonToken.STRING == token) {

      // byte[] are encoded as String

      List<byte[]> list = new ArrayList<byte[]>();
      int size = 0;
      while (JsonToken.END_ARRAY != token) {
        byte[] decoded = Base64Utils.fromBase64(reader.nextString());
        size = Math.max(size, decoded.length);
        list.add(decoded);
        token = reader.peek();
      }

      result = new byte[list.size()][size];
      int i = 0;
      for (byte[] value : list) {
        if (null != value) {
          result[i] = value;
        }
        i++;
      }

    } else {

      List<List<Byte>> list =
          doDeserializeIntoList(
              reader,
              ctx,
              BaseNumberJsonDeserializer.ByteJsonDeserializer.getInstance(),
              params,
              token);

      List<Byte> firstList = list.get(0);
      if (firstList.isEmpty()) {

        result = new byte[list.size()][0];

      } else {

        result = new byte[list.size()][firstList.size()];

        int i = 0;
        int j;
        for (List<Byte> innerList : list) {
          j = 0;
          for (Byte value : innerList) {
            if (null != value) {
              result[i][j] = value;
            }
            j++;
          }
          i++;
        }
      }
    }

    reader.endArray();
    return result;
  }
}
