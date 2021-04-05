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

import java.util.ArrayDeque;
import java.util.Deque;
import org.dominokit.jackson.GwtIncompatible;
import org.dominokit.jackson.stream.Stack;

/**
 * A wrapper for an Integer Stack implementation that works in JVM, this implementation will be
 * stripped out during GWT/J2CL compilation. for the browser implementation please check {@link
 * JsIntegerStack}
 */
@GwtIncompatible
public class DefaultIntegerStack implements Stack<Integer> {

  private Deque<Integer> stack = new ArrayDeque<>();

  /** {@inheritDoc} */
  @Override
  public Integer pop() {
    return stack.pop();
  }

  /** {@inheritDoc} */
  @Override
  public Integer peek() {
    return stack.peek();
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return stack.size();
  }

  /** {@inheritDoc} */
  @Override
  public void clear() {
    stack.clear();
  }

  /** {@inheritDoc} */
  @Override
  public void push(Integer value) {
    stack.push(value);
  }

  /** {@inheritDoc} */
  @Override
  public void replaceTop(Integer newTop) {
    stack.pop();
    stack.push(newTop);
  }

  /** {@inheritDoc} */
  @Override
  public void insertFirst(Integer value) {
    stack.pollLast();
    stack.offerLast(value);
  }
}
