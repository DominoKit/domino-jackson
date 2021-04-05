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
package org.dominokit.jackson.stream;

/** Stack interface. */
public interface Stack<T> {

  /** @return remove the top of the stack and return the value */
  T pop();

  /** @return the top of the stack without removing it */
  T peek();

  /** @return int size of the stack */
  int size();

  /** Removes all elements of the stack */
  void clear();

  /**
   * add the value to the top of the stack
   *
   * @param value to be placed at the top of the stack
   */
  void push(T value);

  /** @param newTop the value to replace the top of the stack, the stack size remain the same */
  void replaceTop(T newTop);

  /**
   * @param value the value to be inserted the start of the stack, this means it will be popped
   *     last.
   */
  void insertFirst(T value);
}
