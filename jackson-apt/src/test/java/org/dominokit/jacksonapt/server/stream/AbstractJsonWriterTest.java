/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jacksonapt.server.stream;

import org.dominokit.jacksonapt.server.ServerJacksonTestCase;
import org.dominokit.jacksonapt.stream.JsonWriter;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@SuppressWarnings("resource")
public abstract class AbstractJsonWriterTest extends ServerJacksonTestCase {

    public abstract JsonWriter newJsonWriter();

    @Test
	public void testWrongTopLevelType() {
        JsonWriter jsonWriter = newJsonWriter();
        try {
            jsonWriter.value("a");
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testTwoNames() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("a");
        try {
            jsonWriter.name("a");
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testNameWithoutValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("a");
        try {
            jsonWriter.endObject();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testValueWithoutName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        try {
            jsonWriter.value(true);
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray().endArray();
        try {
            jsonWriter.beginArray();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testBadNestingObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginObject();
        try {
            jsonWriter.endArray();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testBadNestingArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginArray();
        try {
            jsonWriter.endObject();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testNullName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        try {
            jsonWriter.name(null);
            fail("failed");
        } catch (NullPointerException expected) {
        }
    }

    @Test
	public void testNullStringValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("a");
        jsonWriter.value((String) null);
        jsonWriter.endObject();
        assertThat("{\"a\":null}").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testNonFiniteDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        try {
            jsonWriter.value(Double.NaN);
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
        try {
            jsonWriter.value(Double.NEGATIVE_INFINITY);
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
        try {
            jsonWriter.value(Double.POSITIVE_INFINITY);
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
	public void testNonFiniteBoxedDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        try {
            jsonWriter.value(new Double(Double.NaN));
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
        try {
            jsonWriter.value(new Double(Double.NEGATIVE_INFINITY));
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
        try {
            jsonWriter.value(new Double(Double.POSITIVE_INFINITY));
            fail("failed");
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
	public void testDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value(-0.0);
        jsonWriter.value(1.0);
        jsonWriter.value(Double.MAX_VALUE);
        jsonWriter.value(Double.MIN_VALUE);
        jsonWriter.value(0.0);
        jsonWriter.value(-0.5);
        jsonWriter.value(2.2250738585072014E-308);
        jsonWriter.value(Math.PI);
        jsonWriter.value(Math.E);
        jsonWriter.endArray();

            assertThat("[-0.0," + "1.0," + "1.7976931348623157E308," + "4.9E-324," + "0.0," + "-0.5," + "2.2250738585072014E-308," +
                    "" + "3.141592653589793," + "2.718281828459045]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testLongs() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value(0);
        jsonWriter.value(1);
        jsonWriter.value(-1);
        jsonWriter.value(Long.MIN_VALUE);
        jsonWriter.value(Long.MAX_VALUE);
        jsonWriter.endArray();
        jsonWriter.close();
        assertThat("[0," + "1," + "-1," + "-9223372036854775808," + "9223372036854775807]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testNumbers() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value(new BigInteger("0"));
        jsonWriter.value(new BigInteger("9223372036854775808"));
        jsonWriter.value(new BigInteger("-9223372036854775809"));
        jsonWriter.value(new BigDecimal("3.141592653589793238462643383"));
        jsonWriter.endArray();
        jsonWriter.close();
        assertThat("[0," + "9223372036854775808," + "-9223372036854775809," + "3.141592653589793238462643383]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testBooleans() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value(true);
        jsonWriter.value(false);
        jsonWriter.endArray();
        assertThat("[true,false]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testNulls() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.nullValue();
        jsonWriter.endArray();
        assertThat("[null]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testStrings() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value("a");
        jsonWriter.value("a\"");
        jsonWriter.value("\"");
        jsonWriter.value(":");
        jsonWriter.value(",");
        jsonWriter.value("\b");
        jsonWriter.value("\f");
        jsonWriter.value("\n");
        jsonWriter.value("\r");
        jsonWriter.value("\t");
        jsonWriter.value(" ");
        jsonWriter.value("\\");
        jsonWriter.value("{");
        jsonWriter.value("}");
        jsonWriter.value("[");
        jsonWriter.value("]");
        jsonWriter.value("\0");
        jsonWriter.value("\u0019");
        jsonWriter.endArray();
        assertThat("[\"a\"," + "\"a\\\"\"," + "\"\\\"\"," + "\":\"," + "\",\"," + "\"\\b\"," + "\"\\f\"," + "\"\\n\"," + "\"\\r\"," +
                        "" + "\"\\t\"," + "\" \"," + "\"\\\\\"," + "\"{\"," + "\"}\"," + "\"[\"," + "\"]\"," + "\"\\u0000\"," + "\"\\u0019\"]").isEqualTo(
                jsonWriter
                        .getOutput());
    }

    @Ignore
    //the JSON utils is being removed and JSON.stringify is being used instead of JSONUtils.escapeValue
    public void ignoredTestUnicodeLineBreaksEscaped() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value("\u2028\u2029");
        jsonWriter.endArray();
        assertThat("[\"\\u2028\\u2029\"]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testEmptyArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        assertThat("[]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testEmptyObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.endObject();
        assertThat("{}").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testObjectsInArrays() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginObject();
        jsonWriter.name("a").value(5);
        jsonWriter.name("b").value(false);
        jsonWriter.endObject();
        jsonWriter.beginObject();
        jsonWriter.name("c").value(6);
        jsonWriter.name("d").value(true);
        jsonWriter.endObject();
        jsonWriter.endArray();
        assertThat("[{\"a\":5,\"b\":false}," + "{\"c\":6,\"d\":true}]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testArraysInObjects() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("a");
        jsonWriter.beginArray();
        jsonWriter.value(5);
        jsonWriter.value(false);
        jsonWriter.endArray();
        jsonWriter.name("b");
        jsonWriter.beginArray();
        jsonWriter.value(6);
        jsonWriter.value(true);
        jsonWriter.endArray();
        jsonWriter.endObject();
        assertThat("{\"a\":[5,false]," + "\"b\":[6,true]}").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testDeepNestingArrays() {
        JsonWriter jsonWriter = newJsonWriter();
        for (int i = 0; i < 20; i++) {
            jsonWriter.beginArray();
        }
        for (int i = 0; i < 20; i++) {
            jsonWriter.endArray();
        }
        assertThat("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testDeepNestingObjects() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        for (int i = 0; i < 20; i++) {
            jsonWriter.name("a");
            jsonWriter.beginObject();
        }
        for (int i = 0; i < 20; i++) {
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
        assertThat("{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":" +
                "{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{" + "}}}}}}}}}}}}}}}}}}}}}").isEqualTo(jsonWriter
                .getOutput());
    }

    @Test
	public void testRepeatedName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("a").value(true);
        jsonWriter.name("a").value(false);
        jsonWriter.endObject();
        // JsonWriter doesn't attempt to detect duplicate names
        assertThat("{\"a\":true,\"a\":false}").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testPrettyPrintObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setIndent("   ");

        jsonWriter.beginObject();
        jsonWriter.name("a").value(true);
        jsonWriter.name("b").value(false);
        jsonWriter.name("c").value(5);
        jsonWriter.name("e").nullValue();
        jsonWriter.name("f").beginArray();
        jsonWriter.value(6);
        jsonWriter.value(7);
        jsonWriter.endArray();
        jsonWriter.name("g").beginObject();
        jsonWriter.name("h").value(8);
        jsonWriter.name("i").value(9);
        jsonWriter.endObject();
        jsonWriter.endObject();

        String expected = "{\n" + "   \"a\": true,\n" + "   \"b\": false,\n" + "   \"c\": 5,\n" + "   \"e\": null," +
                "\n" + "   \"f\": [\n" + "      6,\n" + "      7\n" + "   ],\n" + "   \"g\": {\n" + "      \"h\": 8," +
                "\n" + "      \"i\": 9\n" + "   }\n" + "}";
        assertThat(expected).isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testPrettyPrintArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setIndent("   ");

        jsonWriter.beginArray();
        jsonWriter.value(true);
        jsonWriter.value(false);
        jsonWriter.value(5);
        jsonWriter.nullValue();
        jsonWriter.beginObject();
        jsonWriter.name("a").value(6);
        jsonWriter.name("b").value(7);
        jsonWriter.endObject();
        jsonWriter.beginArray();
        jsonWriter.value(8);
        jsonWriter.value(9);
        jsonWriter.endArray();
        jsonWriter.endArray();

        String expected = "[\n" + "   true,\n" + "   false,\n" + "   5,\n" + "   null,\n" + "   {\n" + "      \"a\": 6," +
                "\n" + "      \"b\": 7\n" + "   },\n" + "   [\n" + "      8,\n" + "      9\n" + "   ]\n" + "]";
        assertThat(expected).isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testLenientWriterPermitsMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setLenient(true);
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        assertThat("[][]").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testStrictWriterDoesNotPermitMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        try {
            jsonWriter.beginArray();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testClosedWriterThrowsOnStructure() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.beginArray();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
        try {
            jsonWriter.endArray();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
        try {
            jsonWriter.beginObject();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
        try {
            jsonWriter.endObject();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testClosedWriterThrowsOnName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.name("a");
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testClosedWriterThrowsOnValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.value("a");
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testClosedWriterThrowsOnFlush() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.flush();
            fail("failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
	public void testWriterCloseIsIdempotent() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        jsonWriter.close();
    }

    @Test
	public void testEscaping() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name("\"json\"");
        jsonWriter.value("{\"key\":\"value\"}");
        jsonWriter.endObject();
        jsonWriter.close();

        assertThat("{\"\\\"json\\\"\":\"{\\\"key\\\":\\\"value\\\"}\"}").isEqualTo(jsonWriter.getOutput());
    }

    @Test
	public void testNoEscaping() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.unescapeName("\"json\"");
        jsonWriter.unescapeValue("{\"key\":\"value\"}");
        jsonWriter.endObject();
        jsonWriter.close();

        assertThat("{\"\"json\"\":\"{\"key\":\"value\"}\"}").isEqualTo(jsonWriter.getOutput());
    }


}
