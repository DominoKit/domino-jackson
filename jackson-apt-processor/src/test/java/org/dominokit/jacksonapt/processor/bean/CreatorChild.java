package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.util.Objects;

@JSONMapper
public class CreatorChild {

    private String name;

    @JsonCreator
    public CreatorChild(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorChild creatorChild = (CreatorChild) o;
        return Objects.equals(name, creatorChild.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
