package com.progressoft.brix.domino.jacksonapt.deser.array.cast;

import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import com.progressoft.brix.domino.jacksonapt.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;

public abstract class BaseJsNumberArrayReader {

    JsArray<JsNumber> readNumberArray(JsonReader reader){
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
