package com.progressoft.brix.domino.jacksonapt.deser.array.cast;

import com.progressoft.brix.domino.jacksonapt.JacksonContext;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import com.progressoft.brix.domino.jacksonapt.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsString;
import jsinterop.base.Js;

public class JsStringArrayReader implements JacksonContext.StringArrayReader {
    @Override
    public String[] readArray(JsonReader reader) {
        JsArray<JsString> jsArray = new JsArray<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                jsArray.push(null);
            } else {
                jsArray.push((JsString) Js.cast(reader.nextString()));
            }
        }
        reader.endArray();

        return reinterpretCast(jsArray);
    }

    private static String[] reinterpretCast(JsArray<JsString> value) {
        JsString[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }
}
