package org.dominokit.jacksonapt.processor.identity;

public class UUIDChild {

    private int id;
    private UUIDGeneratorBean cycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUIDGeneratorBean getCycle() {
        return cycle;
    }

    public void setCycle(UUIDGeneratorBean cycle) {
        this.cycle = cycle;
    }
}
