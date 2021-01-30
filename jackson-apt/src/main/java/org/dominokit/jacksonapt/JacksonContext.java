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
package org.dominokit.jacksonapt;

import java.util.Date;
import org.dominokit.jacksonapt.deser.bean.MapLike;
import org.dominokit.jacksonapt.deser.map.key.DateKeyParser;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.Stack;

/**
 * JacksonContext interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JacksonContext {

  /**
   * dateFormat.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.DateFormat} object.
   */
  DateFormat dateFormat();
  /**
   * integerStackFactory.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.IntegerStackFactory} object.
   */
  IntegerStackFactory integerStackFactory();
  /**
   * mapLikeFactory.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.MapLikeFactory} object.
   */
  MapLikeFactory mapLikeFactory();
  /**
   * stringifier.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.ValueStringifier} object.
   */
  ValueStringifier stringifier();
  /**
   * stringArrayReader.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.StringArrayReader} object.
   */
  StringArrayReader stringArrayReader();
  /**
   * shortArrayReader.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.ShortArrayReader} object.
   */
  ShortArrayReader shortArrayReader();
  /**
   * integerArrayReader.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.IntegerArrayReader} object.
   */
  IntegerArrayReader integerArrayReader();
  /**
   * doubleArrayReader.
   *
   * @return a {@link org.dominokit.jacksonapt.JacksonContext.DoubleArrayReader} object.
   */
  DoubleArrayReader doubleArrayReader();
  /**
   * defaultSerializerParameters.
   *
   * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
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
   * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   */
  JsonDeserializerParameters defaultDeserializerParameters();

  /**
   * newDeserializerParameters
   *
   * @return a new instance of {@link JsonDeserializerParameters} object
   */
  JsonDeserializerParameters newDeserializerParameters();

  interface DateFormat {
    String format(Date date);

    String format(JsonSerializerParameters params, Date date);

    Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date);

    <D extends Date> DateKeyParser<D> makeDateKeyParser();
  }

  interface IntegerStackFactory {
    Stack<Integer> make();
  }

  interface ValueStringifier {
    String stringify(String value);
  }

  interface MapLikeFactory {
    <T> MapLike<T> make();
  }

  interface StringArrayReader {
    String[] readArray(JsonReader reader);
  }

  interface ShortArrayReader {
    short[] readArray(JsonReader reader);
  }

  interface IntegerArrayReader {
    int[] readArray(JsonReader reader);
  }

  interface DoubleArrayReader {
    double[] readArray(JsonReader reader);
  }
}
