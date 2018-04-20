package org.dominokit.jacksonapt.stream.impl;

import org.dominokit.jacksonapt.stream.Stack;

public class DefaultIntegerStack implements Stack<Integer> {

    private java.util.Stack<Integer> stack = new java.util.Stack<>();

    @Override
    public Integer getAt(int index) {
        return stack.get(index);
    }

    @Override
    public void setAt(int index, Integer value) {
        if (stack.empty() || index >= stack.size())
            stack.push(value);
        else
            stack.set(index, value);
    }
}
