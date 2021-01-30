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
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of {@link ObjectIdResolver}
 *
 * @author Pascal Gélinas
 */
public class SimpleObjectIdResolver implements ObjectIdResolver {
  protected Map<IdKey, Object> _items;

  public SimpleObjectIdResolver() {}

  @Override
  public void bindItem(IdKey id, Object ob) {
    if (_items == null) {
      _items = new HashMap<IdKey, Object>();
    } else if (_items.containsKey(id)) {
      throw new IllegalStateException(
          "Already had POJO for id (" + id.key.getClass().getName() + ") [" + id + "]");
    }
    _items.put(id, ob);
  }

  @Override
  public Object resolveId(IdKey id) {
    return (_items == null) ? null : _items.get(id);
  }

  @Override
  public boolean canUseFor(ObjectIdResolver resolverType) {
    return resolverType.getClass() == getClass();
  }

  @Override
  public ObjectIdResolver newForDeserialization(Object context) {
    // 19-Dec-2014, tatu: Important: must re-create without existing mapping; otherwise bindings
    // leak
    //    (and worse, cause unnecessary memory retention)
    return new SimpleObjectIdResolver();
  }
}
