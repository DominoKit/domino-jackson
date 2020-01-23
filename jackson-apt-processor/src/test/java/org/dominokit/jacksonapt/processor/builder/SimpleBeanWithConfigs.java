package org.dominokit.jacksonapt.processor.builder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JsonDeserialize(builder = SimpleBeanWithConfigsBuilder.class)
@JSONMapper
public class SimpleBeanWithConfigs {

    private String id;
    private String name;
    private String address;

    public SimpleBeanWithConfigs(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
