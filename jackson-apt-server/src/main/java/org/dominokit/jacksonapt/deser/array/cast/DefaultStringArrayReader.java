package org.dominokit.jacksonapt.deser.array.cast;

import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import java.util.Stack;

public class DefaultStringArrayReader implements JacksonContext.StringArrayReader {
    @Override
    public String[] readArray(JsonReader reader) {
        Stack<String> stringStack = new Stack<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                stringStack.push(null);
            } else {
                stringStack.push(reader.nextString());
            }
        }
        reader.endArray();

        return stringStack.toArray(new String[stringStack.size()]);
    }
}
