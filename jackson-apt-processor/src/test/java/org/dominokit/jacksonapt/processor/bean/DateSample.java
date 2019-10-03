package org.dominokit.jacksonapt.processor.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.utils.DatePatterns;

import java.util.Date;

@JSONMapper
public class DateSample {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePatterns.SHORT)
    private Date dateOnly;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePatterns.ISO8601)
    private Date dateAndTime;

    public Date getDateOnly() {
        return dateOnly;
    }

    public void setDateOnly(Date dateOnly) {
        this.dateOnly = dateOnly;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
