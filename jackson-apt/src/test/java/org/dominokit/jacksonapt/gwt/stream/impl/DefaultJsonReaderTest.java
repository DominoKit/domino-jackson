package org.dominokit.jacksonapt.gwt.stream.impl;

import org.dominokit.jacksonapt.gwt.stream.AbstractJsonReaderTest;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;
import org.dominokit.jacksonapt.stream.impl.DefaultJsonReader;
import org.dominokit.jacksonapt.stream.impl.MalformedJsonException;
import org.dominokit.jacksonapt.stream.impl.StringReader;

/**
 * @author Nicolas Morel
 */
public class DefaultJsonReaderTest extends AbstractJsonReaderTest {

    @Override
    public JsonReader newJsonReader(String input) {
        return new DefaultJsonReader(new StringReader(input));
    }

    public void testStrictVeryLongNumber() {
        JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
        reader.beginArray();
        try {
            assertEquals(1d, reader.nextDouble());
            fail();
        } catch (MalformedJsonException expected) {
        }
    }

    public void testLenientVeryLongNumber() {
        JsonReader reader = newJsonReader("[0." + repeat('9', 8192) + "]");
        reader.setLenient(true);
        reader.beginArray();
        assertEquals(JsonToken.STRING, reader.peek());
        assertEquals(1d, reader.nextDouble());
        reader.endArray();
        assertEquals(JsonToken.END_DOCUMENT, reader.peek());
    }
}
