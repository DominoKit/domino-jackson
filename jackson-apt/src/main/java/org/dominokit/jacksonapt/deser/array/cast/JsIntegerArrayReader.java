package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;

/**
 * <p>JsIntegerArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsIntegerArrayReader extends BaseJsNumberArrayReader implements JacksonContext.IntegerArrayReader {
    /** {@inheritDoc} */
    @Override
    public int[] readArray(JsonReader reader) {
        return reinterpretCast(super.readNumberArray(reader));
    }

    private static int[] reinterpretCast(JsArray<JsNumber> value) {
    	int[] result = new int[value.length];
    	for (int i = 0; i < value.length; i++) {
			JsNumber number = value.getAt(i);
			result[i] = Double.valueOf(number.valueOf()).intValue();
		}
        return result;
    }

	@Override
	protected double read(JsonReader reader) {
		return reader.nextInt();
}

}
