package org.dominokit.jacksonapt.stream;

/**
 * <p>Stack interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface Stack<T> {
    /**
     * <p>setAt.</p>
     *
     * @param index a int.
     * @param value a T object.
     */
    void setAt(int index, T value);

    /**
     * <p>getAt.</p>
     *
     * @param index a int.
     * @return a T object.
     */
    T getAt(int index);
}
