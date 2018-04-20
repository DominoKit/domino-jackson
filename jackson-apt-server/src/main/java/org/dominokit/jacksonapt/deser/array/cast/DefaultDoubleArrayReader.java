package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import java.util.Stack;

public class DefaultDoubleArrayReader implements JacksonContext.DoubleArrayReader {
    @Override
    public double[] readArray(JsonReader reader) {
        Stack<Double> doubleStack = new Stack<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                doubleStack.push(null);
            } else {
                doubleStack.push(reader.nextDouble());
            }
        }
        reader.endArray();
        return doubleStack.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
