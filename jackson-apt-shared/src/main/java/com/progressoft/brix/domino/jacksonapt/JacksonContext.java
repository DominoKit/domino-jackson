package com.progressoft.brix.domino.jacksonapt;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.progressoft.brix.domino.jacksonapt.deser.bean.MapLike;
import com.progressoft.brix.domino.jacksonapt.deser.map.key.DateKeyParser;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;
import com.progressoft.brix.domino.jacksonapt.stream.Stack;

import java.util.Date;
import java.util.ServiceLoader;

public interface JacksonContext {

    DateFormat dateFormat();
    IntegerStackFactory integerStackFactory();
    MapLikeFactory mapLikeFactory();
    ValueStringifier stringifier();
    StringArrayReader stringArrayReader();
    ShortArrayReader shortArrayReader();
    IntegerArrayReader integerArrayReader();
    DoubleArrayReader doubleArrayReader();
    JsonSerializerParameters defaultSerializerParameters();
    JsonDeserializerParameters defaultDeserializerParameters();

    interface DateFormat{
        String format(Date date);
        String format(JsonSerializerParameters params, Date date);
        Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date);
        <D extends Date> DateKeyParser<D> makeDateKeyParser();
    }

    interface IntegerStackFactory{
        Stack<Integer> make();
    }

    interface ValueStringifier{
        String stringify(String value);
    }

    interface MapLikeFactory{
        <T> MapLike<T> make();
    }

    interface StringArrayReader {
        String[] readArray(JsonReader reader);
    }

    interface ShortArrayReader {
        short[] readArray(JsonReader reader);
    }

    interface IntegerArrayReader {
        int[] readArray(JsonReader reader);
    }

    interface DoubleArrayReader {
        double[] readArray(JsonReader reader);
    }

}
