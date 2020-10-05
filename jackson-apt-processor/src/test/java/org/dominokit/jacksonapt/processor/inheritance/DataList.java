package org.dominokit.jacksonapt.processor.inheritance;

import org.dominokit.jacksonapt.annotation.JSONMapper;

import java.util.List;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 9/25/20
 */
@JSONMapper
public class DataList {

    private List<Data<?>> ioSpecification;

    public List<Data<?>> getIoSpecification() {
        return ioSpecification;
    }

    public void setIoSpecification(List<Data<?>> ioSpecification) {
        this.ioSpecification = ioSpecification;
    }
}
