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
    	double[] result = new double[value.length];
    	for (int i = 0; i < value.length; i++) {
			JsNumber number = value.getAt(i);
			result[i] = number.valueOf();
		}
        return result;
    }

	@Override
	protected double read(JsonReader reader) {
		return reader.nextDouble();
}

}
