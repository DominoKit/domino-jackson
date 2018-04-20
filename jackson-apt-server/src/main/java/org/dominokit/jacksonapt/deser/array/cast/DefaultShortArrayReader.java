package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import java.util.Stack;

public class DefaultShortArrayReader implements JacksonContext.ShortArrayReader {
    @Override
    public short[] readArray(JsonReader reader) {
        Stack<Short> shortStack = new Stack<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                shortStack.push(null);
            } else {
                shortStack.push(new Integer(reader.nextInt()).shortValue());
            }
        }
        reader.endArray();
        short[] shorts = new short[shortStack.size()];
        for (int i = 0; i < shortStack.size(); i++) {
            shorts[i] = shortStack.get(i);
        }
        return shorts;
    }

}
