package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseBeanWithIgnore {

    private String propertyx;
    @JsonIgnore
    private String propertyy;

    public String getPropertyx() {
        return propertyx;
    }

    public void setPropertyx(String propertyx) {
        this.propertyx = propertyx;
    }

    public String getPropertyy() {
        return propertyy;
    }

    public void setPropertyy(String propertyy) {
        this.propertyy = propertyy;
    }
}
