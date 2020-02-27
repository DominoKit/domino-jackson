package org.dominokit.jacksonapt.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PropertyIdGeneratorBean {

    private int id;
    private String name;
    private PropertyIdChild child;

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

    public PropertyIdChild getChild() {
        return child;
    }

    public void setChild(PropertyIdChild child) {
        this.child = child;
    }
}
