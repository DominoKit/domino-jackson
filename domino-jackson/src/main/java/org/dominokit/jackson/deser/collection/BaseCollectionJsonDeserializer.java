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

package org.dominokit.jackson.deser.collection;

import java.util.Collection;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.stream.JsonReader;
import org.dominokit.jackson.stream.JsonToken;

/**
 * Base {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.util.Collection}.
 *
 * @param <C> {@link java.util.Collection} type
 * @param <T> Type of the elements inside the {@link java.util.Collection}
 */
public abstract class BaseCollectionJsonDeserializer<C extends Collection<T>, T>
    extends BaseIterableJsonDeserializer<C, T> {

  /**
   * Constructor for BaseCollectionJsonDeserializer.
   *
   * @param deserializer {@link org.dominokit.jackson.JsonDeserializer} used to map the objects
   *     inside the {@link java.util.Collection}.
   */
  public BaseCollectionJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  public C doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    if (JsonToken.BEGIN_ARRAY == reader.peek()) {

      C result = newCollection();

      reader.beginArray();
      while (JsonToken.END_ARRAY != reader.peek()) {
        T element = deserializer.deserialize(reader, ctx, params);
        if (isNullValueAllowed() || null != element) {
          result.add(element);
        }
      }
      reader.endArray();

      return result;

    } else if (ctx.isAcceptSingleValueAsArray()) {

      C result = newCollection();
      result.add(deserializer.deserialize(reader, ctx, params));
      return result;

    } else {
      throw ctx.traceError(
          "Cannot deserialize a java.util.Collection out of " + reader.peek() + " token", reader);
    }
  }

  /**
   * Instantiates a new collection for deserialization process.
   *
   * @return the new collection
   */
  protected abstract C newCollection();

  /**
   * isNullValueAllowed
   *
   * @return true if the collection accepts null value
   */
  protected boolean isNullValueAllowed() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public void setBackReference(
      String referenceName, Object reference, C value, JsonDeserializationContext ctx) {
    if (null != value && !value.isEmpty()) {
      for (T val : value) {
        deserializer.setBackReference(referenceName, reference, val, ctx);
      }
    }
  }
}
