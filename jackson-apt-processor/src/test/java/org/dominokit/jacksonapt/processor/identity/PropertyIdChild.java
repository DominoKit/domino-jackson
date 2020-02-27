package org.dominokit.jacksonapt.processor.identity;

public class PropertyIdChild {

    private int id;
    private PropertyIdGeneratorBean cycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PropertyIdGeneratorBean getCycle() {
        return cycle;
    }

    public void setCycle(PropertyIdGeneratorBean cycle) {
        this.cycle = cycle;
    }
}
