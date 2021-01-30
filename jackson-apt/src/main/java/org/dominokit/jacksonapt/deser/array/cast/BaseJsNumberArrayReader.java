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
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

/**
 * Abstract BaseJsNumberArrayReader class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class BaseJsNumberArrayReader {

  JsArray<JsNumber> readNumberArray(JsonReader reader) {
    JsArray<JsNumber> jsArray = new JsArray<>();
    reader.beginArray();
    while (JsonToken.END_ARRAY != reader.peek()) {
      if (JsonToken.NULL == reader.peek()) {
        reader.skipValue();
        jsArray.push(null);
      } else {
        jsArray.push((JsNumber) Js.cast(reader.nextInt()));
      }
    }
    reader.endArray();

    return jsArray;
  }
}
