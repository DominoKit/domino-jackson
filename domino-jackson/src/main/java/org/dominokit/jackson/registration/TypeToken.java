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
package org.dominokit.jackson.registration;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * A "supertype" token capable of representing any Java type.
 *
 * <p>While the purpose of this class is primarily to be instantiated by gwt-jackson-apt itself and
 * then just used by the user code, it is a public API.<br>
 * <br>
 * Based on ideas from <a
 * href="http://gafter.blogspot.com/2006/12/super-type-tokens.html">http://gafter.blogspot.com/2006/12/super-type-tokens.html</a>,
 * as well as on the implementation of the same notion in various Java libraries (Guava's TypeToken,
 * Jackson's TypeReference, Guice's TypeLiteral, Gson's TypeToken, Apache Commons TypeLiteral - too
 * many to enumerate all of those here).
 *
 * <p>Unlike all those other implementations however, this {@link TypeToken} is designed to operate
 * in a GWT/J2CL environment, where Java reflection - and therefore, the {@link Type} class on which
 * all other implementations are based of - is not available.
 *
 * <p>How to use: For simple, non-parameterized types like {@link String}, {@link Integer} or
 * non-parameterized Java Beans:<br>
 *
 * <pre>TypeToken.of(String.class)</pre>
 *
 * <br>
 * For parameterized types, like List&lt;String&gt;, in GWT/J2CL environments:<br>
 *
 * <pre>new TypeToken&lt;List&lt;String&gt;&gt;(List.class, TypeToken.of(String)){}</pre>
 *
 * A more advanced example with a multiple-nesting of a parameterized type like
 * List&lt;Map&lt;Integer, String&gt;&gt;:<br>
 * In GWT/J2CL environments:<br>
 *
 * <pre>
 * new TypeToken&lt;List&lt;Map&lt;Integer, String&gt;&gt;(List.class, new TypeToken&lt;Map&lt;Integer, String&gt;&gt;(Map.class, TypeToken.of(Integer.class), TypeToken.of(String.class)) {}) {}
 * </pre>
 *
 * <br>
 * The syntax for GWT/J2CL is much more verbose and requires the captured type not only to be
 * written in the type parameter of the type token (<code>TypeToken&lt;...&gt;</code>) but to be
 * also explicitly enumerated as a pair of a raw class type reference (i.e. "<code>List.class</code>
 * ") plus a chain of nested type token instantiations describing all the instantiations of the type
 * parameters of the raw type for the concrete type we are trying to capture with the type token.
 * This verbosity is unavoidable, because GWT/J2CL is missing Java reflection, which in turn
 * prohibits the {@link TypeToken} instance from "introspecting" itself and figuring out the type
 * automatically.
 */
public class TypeToken<T> implements Comparable<TypeToken<T>> {
  private Class<? super T> rawType;
  private TypeToken<?>[] typeArguments;

  public static <T> TypeToken<T> of(Class<T> type) {
    return new TypeToken<T>(type, new TypeToken<?>[0]);
  }

  protected TypeToken(Class<? super T> rawType, TypeToken<?>... typeArguments) {
    if (rawType != null && rawType.isArray()) {
      // User provided the array directly as a raw class
      // Normalize to the standard type token representation of arrays, where the raw type is null,
      // and the array component type is in the type arguments
      if (typeArguments.length > 0)
        throw new IllegalArgumentException(
            "To create a type token for an array, either pass the non-generic array class instance as the raw type and keep the type argumetns empty, or pass null as raw type and provide a single type argument for the component type of the (possibly generic) array");

      typeArguments = new TypeToken<?>[] {TypeToken.of(rawType.getComponentType())};
      rawType = null;
    }

    this.rawType = rawType;
    this.typeArguments = typeArguments;
  }

  /**
   * Return the raw type represented by this {@link TypeToken} instance. E.g.:<br>
   * When called on {@code TypeToken<String>} it will return {@code String.class} <br>
   * When called on {@code TypeToken<List<String>>} it will return {@code List.class}<br>
   * <br>
   * For arrays, this method will return null.
   *
   * @return {@link Class}
   */
  public final Class<? super T> getRawType() {
    return rawType;
  }

  /**
   * Return the type tokens corresponding to the type arguments of the parameterized type
   * represented by this type token. If the type is not parameterized, an empty array is returned.
   * For example:<br>
   * When called on {@code TypeToken<String>} an empty array will be returned<br>
   * When called on {@code TypeToken<List<String>>} a single-element array with a type token {@code
   * TypeToken<String>}<br>
   * When called on {@code TypeToken<String[]>} a single-element array with a type token {@code
   * TypeToken<String>} will be returned as well
   *
   * @return {@link TypeToken[]}
   */
  public final TypeToken<?>[] getTypeArguments() {
    return typeArguments;
  }

  /**
   * The only reason we define this method (and require implementation of <code>Comparable</code>)
   * is to prevent constructing a reference without type information.
   *
   * @param other {@link TypeToken}
   * @return int
   */
  @Override
  public final int compareTo(TypeToken<T> other) {
    return 0;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rawType == null) ? 0 : rawType.hashCode());
    result = prime * result + Arrays.hashCode(typeArguments);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof TypeToken<?>)) return false;
    TypeToken<?> other = (TypeToken<?>) obj;
    if (rawType == null) {
      if (other.rawType != null) return false;
    } else if (!rawType.equals(other.rawType)) return false;
    if (!Arrays.equals(typeArguments, other.typeArguments)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "TypeToken<" + stringify() + ">";
  }

  public final String stringify() {
    StringBuilder buf = new StringBuilder();

    stringify(buf);

    return buf.toString();
  }

  private void stringify(StringBuilder buf) {
    if (getRawType() != null) {
      buf.append(getRawType().getName());

      if (typeArguments.length > 0) {
        buf.append('<');

        for (int i = 0; i < typeArguments.length; i++) {
          if (i > 0) buf.append(", ");

          typeArguments[i].stringify(buf);
        }

        buf.append('>');
      }
    } else {
      typeArguments[0].stringify(buf);

      buf.append("[]");
    }
  }
}
