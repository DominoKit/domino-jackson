/*
 * Copyright 2017 Ahmad Bawaneh
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
package com.progressoft.brix.domino.gwtjackson.stream.impl;

import com.progressoft.brix.domino.gwtjackson.stream.BufferStack;
import elemental2.core.JsArray;

public class ElementalArrayBufferStack<T> implements BufferStack<T> {

    JsArray<T> stack = new JsArray<T>();

    @Override
    public T getAt(int index) {
        return stack.getAt(index);
    }

    @Override
    public void setAt(int index, T value) {
        stack.setAt(index, value);
    }
}
