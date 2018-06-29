package org.dominokit.jacksonapt.stream.impl;

import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import org.dominokit.jacksonapt.stream.Stack;

/**
 * <p>JsIntegerStack class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
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
     * <p>get.</p>
     *
     * @param index a int.
     * @return a {@link elemental2.core.JsNumber} object.
     */
    public JsNumber get(int index) {
        JsNumber[] slice = stack.slice();
        return slice[index];
    }
}
