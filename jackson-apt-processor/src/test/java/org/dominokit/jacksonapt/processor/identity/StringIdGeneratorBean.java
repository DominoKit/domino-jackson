package org.dominokit.jacksonapt.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class StringIdGeneratorBean {

    private int id;
    private String name;
    private StringIdChild child;

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

    public StringIdChild getChild() {
        return child;
    }

    public void setChild(StringIdChild child) {
        this.child = child;
    }
}
