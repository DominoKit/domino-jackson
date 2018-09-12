package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class ChildOfChild extends ChildObject{

    public static final ChildOfChild_MapperImpl MAPPER = new ChildOfChild_MapperImpl();

    private int age;

    public ChildOfChild() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
