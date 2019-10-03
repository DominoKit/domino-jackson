package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.array.cast.JsDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.JsMapLike;
import org.dominokit.jacksonapt.stream.impl.JsIntegerStack;
import org.dominokit.jacksonapt.utils.JsDateFormat;

/**
 * <p>JsJacksonContext class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsJacksonContext implements JacksonContext{
    /** {@inheritDoc} */
    @Override
    public DateFormat dateFormat() {
        return new JsDateFormat();
    }

    /** {@inheritDoc} */
    @Override
    public IntegerStackFactory integerStackFactory() {
        return JsIntegerStack::new;
    }

    /** {@inheritDoc} */
    @Override
    public MapLikeFactory mapLikeFactory() {
        return JsMapLike::new;
    }

    /** {@inheritDoc} */
    @Override
    public ValueStringifier stringifier() {
        return JSON::stringify;
    }

    /** {@inheritDoc} */
    @Override
    public StringArrayReader stringArrayReader() {
        return new JsStringArrayReader();
    }

    /** {@inheritDoc} */
    @Override
    public ShortArrayReader shortArrayReader() {
        return new JsShortArrayReader();
    }

    /** {@inheritDoc} */
    @Override
    public IntegerArrayReader integerArrayReader() {
        return new JsIntegerArrayReader();
    }

    /** {@inheritDoc} */
    @Override
    public DoubleArrayReader doubleArrayReader() {
        return new JsDoubleArrayReader();
    }

    /** {@inheritDoc} */
    @Override
    public JsonSerializerParameters defaultSerializerParameters() {
        return GwtJacksonJsonSerializerParameters.DEFAULT;
    }

    /** {@inheritDoc} */
    @Override
    public JsonDeserializerParameters defaultDeserializerParameters() {
        return GwtJacksonJsonDeserializerParameters.DEFAULT;
    }

    @Override
    public JsonSerializerParameters newSerializerParameters() {
        return new GwtJacksonJsonSerializerParameters();
    }

    @Override
    public JsonDeserializerParameters newDeserializerParameters() {
        return new GwtJacksonJsonDeserializerParameters();
    }
}
