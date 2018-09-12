package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.util.Objects;

@JSONMapper
public class ChildObject extends ParentObject{

    public static final ChildObject_MapperImpl MAPPER = new ChildObject_MapperImpl();

    private String address;

    public ChildObject() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildObject)) return false;
        if (!super.equals(o)) return false;
        ChildObject that = (ChildObject) o;
        return Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getAddress());
    }
}
