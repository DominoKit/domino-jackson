package org.dominokit.jacksonapt.processor;

import java.util.Objects;

public abstract class ParentObject {

    private int id;
    private String name;

    public ParentObject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentObject)) return false;
        ParentObject that = (ParentObject) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }
}
