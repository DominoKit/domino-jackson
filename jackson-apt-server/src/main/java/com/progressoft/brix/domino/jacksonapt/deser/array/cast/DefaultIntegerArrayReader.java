package com.progressoft.brix.domino.jacksonapt.deser.array.cast;

import com.progressoft.brix.domino.jacksonapt.JacksonContext;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import com.progressoft.brix.domino.jacksonapt.stream.JsonToken;

import java.util.Stack;

public class DefaultIntegerArrayReader implements JacksonContext.IntegerArrayReader {
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
