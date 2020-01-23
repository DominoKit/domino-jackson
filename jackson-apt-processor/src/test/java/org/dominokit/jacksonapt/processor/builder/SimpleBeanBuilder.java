package org.dominokit.jacksonapt.processor.builder;

public class SimpleBeanBuilder {
    private String id;
    private String name;

    public SimpleBeanBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SimpleBeanBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SimpleBean build() {
        return new SimpleBean(id, name);
    }
}
