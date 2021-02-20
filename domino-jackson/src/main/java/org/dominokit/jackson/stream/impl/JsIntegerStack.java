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
package org.dominokit.jackson.stream.impl;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import org.dominokit.jackson.stream.Stack;

/**
 * A wrapper for an Integer Stack implementation that works in the browser. for the JVM
 * implementation please check {@link DefaultIntegerStack}
 */
public class JsIntegerStack implements Stack<Integer> {

  private JsArray<JsNumber> stack = new JsArray<>();

  /** {@inheritDoc} */
  @Override
  public Integer getAt(int index) {
    return new Double(get(index).valueOf()).intValue();
  }

  /** {@inheritDoc} */
  @Override
  public void setAt(int index, Integer value) {
    stack.setAt(index, new JsNumber(value));
  }

  /**
   * get.
   *
   * @param index a int.
   * @return a {@link elemental2.core.JsNumber} object.
   */
  public JsNumber get(int index) {
    JsArray<JsNumber> slice = stack.slice();
    return slice.asArray(new JsNumber[slice.length])[index];
  }
}
