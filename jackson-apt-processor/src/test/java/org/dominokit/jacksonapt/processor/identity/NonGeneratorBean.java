package org.dominokit.jacksonapt.processor.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class)
public class NonGeneratorBean {

    private int id;
    private String name;
    private NoneChild noneChild;

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

    public NoneChild getNoneChild() {
        return noneChild;
    }

    public void setNoneChild(NoneChild noneChild) {
        this.noneChild = noneChild;
    }
}
