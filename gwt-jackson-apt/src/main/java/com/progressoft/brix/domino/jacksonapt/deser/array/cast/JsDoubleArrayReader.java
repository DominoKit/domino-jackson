package com.progressoft.brix.domino.jacksonapt.deser.array.cast;

import com.progressoft.brix.domino.jacksonapt.JacksonContext;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;

public class JsDoubleArrayReader extends BaseJsNumberArrayReader implements JacksonContext.DoubleArrayReader {
    @Override
    public double[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static double[] reinterpretCast(JsArray<JsNumber> value) {
        JsNumber[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }
}
