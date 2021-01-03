package org.dominokit.jacksonapt.deser.array.cast;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

/**
 * <p>Abstract BaseJsNumberArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class BaseJsNumberArrayReader {

    JsArray<JsNumber> readNumberArray(JsonReader reader){
        JsArray<JsNumber> jsArray = new JsArray<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                jsArray.push(null);
            } else {
                jsArray.push((JsNumber) Js.cast(read(reader)));
            }
        }
        reader.endArray();

        return jsArray;
    }

	protected abstract double read(JsonReader reader);

}
