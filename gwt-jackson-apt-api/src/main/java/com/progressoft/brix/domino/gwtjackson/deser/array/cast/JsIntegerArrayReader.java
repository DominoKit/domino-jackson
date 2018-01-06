package com.progressoft.brix.domino.gwtjackson.deser.array.cast;

import com.progressoft.brix.domino.gwtjackson.JacksonContext;
import com.progressoft.brix.domino.gwtjackson.stream.JsonReader;
import com.progressoft.brix.domino.gwtjackson.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;

public class JsIntegerArrayReader extends BaseJsNumberArrayReader implements JacksonContext.IntegerArrayReader {
    @Override
    public int[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static int[] reinterpretCast(JsArray<JsNumber> value) {
        JsNumber[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }
}
