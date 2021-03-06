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

import java.util.Queue;
import org.dominokit.jackson.JsonDeserializer;

/**
 * Base {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link java.util.Queue}.
 *
 * @param <Q> {@link java.util.Queue} type
 * @param <T> Type of the elements inside the {@link java.util.Queue}
 */
public abstract class BaseQueueJsonDeserializer<Q extends Queue<T>, T>
    extends BaseCollectionJsonDeserializer<Q, T> {

  /**
   * Constructor for BaseQueueJsonDeserializer.
   *
   * @param deserializer {@link org.dominokit.jackson.JsonDeserializer} used to map the objects
   *     inside the {@link java.util.Queue}.
   */
  public BaseQueueJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isNullValueAllowed() {
    return false;
  }
}
