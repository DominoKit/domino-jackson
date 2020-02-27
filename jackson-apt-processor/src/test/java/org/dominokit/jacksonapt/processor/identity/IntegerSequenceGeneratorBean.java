package org.dominokit.jacksonapt.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class IntegerSequenceGeneratorBean {

    private int id;
    private String name;
    private IntegerSequenceChild child;

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

    public IntegerSequenceChild getChild() {
        return child;
    }

    public void setChild(IntegerSequenceChild child) {
        this.child = child;
    }
}
