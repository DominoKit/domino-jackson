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

package org.dominokit.jacksonapt.deser.collection;

import java.util.HashSet;
import org.dominokit.jacksonapt.JsonDeserializer;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for {@link
 * java.util.HashSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.HashSet}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class HashSetJsonDeserializer<T> extends BaseSetJsonDeserializer<HashSet<T>, T> {

  /**
   * newInstance
   *
   * @param deserializer {@link org.dominokit.jacksonapt.JsonDeserializer} used to deserialize the
   *     objects inside the {@link java.util.HashSet}.
   * @param <T> Type of the elements inside the {@link java.util.HashSet}
   * @return a new instance of {@link
   *     org.dominokit.jacksonapt.deser.collection.HashSetJsonDeserializer}
   */
  public static <T> HashSetJsonDeserializer<T> newInstance(JsonDeserializer<T> deserializer) {
    return new HashSetJsonDeserializer<T>(deserializer);
  }

  /**
   * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link
   *     HashSet}.
   */
  private HashSetJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected HashSet<T> newCollection() {
    return new HashSet<T>();
  }
}
