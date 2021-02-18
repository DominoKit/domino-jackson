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
package org.dominokit.jacksonapt.deser.bean;

import java.util.HashMap;
import java.util.Map;
import org.dominokit.jacksonapt.GwtIncompatible;

/**
 * A wrapper for a map implementation that works in JVM, this implementation will be stripped out
 * during GWT/J2CL compilation. for the browser implementation please check {@link JsMapLike}
 */
@GwtIncompatible
public class DefaultMapLike<T> implements MapLike<T> {

  private Map<String, T> map = new HashMap<>();

  /** {@inheritDoc} */
  @Override
  public T get(String key) {
    return map.get(key);
  }

  /** {@inheritDoc} */
  @Override
  public void put(String key, T value) {
    map.put(key, value);
  }
}
