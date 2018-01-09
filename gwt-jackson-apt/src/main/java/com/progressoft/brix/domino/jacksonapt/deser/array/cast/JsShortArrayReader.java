package com.progressoft.brix.domino.jacksonapt.deser.array.cast;

import com.progressoft.brix.domino.jacksonapt.JacksonContext;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import com.progressoft.brix.domino.jacksonapt.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;

public class JsShortArrayReader extends BaseJsNumberArrayReader implements JacksonContext.ShortArrayReader {
    @Override
    public short[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static short[] reinterpretCast(JsArray<JsNumber> value) {
        JsNumber[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }
}
