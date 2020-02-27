package org.dominokit.jacksonapt.processor.identity;

public class IntegerSequenceChild {

    private int id;
    private IntegerSequenceGeneratorBean cycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IntegerSequenceGeneratorBean getCycle() {
        return cycle;
    }

    public void setCycle(IntegerSequenceGeneratorBean cycle) {
        this.cycle = cycle;
    }
}
