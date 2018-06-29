package org.dominokit.jacksonapt.deser.array.cast;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * <p>JsDoubleArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsDoubleArrayReader extends BaseJsNumberArrayReader implements JacksonContext.DoubleArrayReader {
    /** {@inheritDoc} */
    @Override
    public double[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static double[] reinterpretCast(JsArray<JsNumber> value) {
        JsNumber[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }
}
