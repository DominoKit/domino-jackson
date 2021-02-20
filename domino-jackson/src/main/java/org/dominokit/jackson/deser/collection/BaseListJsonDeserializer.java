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

import java.util.List;
import org.dominokit.jackson.JsonDeserializer;

/**
 * Base {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link java.util.List}.
 *
 * @param <L> {@link java.util.List} type
 * @param <T> Type of the elements inside the {@link java.util.List}
 */
public abstract class BaseListJsonDeserializer<L extends List<T>, T>
    extends BaseCollectionJsonDeserializer<L, T> {

  /**
   * Constructor for BaseListJsonDeserializer.
   *
   * @param deserializer {@link org.dominokit.jackson.JsonDeserializer} used to map the objects
   *     inside the {@link java.util.List}.
   */
  public BaseListJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }
}
