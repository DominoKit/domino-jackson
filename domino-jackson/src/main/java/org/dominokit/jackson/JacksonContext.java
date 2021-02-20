/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson;

import java.util.Date;
import org.dominokit.jackson.deser.bean.MapLike;
import org.dominokit.jackson.deser.map.key.DateKeyParser;
import org.dominokit.jackson.stream.JsonReader;
import org.dominokit.jackson.stream.Stack;

/**
 * A context that define which implementation to be used based on the running environment - JVM vs
 * Browser -
 */
public interface JacksonContext {

  /**
   * dateFormat.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.DateFormat} object.
   */
  DateFormat dateFormat();
  /**
   * integerStackFactory.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.IntegerStackFactory} object.
   */
  IntegerStackFactory integerStackFactory();
  /**
   * mapLikeFactory.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.MapLikeFactory} object.
   */
  MapLikeFactory mapLikeFactory();
  /**
   * stringifier.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.ValueStringifier} object.
   */
  ValueStringifier stringifier();
  /**
   * stringArrayReader.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.StringArrayReader} object.
   */
  StringArrayReader stringArrayReader();
  /**
   * shortArrayReader.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.ShortArrayReader} object.
   */
  ShortArrayReader shortArrayReader();
  /**
   * integerArrayReader.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.IntegerArrayReader} object.
   */
  IntegerArrayReader integerArrayReader();
  /**
   * doubleArrayReader.
   *
   * @return a {@link org.dominokit.jackson.JacksonContext.DoubleArrayReader} object.
   */
  DoubleArrayReader doubleArrayReader();
  /**
   * defaultSerializerParameters.
   *
   * @return a {@link org.dominokit.jackson.JsonSerializerParameters} object.
   */
  JsonSerializerParameters defaultSerializerParameters();

  /**
   * newSerializerParameters
   *
   * @return a new instance of {@link JsonSerializerParameters} object
   */
  JsonSerializerParameters newSerializerParameters();

  /**
   * defaultDeserializerParameters.
   *
   * @return a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters defaultDeserializerParameters();

  /**
   * newDeserializerParameters
   *
   * @return a new instance of {@link JsonDeserializerParameters} object
   */
  JsonDeserializerParameters newDeserializerParameters();

  /** An interface to wrap different implementations of DateFormat e.g - JVM bs Browser - */
  interface DateFormat {
    String format(Date date);

    String format(JsonSerializerParameters params, Date date);

    Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date);

    <D extends Date> DateKeyParser<D> makeDateKeyParser();
  }

  /** An interface to wrap different implementations of IntegerStack e.g - JVM bs Browser - */
  interface IntegerStackFactory {
    Stack<Integer> make();
  }

  /**
   * An interface to wrap different implementations of ValueStringifier to stringify a json value
   * e.g - JVM bs Browser -
   */
  interface ValueStringifier {
    String stringify(String value);
  }

  /** An interface to wrap different implementations of Map e.g - JVM bs Browser - */
  interface MapLikeFactory {
    <T> MapLike<T> make();
  }

  /** An interface to wrap different implementations of StringArrayReader e.g - JVM bs Browser - */
  interface StringArrayReader {
    String[] readArray(JsonReader reader);
  }

  /** An interface to wrap different implementations of ShortArrayReader e.g - JVM bs Browser - */
  interface ShortArrayReader {
    short[] readArray(JsonReader reader);
  }

  /** An interface to wrap different implementations of IntegerArrayReader e.g - JVM bs Browser - */
  interface IntegerArrayReader {
    int[] readArray(JsonReader reader);
  }

  /** An interface to wrap different implementations of DoubleArrayReader e.g - JVM bs Browser - */
  interface DoubleArrayReader {
    double[] readArray(JsonReader reader);
  }
}
