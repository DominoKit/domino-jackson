package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.bean.MapLike;
import org.dominokit.jacksonapt.deser.map.key.DateKeyParser;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.Stack;

import java.util.Date;

/**
 * <p>JacksonContext interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JacksonContext {

    /**
     * <p>dateFormat.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.DateFormat} object.
     */
    DateFormat dateFormat();
    /**
     * <p>integerStackFactory.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.IntegerStackFactory} object.
     */
    IntegerStackFactory integerStackFactory();
    /**
     * <p>mapLikeFactory.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.MapLikeFactory} object.
     */
    MapLikeFactory mapLikeFactory();
    /**
     * <p>stringifier.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.ValueStringifier} object.
     */
    ValueStringifier stringifier();
    /**
     * <p>stringArrayReader.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.StringArrayReader} object.
     */
    StringArrayReader stringArrayReader();
    /**
     * <p>shortArrayReader.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.ShortArrayReader} object.
     */
    ShortArrayReader shortArrayReader();
    /**
     * <p>integerArrayReader.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.IntegerArrayReader} object.
     */
    IntegerArrayReader integerArrayReader();
    /**
     * <p>doubleArrayReader.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext.DoubleArrayReader} object.
     */
    DoubleArrayReader doubleArrayReader();
    /**
     * <p>defaultSerializerParameters.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters defaultSerializerParameters();

    /**
     *<p>newSerializerParameters</p>
     * @return a new instance of {@link JsonSerializerParameters} object
     */
    JsonSerializerParameters newSerializerParameters();

    /**
     * <p>defaultDeserializerParameters.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters defaultDeserializerParameters();

    /**
     *<p>newDeserializerParameters</p>
     * @return a new instance of {@link JsonDeserializerParameters} object
     */
    JsonDeserializerParameters newDeserializerParameters();

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
