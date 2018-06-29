package org.dominokit.jacksonapt.deser.array.cast;

import elemental2.core.JsArray;
import elemental2.core.JsString;
import jsinterop.base.Js;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

/**
 * <p>JsStringArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsStringArrayReader implements JacksonContext.StringArrayReader {
    /** {@inheritDoc} */
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
