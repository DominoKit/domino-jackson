package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.processor.bean.DateSample;
import org.dominokit.jacksonapt.processor.bean.DateSample_MapperImpl;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonFormatTest {

    @Test
    public void dateJsonFormatTest() {
        DateSample dateSample = new DateSample();
        dateSample.setDateOnly(getUTCDate(2019,9,18,0,0,0,0));
        dateSample.setDateAndTime(getUTCDate(2019,9,18,9,40,10,50));

        String result = DateSample_MapperImpl.INSTANCE.write(dateSample);
        assertEquals("{\"dateOnly\":\"2019-09-18\",\"dateAndTime\":\"2019-09-18T09:40:10.050+0000\"}", result);

        String json = "{\"dateOnly\":\"2018-07-10\",\"dateAndTime\":\"2018-07-10T10:30:05.010+0000\"}";

        DateSample resultDateSample = DateSample_MapperImpl.INSTANCE.read(json);
        assertEquals(getUTCDate(2018, 07, 10, 0,0,0,0), resultDateSample.getDateOnly());
        assertEquals(getUTCDate(2018, 07, 10, 10,30,5,10), resultDateSample.getDateAndTime());
    }

    public static Date getUTCDate(int year, int month, int day, int hour, int minute, int second, int milli) {
        return new Date(getUTCTime(year, month, day, hour, minute, second, milli));
    }

    @SuppressWarnings("deprecation")
    public static long getUTCTime(int year, int month, int day, int hour, int minute, int second, int milli) {
        return Date.UTC(year - 1900, month - 1, day, hour, minute, second) + milli;
    }
}
