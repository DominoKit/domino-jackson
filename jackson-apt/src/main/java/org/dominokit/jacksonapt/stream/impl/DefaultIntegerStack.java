package org.dominokit.jacksonapt.stream.impl;

import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.stream.Stack;

/**
 * <p>DefaultIntegerStack class.</p>
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
        if (stack.empty() || index >= stack.size())
            stack.push(value);
        else
            stack.set(index, value);
    }
}
