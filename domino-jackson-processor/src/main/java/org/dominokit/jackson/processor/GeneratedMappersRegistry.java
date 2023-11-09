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
package org.dominokit.jackson.processor;

import com.google.auto.common.MoreTypes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.lang.model.type.TypeMirror;

public class GeneratedMappersRegistry {

  public static final GeneratedMappersRegistry INSTANCE = new GeneratedMappersRegistry();
  private final Map<Category, Set<GeneratedTypeToken>> typeTokens = new HashMap<>();

  private GeneratedMappersRegistry() {}

  public void addTypeToken(Category category, TypeMirror typeMirror) {
    if (!typeTokens.containsKey(category)) {
      typeTokens.put(category, new HashSet<>());
    }
    typeTokens.get(category).add(new GeneratedTypeToken(typeMirror));
  }

  public boolean hasTypeToken(Category category, TypeMirror typeMirror) {
    if (typeTokens.containsKey(category)) {
      return typeTokens.get(category).contains(new GeneratedTypeToken(typeMirror));
    }
    return false;

    //    return false;
  }

  public enum Category {
    MAPPER,
    READER,
    WRITER,
    SERIALIZER,
    DESERIALIZER
  }

  public static class GeneratedTypeToken {
    TypeMirror typeMirror;

    public GeneratedTypeToken(TypeMirror typeMirror) {
      this.typeMirror = typeMirror;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      GeneratedTypeToken that = (GeneratedTypeToken) o;
      return MoreTypes.equivalence().equivalent(typeMirror, that.typeMirror);
    }

    @Override
    public int hashCode() {
      return typeMirror.hashCode();
    }
  }
}
