package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class TestBeanWithIgnore extends BaseBeanWithIgnore{

    @JsonIgnore
    private Integer id;
    @JsonIgnore
    @JsonProperty("gender")
    private String personGender;
    private String name;
    private String address;

    public TestBeanWithIgnore() {
    }

    public TestBeanWithIgnore(Integer id, String name, String address) {
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

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
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
