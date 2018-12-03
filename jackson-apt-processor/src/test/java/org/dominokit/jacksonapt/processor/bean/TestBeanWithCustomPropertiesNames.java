package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class TestBeanWithCustomPropertiesNames {

    @JsonIgnore
    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("person-name")
    private String name;
    @JsonProperty("location")
    private String address;

    public TestBeanWithCustomPropertiesNames() {
    }

    public TestBeanWithCustomPropertiesNames(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
