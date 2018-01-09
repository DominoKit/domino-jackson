package com.progressoft.brix.domino.jacksonapt.stream;

public interface Stack<T> {
    void setAt(int index, T value);

    T getAt(int index);
}
