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
package org.dominokit.jacksonapt.stream.impl;

import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.stream.Stack;

/**
 * DefaultIntegerStack class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@GwtIncompatible
public class DefaultIntegerStack implements Stack<Integer> {

  private java.util.Stack<Integer> stack = new java.util.Stack<>();

  /** {@inheritDoc} */
  @Override
  public Integer getAt(int index) {
    return stack.get(index);
  }

  /** {@inheritDoc} */
  @Override
  public void setAt(int index, Integer value) {
    if (stack.empty() || index >= stack.size()) stack.push(value);
    else stack.set(index, value);
  }
}
