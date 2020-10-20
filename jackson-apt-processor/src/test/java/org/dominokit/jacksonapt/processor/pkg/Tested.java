package org.dominokit.jacksonapt.processor.pkg;

import java.util.List;

import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.processor.pkg.one.ExtensionElement;

@JSONMapper
public class Tested {

    private List<ExtensionElement> beans;

    private Import anImport;

    public List<ExtensionElement> getBeans() {
        return beans;
    }

    public void setBeans(List<ExtensionElement> beans) {
        this.beans = beans;
    }

    public Import getAnImport() {
        return anImport;
    }

    public void setAnImport(Import anImport) {
        this.anImport = anImport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tested)) {
            return false;
        }

        Tested tested = (Tested) o;

        if (getBeans() != null ? !getBeans().equals(tested.getBeans()) : tested.getBeans() != null) {
            return false;
        }
        return getAnImport() != null ? getAnImport().equals(tested.getAnImport()) : tested.getAnImport() == null;
    }

    @Override
    public int hashCode() {
        int result = getBeans() != null ? getBeans().hashCode() : 0;
        result = 31 * result + (getAnImport() != null ? getAnImport().hashCode() : 0);
        return result;
    }
}
