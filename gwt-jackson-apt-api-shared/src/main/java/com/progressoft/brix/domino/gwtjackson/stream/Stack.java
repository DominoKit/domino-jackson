package com.progressoft.brix.domino.gwtjackson.stream;

public interface Stack<T> {
    void setAt(int index, T value);

    T getAt(int index);
}
