package org.dominokit.jacksonapt.processor.identity;

public class StringIdChild {

    private int id;
    private StringIdGeneratorBean cycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringIdGeneratorBean getCycle() {
        return cycle;
    }

    public void setCycle(StringIdGeneratorBean cycle) {
        this.cycle = cycle;
    }
}
