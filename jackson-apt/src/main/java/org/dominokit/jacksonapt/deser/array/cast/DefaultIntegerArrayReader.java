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
package org.dominokit.jacksonapt.deser.array.cast;

import java.util.Stack;
import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

/** The default JVM implementation for reading a Integer array json */
@GwtIncompatible
public class DefaultIntegerArrayReader implements JacksonContext.IntegerArrayReader {
  /** {@inheritDoc} */
  @Override
  public int[] readArray(JsonReader reader) {
    Stack<Integer> intStack = new Stack<>();
    reader.beginArray();
    while (JsonToken.END_ARRAY != reader.peek()) {
      if (JsonToken.NULL == reader.peek()) {
        reader.skipValue();
        intStack.push(null);
      } else {
        intStack.push(reader.nextInt());
      }
    }
    reader.endArray();
    return intStack.stream().mapToInt(Integer::intValue).toArray();
  }
}
