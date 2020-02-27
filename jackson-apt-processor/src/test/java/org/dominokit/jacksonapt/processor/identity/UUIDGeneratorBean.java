package org.dominokit.jacksonapt.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class UUIDGeneratorBean {

    private int id;
    private String name;
    private UUIDChild child;

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

    public UUIDChild getChild() {
        return child;
    }

    public void setChild(UUIDChild child) {
        this.child = child;
    }
}
