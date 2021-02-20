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

package org.dominokit.jackson.server.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.dominokit.jackson.stream.JsonToken.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;
import org.dominokit.jackson.exception.JsonDeserializationException;
import org.dominokit.jackson.server.ServerJacksonTestCase;
import org.dominokit.jackson.stream.JsonReader;
import org.dominokit.jackson.stream.JsonToken;
import org.dominokit.jackson.stream.impl.MalformedJsonException;
import org.dominokit.jackson.stream.impl.StringReader;
import org.junit.Test;

@SuppressWarnings("resource")
public abstract class AbstractJsonReaderTest extends ServerJacksonTestCase {

  private static final Logger LOGGER = Logger.getLogger(AbstractJsonReaderTest.class.getName());

  public abstract JsonReader newJsonReader(String input);

  @Test
  public void testReadArray() {
    JsonReader reader = newJsonReader("[true, true]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(reader.nextBoolean()).isTrue();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testReadEmptyArray() {
    JsonReader reader = newJsonReader("[]");
    reader.beginArray();
    assertThat(reader.hasNext()).isFalse();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testReadObject() {
    JsonReader reader = newJsonReader("{\"a\": \"android\", \"b\": \"banana\"}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat("android").isEqualTo(reader.nextString());
    assertThat("b").isEqualTo(reader.nextName());
    assertThat("banana").isEqualTo(reader.nextString());
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testReadEmptyObject() {
    JsonReader reader = newJsonReader("{}");
    reader.beginObject();
    assertThat(reader.hasNext()).isFalse();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipArray() {
    JsonReader reader = newJsonReader("{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    reader.skipValue();
    assertThat("b").isEqualTo(reader.nextName());
    assertThat(123).isEqualTo(reader.nextInt());
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipArrayAfterPeek() throws Exception {
    JsonReader reader = newJsonReader("{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat(BEGIN_ARRAY).isEqualTo(reader.peek());
    reader.skipValue();
    assertThat("b").isEqualTo(reader.nextName());
    assertThat(123).isEqualTo(reader.nextInt());
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipTopLevelObject() throws Exception {
    JsonReader reader = newJsonReader("{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}");
    reader.skipValue();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipObject() {
    JsonReader reader =
        newJsonReader("{\"a\": { \"c\": [], \"d\": [true, true, {}] }, \"b\": \"banana\"}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    reader.skipValue();
    assertThat("b").isEqualTo(reader.nextName());
    reader.skipValue();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipObjectAfterPeek() throws Exception {
    String json =
        "{"
            + "  \"one\": { \"num\": 1 }"
            + ", \"two\": { \"num\": 2 }"
            + ", \"three\": { \"num\": 3 }"
            + "}";
    JsonReader reader = newJsonReader(json);
    reader.beginObject();
    assertThat("one").isEqualTo(reader.nextName());
    assertThat(BEGIN_OBJECT).isEqualTo(reader.peek());
    reader.skipValue();
    assertThat("two").isEqualTo(reader.nextName());
    assertThat(BEGIN_OBJECT).isEqualTo(reader.peek());
    reader.skipValue();
    assertThat("three").isEqualTo(reader.nextName());
    reader.skipValue();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipInteger() {
    JsonReader reader = newJsonReader("{\"a\":123456789,\"b\":-123456789}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    reader.skipValue();
    assertThat("b").isEqualTo(reader.nextName());
    reader.skipValue();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipDouble() {
    JsonReader reader = newJsonReader("{\"a\":-123.456e-789,\"b\":123456789.0}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    reader.skipValue();
    assertThat("b").isEqualTo(reader.nextName());
    reader.skipValue();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testHelloWorld() {
    String json = "{\n" + "   \"hello\": true,\n" + "   \"foo\": [\"world\"]\n" + "}";
    JsonReader reader = newJsonReader(json);
    reader.beginObject();
    assertThat("hello").isEqualTo(reader.nextName());
    assertThat(reader.nextBoolean()).isTrue();
    assertThat("foo").isEqualTo(reader.nextName());
    reader.beginArray();
    assertThat("world").isEqualTo(reader.nextString());
    reader.endArray();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testNulls() {
    try {
      newJsonReader(null);
      fail("failed");
    } catch (NullPointerException expected) {
    }
  }

  @Test
  public void testEmptyString() {
    try {
      newJsonReader("").beginArray();
    } catch (JsonDeserializationException expected) {
    }
    try {
      newJsonReader("").beginObject();
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testNoTopLevelObject() {
    try {
      newJsonReader("true").nextBoolean();
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testCharacterUnescaping() {
    String json =
        "[\"a\","
            + "\"a\\\"\","
            + "\"\\\"\","
            + "\":\","
            + "\",\","
            + "\"\\b\","
            + "\"\\f\","
            + "\"\\n\","
            + "\"\\r\","
            + ""
            + "\"\\t\","
            + "\" \","
            + "\"\\\\\","
            + "\"{\","
            + "\"}\","
            + "\"[\","
            + "\"]\","
            + "\"\\u0000\","
            + "\"\\u0019\","
            + ""
            + "\"\\u20AC\""
            + "]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    assertThat("a").isEqualTo(reader.nextString());
    assertThat("a\"").isEqualTo(reader.nextString());
    assertThat("\"").isEqualTo(reader.nextString());
    assertThat(":").isEqualTo(reader.nextString());
    assertThat(",").isEqualTo(reader.nextString());
    assertThat("\b").isEqualTo(reader.nextString());
    assertThat("\f").isEqualTo(reader.nextString());
    assertThat("\n").isEqualTo(reader.nextString());
    assertThat("\r").isEqualTo(reader.nextString());
    assertThat("\t").isEqualTo(reader.nextString());
    assertThat(" ").isEqualTo(reader.nextString());
    assertThat("\\").isEqualTo(reader.nextString());
    assertThat("{").isEqualTo(reader.nextString());
    assertThat("}").isEqualTo(reader.nextString());
    assertThat("[").isEqualTo(reader.nextString());
    assertThat("]").isEqualTo(reader.nextString());
    assertThat("\0").isEqualTo(reader.nextString());
    assertThat("\u0019").isEqualTo(reader.nextString());
    assertThat("\u20AC").isEqualTo(reader.nextString());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testUnescapingInvalidCharacters() {
    String json = "[\"\\u000g\"]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
  }

  @Test
  public void testUnescapingTruncatedCharacters() {
    String json = "[\"\\u000";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testUnescapingTruncatedSequence() {
    String json = "[\"\\";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testIntegersWithFractionalPartSpecified() {
    JsonReader reader = newJsonReader("[1.0,1.0,1.0]");
    reader.beginArray();
    assertThat(1.0).isEqualTo(reader.nextDouble());
    assertThat(1).isEqualTo(reader.nextInt());
    assertThat(1L).isEqualTo(reader.nextLong());
  }

  @Test
  public void testDoubles() {
    String json =
        "[-0.0,"
            + "1.0,"
            + "1.7976931348623157E308,"
            + "4.9E-324,"
            + "0.0,"
            + "-0.5,"
            + "2.2250738585072014E-308,"
            + ""
            + "3.141592653589793,"
            + "2.718281828459045]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    assertThat(-0.0).isEqualTo(reader.nextDouble());
    assertThat(1.0).isEqualTo(reader.nextDouble());
    assertThat(1.7976931348623157E308).isEqualTo(reader.nextDouble());
    assertThat(4.9E-324).isEqualTo(reader.nextDouble());
    assertThat(0.0).isEqualTo(reader.nextDouble());
    assertThat(-0.5).isEqualTo(reader.nextDouble());
    assertThat(2.2250738585072014E-308).isEqualTo(reader.nextDouble());
    assertThat(3.141592653589793).isEqualTo(reader.nextDouble());
    assertThat(2.718281828459045).isEqualTo(reader.nextDouble());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testStrictNonFiniteDoubles() {
    String json = "[NaN]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.nextDouble();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testStrictQuotedNonFiniteDoubles() {
    String json = "[\"NaN\"]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.nextDouble();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testLenientNonFiniteDoubles() {
    String json = "[NaN, -Infinity, Infinity]";
    JsonReader reader = newJsonReader(json);
    reader.setLenient(true);
    reader.beginArray();
    assertThat(Double.isNaN(reader.nextDouble())).isTrue();
    assertThat(Double.NEGATIVE_INFINITY).isEqualTo(reader.nextDouble());
    assertThat(Double.POSITIVE_INFINITY).isEqualTo(reader.nextDouble());
    reader.endArray();
  }

  @Test
  public void testLenientQuotedNonFiniteDoubles() {
    String json = "[\"NaN\", \"-Infinity\", \"Infinity\"]";
    JsonReader reader = newJsonReader(json);
    reader.setLenient(true);
    reader.beginArray();
    assertThat(Double.isNaN(reader.nextDouble())).isTrue();
    assertThat(Double.NEGATIVE_INFINITY).isEqualTo(reader.nextDouble());
    assertThat(Double.POSITIVE_INFINITY).isEqualTo(reader.nextDouble());
    reader.endArray();
  }

  @Test
  public void testStrictNonFiniteDoublesWithSkipValue() {
    String json = "[NaN]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testLongs() {
    String json =
        "[0,0,0," + "1,1,1," + "-1,-1,-1," + "-9223372036854775808," + "9223372036854775807]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    assertThat(0L).isEqualTo(reader.nextLong());
    assertThat(0).isEqualTo(reader.nextInt());
    assertThat(0.0).isEqualTo(reader.nextDouble());
    assertThat(1L).isEqualTo(reader.nextLong());
    assertThat(1).isEqualTo(reader.nextInt());
    assertThat(1.0).isEqualTo(reader.nextDouble());
    assertThat(-1L).isEqualTo(reader.nextLong());
    assertThat(-1).isEqualTo(reader.nextInt());
    assertThat(-1.0).isEqualTo(reader.nextDouble());
    try {
      reader.nextInt();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
    assertThat(Long.MIN_VALUE).isEqualTo(reader.nextLong());
    try {
      reader.nextInt();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
    assertThat(Long.MAX_VALUE).isEqualTo(reader.nextLong());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  public void disabled_testNumberWithOctalPrefix() {
    String json = "[01]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    try {
      reader.peek();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
    try {
      reader.nextInt();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
    try {
      reader.nextLong();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
    try {
      reader.nextDouble();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
    assertThat("01").isEqualTo(reader.nextString());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testBooleans() {
    JsonReader reader = newJsonReader("[true,false]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(reader.nextBoolean()).isFalse();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testPeekingUnquotedStringsPrefixedWithBooleans() {
    JsonReader reader = newJsonReader("[truey]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    assertThat("truey").isEqualTo(reader.nextString());
    reader.endArray();
  }

  @Test
  public void testMalformedNumbers() {
    assertNotANumber("-");
    assertNotANumber(".");

    // exponent lacks digit
    assertNotANumber("e");
    assertNotANumber("0e");
    assertNotANumber(".e");
    assertNotANumber("0.e");
    assertNotANumber("-.0e");

    // no integer
    assertNotANumber("e1");
    assertNotANumber(".e1");
    assertNotANumber("-e1");

    // trailing characters
    assertNotANumber("1x");
    assertNotANumber("1.1x");
    assertNotANumber("1e1x");
    assertNotANumber("1ex");
    assertNotANumber("1.1ex");
    assertNotANumber("1.1e1x");

    // fraction has no digit
    assertNotANumber("0.");
    assertNotANumber("-0.");
    assertNotANumber("0.e1");
    assertNotANumber("-0.e1");

    // no leading digit
    assertNotANumber(".0");
    assertNotANumber("-.0");
    assertNotANumber(".0e1");
    assertNotANumber("-.0e1");
  }

  private void assertNotANumber(String s) {
    JsonReader reader = newJsonReader("[" + s + "]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(JsonToken.STRING).isEqualTo(reader.peek());
    assertThat(s).isEqualTo(reader.nextString());
    reader.endArray();
  }

  @Test
  public void testPeekingUnquotedStringsPrefixedWithIntegers() {
    JsonReader reader = newJsonReader("[12.34e5x]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
    try {
      reader.nextInt();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    assertThat("12.34e5x").isEqualTo(reader.nextString());
  }

  @Test
  public void testPeekLongMinValue() {
    JsonReader reader = newJsonReader("[-9223372036854775808]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    assertThat(-9223372036854775808L).isEqualTo(reader.nextLong());
  }

  @Test
  public void testPeekLongMaxValue() {
    JsonReader reader = newJsonReader("[9223372036854775807]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    assertThat(9223372036854775807L).isEqualTo(reader.nextLong());
  }

  @Test
  public void testLongLargerThanMaxLongThatWrapsAround() {
    JsonReader reader = newJsonReader("[22233720368547758070]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    try {
      reader.nextLong();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
  }

  @Test
  public void testLongLargerThanMinLongThatWrapsAround() {
    JsonReader reader = newJsonReader("[-22233720368547758070]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    try {
      reader.nextLong();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
  }

  /**
   * This test fails because there's no double for 9223372036854775808, and our long parsing uses
   * Double.parseDouble() for fractional values.
   */
  public void disabled_testPeekLargerThanLongMaxValue() {
    JsonReader reader = newJsonReader("[9223372036854775808]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    try {
      reader.nextLong();
      fail("failed");
    } catch (NumberFormatException e) {
    }
  }

  /**
   * This test fails because there's no double for -9223372036854775809, and our long parsing uses
   * Double.parseDouble() for fractional values.
   */
  public void disabled_testPeekLargerThanLongMinValue() {
    JsonReader reader = newJsonReader("[-9223372036854775809]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    try {
      reader.nextLong();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
    assertThat(-9223372036854775809d).isEqualTo(reader.nextDouble());
  }

  /**
   * This test fails because there's no double for 9223372036854775806, and our long parsing uses
   * Double.parseDouble() for fractional values.
   */
  public void disabled_testHighPrecisionLong() {
    String json = "[9223372036854775806.000]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    assertThat(9223372036854775806L).isEqualTo(reader.nextLong());
    reader.endArray();
  }

  @Test
  public void testPeekMuchLargerThanLongMinValue() {
    JsonReader reader = newJsonReader("[-92233720368547758080]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(NUMBER).isEqualTo(reader.peek());
    try {
      reader.nextLong();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
    assertThat(-92233720368547758080d).isEqualTo(reader.nextDouble());
  }

  @Test
  public void testQuotedNumberWithEscape() {
    JsonReader reader = newJsonReader("[\"12\u00334\"]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
    assertThat(1234).isEqualTo(reader.nextInt());
  }

  @Test
  public void testMixedCaseLiterals() {
    JsonReader reader = newJsonReader("[True,TruE,False,FALSE,NULL,nulL]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(reader.nextBoolean()).isFalse();
    assertThat(reader.nextBoolean()).isFalse();
    reader.nextNull();
    reader.nextNull();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testMissingValue() {
    JsonReader reader = newJsonReader("{\"a\":}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testPrematureEndOfInput() {
    JsonReader reader = newJsonReader("{\"a\":true,");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.nextName();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testPrematurelyClosed() {
    try {
      JsonReader reader = newJsonReader("{\"a\":[]}");
      reader.beginObject();
      reader.close();
      reader.nextName();
      fail("failed");
    } catch (IllegalStateException expected) {
    }

    try {
      JsonReader reader = newJsonReader("{\"a\":[]}");
      reader.close();
      reader.beginObject();
      fail("failed");
    } catch (IllegalStateException expected) {
    }

    try {
      JsonReader reader = newJsonReader("{\"a\":true}");
      reader.beginObject();
      reader.nextName();
      reader.peek();
      reader.close();
      reader.nextBoolean();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
  }

  @Test
  public void testNextFailuresDoNotAdvance() {
    JsonReader reader = newJsonReader("{\"a\":true}");
    reader.beginObject();
    try {
      reader.nextString();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.nextName();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.beginArray();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.endArray();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.beginObject();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.endObject();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.nextString();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.nextName();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.beginArray();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    try {
      reader.endArray();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
    reader.close();
  }

  @Test
  public void testIntegerMismatchFailuresDoNotAdvance() {
    JsonReader reader = newJsonReader("[1.5]");
    reader.beginArray();
    try {
      reader.nextInt();
      fail("failed");
    } catch (NumberFormatException expected) {
    }
    assertThat(1.5d).isEqualTo(reader.nextDouble());
    reader.endArray();
  }

  @Test
  public void testStringNullIsNotNull() {
    JsonReader reader = newJsonReader("[\"null\"]");
    reader.beginArray();
    try {
      reader.nextNull();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
  }

  @Test
  public void testNullLiteralIsNotAString() {
    JsonReader reader = newJsonReader("[null]");
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (IllegalStateException expected) {
    }
  }

  @Test
  public void testStrictNameValueSeparator() {
    JsonReader reader = newJsonReader("{\"a\"=true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("{\"a\"=>true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientNameValueSeparator() {
    JsonReader reader = newJsonReader("{\"a\"=true}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat(reader.nextBoolean()).isTrue();

    reader = newJsonReader("{\"a\"=>true}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat(reader.nextBoolean()).isTrue();
  }

  @Test
  public void testStrictNameValueSeparatorWithSkipValue() {
    JsonReader reader = newJsonReader("{\"a\"=true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("{\"a\"=>true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testCommentsInStringValue() throws Exception {
    JsonReader reader = newJsonReader("[\"// comment\"]");
    reader.beginArray();
    assertThat("// comment").isEqualTo(reader.nextString());
    reader.endArray();

    reader = newJsonReader("{\"a\":\"#someComment\"}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat("#someComment").isEqualTo(reader.nextString());
    reader.endObject();

    reader = newJsonReader("{\"#//a\":\"#some //Comment\"}");
    reader.beginObject();
    assertThat("#//a").isEqualTo(reader.nextName());
    assertThat("#some //Comment").isEqualTo(reader.nextString());
    reader.endObject();
  }

  @Test
  public void testStrictComments() {
    JsonReader reader = newJsonReader("[// comment \n true]");
    reader.beginArray();
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[# comment \n true]");
    reader.beginArray();
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[/* comment */ true]");
    reader.beginArray();
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientComments() {
    JsonReader reader = newJsonReader("[// comment \n true]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();

    reader = newJsonReader("[# comment \n true]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();

    reader = newJsonReader("[/* comment */ true]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
  }

  @Test
  public void testStrictCommentsWithSkipValue() {
    JsonReader reader = newJsonReader("[// comment \n true]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[# comment \n true]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[/* comment */ true]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictUnquotedNames() {
    JsonReader reader = newJsonReader("{a:true}");
    reader.beginObject();
    try {
      reader.nextName();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientUnquotedNames() {
    JsonReader reader = newJsonReader("{a:true}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
  }

  @Test
  public void testStrictUnquotedNamesWithSkipValue() {
    JsonReader reader = newJsonReader("{a:true}");
    reader.beginObject();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictSingleQuotedNames() {
    JsonReader reader = newJsonReader("{'a':true}");
    reader.beginObject();
    try {
      reader.nextName();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientSingleQuotedNames() {
    JsonReader reader = newJsonReader("{'a':true}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
  }

  @Test
  public void testStrictSingleQuotedNamesWithSkipValue() {
    JsonReader reader = newJsonReader("{'a':true}");
    reader.beginObject();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictUnquotedStrings() {
    JsonReader reader = newJsonReader("[a]");
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testStrictUnquotedStringsWithSkipValue() {
    JsonReader reader = newJsonReader("[a]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testLenientUnquotedStrings() {
    JsonReader reader = newJsonReader("[a]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat("a").isEqualTo(reader.nextString());
  }

  @Test
  public void testStrictSingleQuotedStrings() {
    JsonReader reader = newJsonReader("['a']");
    reader.beginArray();
    try {
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientSingleQuotedStrings() {
    JsonReader reader = newJsonReader("['a']");
    reader.setLenient(true);
    reader.beginArray();
    assertThat("a").isEqualTo(reader.nextString());
  }

  @Test
  public void testStrictSingleQuotedStringsWithSkipValue() {
    JsonReader reader = newJsonReader("['a']");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictSemicolonDelimitedArray() {
    JsonReader reader = newJsonReader("[true;true]");
    reader.beginArray();
    try {
      reader.nextBoolean();
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientSemicolonDelimitedArray() {
    JsonReader reader = newJsonReader("[true;true]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(reader.nextBoolean()).isTrue();
  }

  @Test
  public void testStrictSemicolonDelimitedArrayWithSkipValue() {
    JsonReader reader = newJsonReader("[true;true]");
    reader.beginArray();
    try {
      reader.skipValue();
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictSemicolonDelimitedNameValuePair() {
    JsonReader reader = newJsonReader("{\"a\":true;\"b\":true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.nextBoolean();
      reader.nextName();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientSemicolonDelimitedNameValuePair() {
    JsonReader reader = newJsonReader("{\"a\":true;\"b\":true}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat(reader.nextBoolean()).isTrue();
    assertThat("b").isEqualTo(reader.nextName());
  }

  @Test
  public void testStrictSemicolonDelimitedNameValuePairWithSkipValue() {
    JsonReader reader = newJsonReader("{\"a\":true;\"b\":true}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    try {
      reader.skipValue();
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictUnnecessaryArraySeparators() {
    JsonReader reader = newJsonReader("[true,,true]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.nextNull();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[,true]");
    reader.beginArray();
    try {
      reader.nextNull();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[true,]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.nextNull();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[,]");
    reader.beginArray();
    try {
      reader.nextNull();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientUnnecessaryArraySeparators() {
    JsonReader reader = newJsonReader("[true,,true]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    reader.nextNull();
    assertThat(reader.nextBoolean()).isTrue();
    reader.endArray();

    reader = newJsonReader("[,true]");
    reader.setLenient(true);
    reader.beginArray();
    reader.nextNull();
    assertThat(reader.nextBoolean()).isTrue();
    reader.endArray();

    reader = newJsonReader("[true,]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    reader.nextNull();
    reader.endArray();

    reader = newJsonReader("[,]");
    reader.setLenient(true);
    reader.beginArray();
    reader.nextNull();
    reader.nextNull();
    reader.endArray();
  }

  @Test
  public void testStrictUnnecessaryArraySeparatorsWithSkipValue() {
    JsonReader reader = newJsonReader("[true,,true]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[,true]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[true,]");
    reader.beginArray();
    assertThat(reader.nextBoolean()).isTrue();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }

    reader = newJsonReader("[,]");
    reader.beginArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictMultipleTopLevelValues() {
    JsonReader reader = newJsonReader("[] []");
    reader.beginArray();
    reader.endArray();
    try {
      reader.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientMultipleTopLevelValues() {
    JsonReader reader = newJsonReader("[] true {}");
    reader.setLenient(true);
    reader.beginArray();
    reader.endArray();
    assertThat(reader.nextBoolean()).isTrue();
    reader.beginObject();
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testStrictMultipleTopLevelValuesWithSkipValue() {
    JsonReader reader = newJsonReader("[] []");
    reader.beginArray();
    reader.endArray();
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictTopLevelString() {
    JsonReader reader = newJsonReader("\"a\"");
    try {
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientTopLevelString() {
    JsonReader reader = newJsonReader("\"a\"");
    reader.setLenient(true);
    assertThat("a").isEqualTo(reader.nextString());
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testStrictTopLevelValueType() {
    JsonReader reader = newJsonReader("true");
    try {
      reader.nextBoolean();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientTopLevelValueType() {
    JsonReader reader = newJsonReader("true");
    reader.setLenient(true);
    assertThat(reader.nextBoolean()).isTrue();
  }

  @Test
  public void testStrictTopLevelValueTypeWithSkipValue() {
    JsonReader reader = newJsonReader("true");
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictNonExecutePrefix() {
    JsonReader reader = newJsonReader(")]}'\n []");
    try {
      reader.beginArray();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testStrictNonExecutePrefixWithSkipValue() {
    JsonReader reader = newJsonReader(")]}'\n []");
    try {
      reader.skipValue();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientNonExecutePrefix() {
    JsonReader reader = newJsonReader(")]}'\n []");
    reader.setLenient(true);
    reader.beginArray();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testLenientNonExecutePrefixWithLeadingWhitespace() {
    JsonReader reader = newJsonReader("\r\n \t)]}'\n []");
    reader.setLenient(true);
    reader.beginArray();
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testLenientPartialNonExecutePrefix() {
    JsonReader reader = newJsonReader(")]}' []");
    reader.setLenient(true);
    try {
      assertThat(")").isEqualTo(reader.nextString());
      reader.nextString();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testBomIgnoredAsFirstCharacterOfDocument() {
    JsonReader reader = newJsonReader("\ufeff[]");
    reader.beginArray();
    reader.endArray();
  }

  @Test
  public void testBomForbiddenAsOtherCharacterInDocument() {
    JsonReader reader = newJsonReader("[\ufeff]");
    reader.beginArray();
    try {
      reader.endArray();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testFailWithPosition() {
    testFailWithPosition("Expected value at line 6 column 5", "[\n\n\n\n\n\"a\",}]");
  }

  @Test
  public void testFailWithPositionGreaterThanBufferSize() {
    String spaces = repeat(' ', 8192);
    testFailWithPosition("Expected value at line 6 column 5", "[\n\n" + spaces + "\n\n\n\"a\",}]");
  }

  @Test
  public void testFailWithPositionOverSlashSlashEndOfLineComment() {
    testFailWithPosition("Expected value at line 5 column 6", "\n// foo\n\n//bar\r\n[\"a\",}");
  }

  @Test
  public void testFailWithPositionOverHashEndOfLineComment() {
    testFailWithPosition("Expected value at line 5 column 6", "\n# foo\n\n#bar\r\n[\"a\",}");
  }

  @Test
  public void testFailWithPositionOverCStyleComment() {
    testFailWithPosition(
        "Expected value at line 6 column 12", "\n\n/* foo\n*\n*\r\nbar */[\"a\",}");
  }

  @Test
  public void testFailWithPositionOverQuotedString() {
    testFailWithPosition("Expected value at line 5 column 3", "[\"foo\nbar\r\nbaz\n\",\n  }");
  }

  @Test
  public void testFailWithPositionOverUnquotedString() {
    testFailWithPosition("Expected value at line 5 column 2", "[\n\nabcd\n\n,}");
  }

  @Test
  public void testFailWithEscapedNewlineCharacter() {
    testFailWithPosition("Expected value at line 5 column 3", "[\n\n\"\\\n\n\",}");
  }

  @Test
  public void testFailWithPositionIsOffsetByBom() {
    testFailWithPosition("Expected value at line 1 column 6", "\ufeff[\"a\",}]");
  }

  private void testFailWithPosition(String message, String json) {
    // Validate that it works reading the string normally.
    JsonReader reader1 = newJsonReader(json);
    reader1.setLenient(true);
    reader1.beginArray();
    reader1.nextString();
    try {
      reader1.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
      assertThat(message).isEqualTo(expected.getMessage());
    }

    // Also validate that it works when skipping.
    JsonReader reader2 = newJsonReader(json);
    reader2.setLenient(true);
    reader2.beginArray();
    reader2.skipValue();
    try {
      reader2.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
      assertThat(message).isEqualTo(expected.getMessage());
    }
  }

  @Test
  public void testVeryLongUnquotedLiteral() {
    String literal = "a" + repeat('b', 8192) + "c";
    JsonReader reader = newJsonReader("[" + literal + "]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(literal).isEqualTo(reader.nextString());
    reader.endArray();
  }

  @Test
  public void testDeeplyNestedArrays() {
    // this is nested 40 levels deep; Gson is tuned for nesting is 30 levels deep or fewer
    JsonReader reader =
        newJsonReader(
            "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
    for (int i = 0; i < 40; i++) {
      reader.beginArray();
    }
    for (int i = 0; i < 40; i++) {
      reader.endArray();
    }
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testDeeplyNestedObjects() {
    // Build a JSON document structured like {"a":{"a":{"a":{"a":true}}}}, but 40 levels deep
    String array = "{\"a\":%s}";
    String json = "true";
    for (int i = 0; i < 40; i++) {
      //      json = String.format( array, json );
      json = array.replace("%s", json);
    }

    JsonReader reader = newJsonReader(json);
    for (int i = 0; i < 40; i++) {
      reader.beginObject();
      assertThat("a").isEqualTo(reader.nextName());
    }
    assertThat(reader.nextBoolean()).isTrue();
    for (int i = 0; i < 40; i++) {
      reader.endObject();
    }
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  // http://code.google.com/p/google-gson/issues/detail?id=409
  @Test
  public void testStringEndingInSlash() {
    JsonReader reader = newJsonReader("/");
    reader.setLenient(true);
    try {
      reader.peek();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testDocumentWithCommentEndingInSlash() {
    JsonReader reader = newJsonReader("/* foo *//");
    reader.setLenient(true);
    try {
      reader.peek();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testStringWithLeadingSlash() {
    JsonReader reader = newJsonReader("/x");
    reader.setLenient(true);
    try {
      reader.peek();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testUnterminatedObject() {
    JsonReader reader = newJsonReader("{\"a\":\"android\"x");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat("android").isEqualTo(reader.nextString());
    try {
      reader.peek();
      fail("failed");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testVeryLongQuotedString() {
    char[] stringChars = new char[1024 * 16];
    Arrays.fill(stringChars, 'x');
    String string = new String(stringChars);
    String json = "[\"" + string + "\"]";
    JsonReader reader = newJsonReader(json);
    reader.beginArray();
    assertThat(string).isEqualTo(reader.nextString());
    reader.endArray();
  }

  @Test
  public void testVeryLongUnquotedString() {
    char[] stringChars = new char[1024 * 16];
    Arrays.fill(stringChars, 'x');
    String string = new String(stringChars);
    String json = "[" + string + "]";
    JsonReader reader = newJsonReader(json);
    reader.setLenient(true);
    reader.beginArray();
    assertThat(string).isEqualTo(reader.nextString());
    reader.endArray();
  }

  @Test
  public void testVeryLongUnterminatedString() {
    char[] stringChars = new char[1024 * 16];
    Arrays.fill(stringChars, 'x');
    String string = new String(stringChars);
    String json = "[" + string;
    JsonReader reader = newJsonReader(json);
    reader.setLenient(true);
    reader.beginArray();
    assertThat(string).isEqualTo(reader.nextString());
    try {
      reader.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
      assertThat(expected.getMessage().startsWith("End of input at line")).isTrue();
    }
  }

  @Test
  public void testSkipVeryLongUnquotedString() {
    JsonReader reader = newJsonReader("[" + repeat('x', 8192) + "]");
    reader.setLenient(true);
    reader.beginArray();
    reader.skipValue();
    reader.endArray();
  }

  @Test
  public void testSkipTopLevelUnquotedString() {
    JsonReader reader = newJsonReader(repeat('x', 8192));
    reader.setLenient(true);
    reader.skipValue();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testSkipVeryLongQuotedString() {
    JsonReader reader = newJsonReader("[\"" + repeat('x', 8192) + "\"]");
    reader.beginArray();
    reader.skipValue();
    reader.endArray();
  }

  @Test
  public void testSkipTopLevelQuotedString() {
    JsonReader reader = newJsonReader("\"" + repeat('x', 8192) + "\"");
    reader.setLenient(true);
    reader.skipValue();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testStringAsNumberWithTruncatedExponent() {
    JsonReader reader = newJsonReader("[123e]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
  }

  @Test
  public void testStringAsNumberWithDigitAndNonDigitExponent() {
    JsonReader reader = newJsonReader("[123e4b]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
  }

  @Test
  public void testStringAsNumberWithNonDigitExponent() {
    JsonReader reader = newJsonReader("[123eb]");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(STRING).isEqualTo(reader.peek());
  }

  @Test
  public void testEmptyStringName() {
    JsonReader reader = newJsonReader("{\"\":true}");
    reader.setLenient(true);
    assertThat(BEGIN_OBJECT).isEqualTo(reader.peek());
    reader.beginObject();
    assertThat(NAME).isEqualTo(reader.peek());
    assertThat("").isEqualTo(reader.nextName());
    assertThat(JsonToken.BOOLEAN).isEqualTo(reader.peek());
    assertThat(reader.nextBoolean()).isTrue();
    assertThat(JsonToken.END_OBJECT).isEqualTo(reader.peek());
    reader.endObject();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  @Test
  public void testStrictExtraCommasInMaps() {
    JsonReader reader = newJsonReader("{\"a\":\"b\",}");
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat("b").isEqualTo(reader.nextString());
    try {
      reader.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  @Test
  public void testLenientExtraCommasInMaps() {
    JsonReader reader = newJsonReader("{\"a\":\"b\",}");
    reader.setLenient(true);
    reader.beginObject();
    assertThat("a").isEqualTo(reader.nextName());
    assertThat("b").isEqualTo(reader.nextString());
    try {
      reader.peek();
      fail("failed");
    } catch (JsonDeserializationException expected) {
    }
  }

  protected String repeat(char c, int count) {
    char[] array = new char[count];
    Arrays.fill(array, c);
    return new String(array);
  }

  @Test
  public void testMalformedDocuments() {
    assertDocument("{]", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{,", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{{", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{[", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{:", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\":}", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\"::", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\":,", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\"=}", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\"=>}", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument(
        "{\"name\"=>\"string\":", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\"=>\"string\"=", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\"=>\"string\"=>", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\"=>\"string\",", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument("{\"name\"=>\"string\",\"name\"", BEGIN_OBJECT, NAME, STRING, NAME);
    assertDocument("[}", BEGIN_ARRAY, JsonDeserializationException.class);
    assertDocument("[,]", BEGIN_ARRAY, NULL, NULL, END_ARRAY);
    assertDocument("{", BEGIN_OBJECT, JsonDeserializationException.class);
    assertDocument("{\"name\"", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{'name'", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{'name',", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("{name", BEGIN_OBJECT, NAME, JsonDeserializationException.class);
    assertDocument("[", BEGIN_ARRAY, JsonDeserializationException.class);
    assertDocument("[string", BEGIN_ARRAY, STRING, JsonDeserializationException.class);
    assertDocument("[\"string\"", BEGIN_ARRAY, STRING, JsonDeserializationException.class);
    assertDocument("['string'", BEGIN_ARRAY, STRING, JsonDeserializationException.class);
    assertDocument("[123", BEGIN_ARRAY, NUMBER, JsonDeserializationException.class);
    assertDocument("[123,", BEGIN_ARRAY, NUMBER, JsonDeserializationException.class);
    assertDocument("{\"name\":123", BEGIN_OBJECT, NAME, NUMBER, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":123,", BEGIN_OBJECT, NAME, NUMBER, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":\"string\"", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":\"string\",", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":'string'", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":'string',", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":false", BEGIN_OBJECT, NAME, BOOLEAN, JsonDeserializationException.class);
    assertDocument(
        "{\"name\":false,,", BEGIN_OBJECT, NAME, BOOLEAN, JsonDeserializationException.class);
  }

  /**
   * This test behave slightly differently in Gson 2.2 and earlier. It fails during peek rather than
   * during nextString().
   */
  @Test
  public void testUnterminatedStringFailure() {
    JsonReader reader = newJsonReader("[\"string");
    reader.setLenient(true);
    reader.beginArray();
    assertThat(JsonToken.STRING).isEqualTo(reader.peek());
    try {
      reader.nextString();
      fail("faield");
    } catch (MalformedJsonException expected) {
    }
  }

  @Test
  public void testNextValueNull() {
    JsonReader reader = newJsonReader("{\"value\":null}");
    reader.beginObject();
    assertThat("value").isEqualTo(reader.nextName());
    assertThat("null").isEqualTo(reader.nextValue());
    reader.endObject();
  }

  @Test
  public void testNextNumber() {
    JsonReader reader =
        newJsonReader(
            "["
                + "123,"
                + "12345678999,"
                + "54878.45,"
                + "\"literal\","
                + "\"-1545.78\","
                + "\"2147483647\","
                + "\"2147483648\","
                + Integer.MIN_VALUE
                + ","
                + (Long.valueOf("" + Integer.MIN_VALUE) - 1l)
                + ","
                + Integer.MAX_VALUE
                + ","
                + (Long.valueOf("" + Integer.MAX_VALUE) + 1l)
                + ","
                + Long.MIN_VALUE
                + ", "
                + "\""
                + new BigInteger(Long.MIN_VALUE + "").subtract(BigInteger.ONE)
                + "\","
                + Long.MAX_VALUE
                + ", "
                + "\""
                + new BigInteger(Long.MAX_VALUE + "").add(BigInteger.ONE)
                + "\""
                + "]");
    reader.beginArray();
    assertThat(new Integer(123)).isEqualTo(reader.nextNumber());
    assertThat(new Long(12345678999l)).isEqualTo(reader.nextNumber());
    assertThat(new Double(54878.45d)).isEqualTo(reader.nextNumber());
    try {
      reader.nextNumber();
      fail("failed");
    } catch (NumberFormatException e) {
    }
    assertThat("literal").isEqualTo(reader.nextString());
    assertThat(new Double(-1545.78d)).isEqualTo(reader.nextNumber());
    assertThat(new Integer(2147483647)).isEqualTo(reader.nextNumber());
    assertThat(new Long(2147483648l)).isEqualTo(reader.nextNumber());
    assertThat(Integer.MIN_VALUE).isEqualTo(reader.nextNumber());
    assertThat(Long.valueOf("" + Integer.MIN_VALUE) - 1l).isEqualTo(reader.nextNumber());
    assertThat(Integer.MAX_VALUE).isEqualTo(reader.nextNumber());
    assertThat(Long.valueOf("" + Integer.MAX_VALUE) + 1l).isEqualTo(reader.nextNumber());
    assertThat(Long.MIN_VALUE).isEqualTo(reader.nextNumber());
    assertThat(new BigInteger(Long.MIN_VALUE + "").subtract(BigInteger.ONE))
        .isEqualTo(reader.nextNumber());
    assertThat(Long.MAX_VALUE).isEqualTo(reader.nextNumber());
    assertThat(new BigInteger(Long.MAX_VALUE + "").add(BigInteger.ONE))
        .isEqualTo(reader.nextNumber());
    reader.endArray();
    assertThat(JsonToken.END_DOCUMENT).isEqualTo(reader.peek());
  }

  private void assertDocument(String document, Object... expectations) {
    JsonReader reader = newJsonReader(document);
    reader.setLenient(true);
    for (Object expectation : expectations) {
      if (expectation == BEGIN_OBJECT) {
        reader.beginObject();
      } else if (expectation == BEGIN_ARRAY) {
        reader.beginArray();
      } else if (expectation == END_OBJECT) {
        reader.endObject();
      } else if (expectation == END_ARRAY) {
        reader.endArray();
      } else if (expectation == NAME) {
        assertThat("name").isEqualTo(reader.nextName());
      } else if (expectation == BOOLEAN) {
        assertThat(reader.nextBoolean()).isFalse();
      } else if (expectation == STRING) {
        assertThat("string").isEqualTo(reader.nextString());
      } else if (expectation == NUMBER) {
        assertThat(123).isEqualTo(reader.nextInt());
      } else if (expectation == NULL) {
        reader.nextNull();
      } else if (expectation == JsonDeserializationException.class) {
        try {
          reader.peek();
          fail("failed");
        } catch (JsonDeserializationException expected) {
        }
      } else {
        throw new AssertionError();
      }
    }
  }

  /** Returns a reader that returns one character at a time. */
  private StringReader reader(final String s) {
    return new StringReader(s);
  }
}
