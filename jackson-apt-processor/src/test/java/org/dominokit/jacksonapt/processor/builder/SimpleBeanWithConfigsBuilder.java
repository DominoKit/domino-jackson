package org.dominokit.jacksonapt.processor.builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder(buildMethodName = "create")
public class SimpleBeanWithConfigsBuilder {
    private String id;
    private String name;
    @JsonIgnore
    private String address;

    public SimpleBeanWithConfigsBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SimpleBeanWithConfigsBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SimpleBeanWithConfigsBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public SimpleBeanWithConfigs create() {
        return new SimpleBeanWithConfigs(id, name, address);
    }
}
