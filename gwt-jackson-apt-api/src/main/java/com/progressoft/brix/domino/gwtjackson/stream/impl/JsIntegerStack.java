package com.progressoft.brix.domino.gwtjackson.stream.impl;

import com.progressoft.brix.domino.gwtjackson.stream.Stack;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;

public class JsIntegerStack implements Stack<Integer> {

    private JsArray<JsNumber> stack = new JsArray<>();

    @Override
    public Integer getAt(int index) {
        return new Double(get(index).valueOf()).intValue();
    }

    @Override
    public void setAt(int index, Integer value) {
        stack.setAt(index, new JsNumber(value));
    }

    public JsNumber get(int index) {
        JsNumber[] slice = stack.slice();
        return slice[index];
    }
}
