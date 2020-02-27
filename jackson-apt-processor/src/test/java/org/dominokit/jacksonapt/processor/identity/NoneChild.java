package org.dominokit.jacksonapt.processor.identity;

public class NoneChild {

    private int id;
    private NonGeneratorBean cycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NonGeneratorBean getCycle() {
        return cycle;
    }

    public void setCycle(NonGeneratorBean cycle) {
        this.cycle = cycle;
    }
}
