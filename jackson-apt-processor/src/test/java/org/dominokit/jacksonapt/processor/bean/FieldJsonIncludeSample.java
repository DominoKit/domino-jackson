package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class FieldJsonIncludeSample {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
