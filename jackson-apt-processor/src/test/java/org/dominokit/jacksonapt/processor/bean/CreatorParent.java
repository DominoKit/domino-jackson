package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.util.Objects;

@JSONMapper
public class CreatorParent {

    private int id;
    private String name;
    private CreatorChild child;

    @JsonCreator
    public CreatorParent(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("child") CreatorChild child) {
        this.id = id;
        this.name = name;
        this.child = child;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CreatorChild getChild() {
        return child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorParent creatorParent = (CreatorParent) o;
        return id == creatorParent.id &&
                Objects.equals(name, creatorParent.name) &&
                Objects.equals(child, creatorParent.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, child);
    }
}
