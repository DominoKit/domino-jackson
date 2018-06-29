package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.bean.MapLike;
import org.dominokit.jacksonapt.deser.map.key.DateKeyParser;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.Stack;

import java.util.Date;

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
