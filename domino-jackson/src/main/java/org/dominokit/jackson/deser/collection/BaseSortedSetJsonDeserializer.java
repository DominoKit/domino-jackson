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

import java.util.SortedSet;
import org.dominokit.jackson.JsonDeserializer;

/**
 * Base {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.util.SortedSet}.
 *
 * @param <S> {@link java.util.SortedSet} type
 * @param <T> Type of the elements inside the {@link java.util.SortedSet}
 */
public abstract class BaseSortedSetJsonDeserializer<S extends SortedSet<T>, T>
    extends BaseSetJsonDeserializer<S, T> {

  /**
   * Constructor for BaseSortedSetJsonDeserializer.
   *
   * @param deserializer {@link org.dominokit.jackson.JsonDeserializer} used to map the objects
   *     inside the {@link java.util.SortedSet}.
   */
  public BaseSortedSetJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isNullValueAllowed() {
    return false;
  }
}
