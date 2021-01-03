package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;

/**
 * <p>JsShortArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsShortArrayReader extends BaseJsNumberArrayReader implements JacksonContext.ShortArrayReader {
    /** {@inheritDoc} */
    @Override
    public short[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static short[] reinterpretCast(JsArray<JsNumber> value) {
    	short[] result = new short[value.length];
    	for (int i = 0; i < value.length; i++) {
			JsNumber number = value.getAt(i);
			result[i] = Double.valueOf(number.valueOf()).shortValue();
		}
        return result;
    }

    @Override
	protected double read(JsonReader reader) {
		return reader.nextInt();
}

}
