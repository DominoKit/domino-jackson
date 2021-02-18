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

import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;

/** The JS implementation for reading a Short array json */
public class JsShortArrayReader extends BaseJsNumberArrayReader
    implements JacksonContext.ShortArrayReader {
  /**
   * @param reader {@link JsonReader}
   * @return Short[]
   */
  @Override
  public short[] readArray(JsonReader reader) {
    return reinterpretCast(super.readNumberArray(reader));
  }

  private static short[] reinterpretCast(JsArray<JsNumber> value) {
    JsArray<JsNumber> slice = value.slice();
    return Js.uncheckedCast(slice.asArray(new JsNumber[slice.length]));
  }
}
