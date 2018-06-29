package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import java.util.Stack;

/**
 * <p>DefaultIntegerArrayReader class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@GwtIncompatible
public class DefaultIntegerArrayReader implements JacksonContext.IntegerArrayReader {
    /** {@inheritDoc} */
    @Override
    public int[] readArray(JsonReader reader) {
        Stack<Integer> intStack = new Stack<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                intStack.push(null);
            } else {
                intStack.push(reader.nextInt());
            }
        }
        reader.endArray();
        return intStack.stream().mapToInt(Integer::intValue).toArray();
    }
}
