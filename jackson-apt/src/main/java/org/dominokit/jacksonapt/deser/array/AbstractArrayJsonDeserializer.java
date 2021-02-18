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

package org.dominokit.jacksonapt.deser.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

/** Base implementation of {@link org.dominokit.jacksonapt.JsonDeserializer} for array. */
public abstract class AbstractArrayJsonDeserializer<T> extends JsonDeserializer<T> {

  /** {@inheritDoc} */
  @Override
  public T doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    if (JsonToken.BEGIN_ARRAY == reader.peek()) {
      return doDeserializeArray(reader, ctx, params);
    } else {
      return doDeserializeNonArray(reader, ctx, params);
    }
  }

  /**
   * doDeserializeArray
   *
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   * @return a T object.
   */
  protected abstract T doDeserializeArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params);

  /**
   * doDeserializeNonArray
   *
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   * @return a T object.
   */
  protected T doDeserializeNonArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    if (ctx.isAcceptSingleValueAsArray()) {
      return doDeserializeSingleArray(reader, ctx, params);
    } else {
      throw ctx.traceError(
          "Cannot deserialize an array out of " + reader.peek() + " token", reader);
    }
  }

  /**
   * doDeserializeSingleArray
   *
   * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
   * @param ctx a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
   * @param params a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
   * @return a T object.
   */
  protected abstract T doDeserializeSingleArray(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params);

  /**
   * Deserializes the array into a {@link java.util.List}. We need the length of the array before
   * creating it.
   *
   * @param reader reader
   * @param ctx context of the deserialization process
   * @param deserializer deserializer for element inside the array
   * @param params Parameters for the deserializer
   * @param <C> type of the element inside the array
   * @return a list containing all the elements of the array
   */
  protected <C> List<C> deserializeIntoList(
      JsonReader reader,
      JsonDeserializationContext ctx,
      JsonDeserializer<C> deserializer,
      JsonDeserializerParameters params) {
    List<C> list;

    reader.beginArray();
    JsonToken token = reader.peek();

    if (JsonToken.END_ARRAY == token) {

      // empty array, no need to create a list
      list = Collections.emptyList();

    } else {

      list = new ArrayList<C>();

      while (JsonToken.END_ARRAY != token) {
        list.add(deserializer.deserialize(reader, ctx, params));
        token = reader.peek();
      }
    }

    reader.endArray();
    return list;
  }
}
