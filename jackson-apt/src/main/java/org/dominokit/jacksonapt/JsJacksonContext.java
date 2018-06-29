package org.dominokit.jacksonapt;

import elemental2.core.Global;
import org.dominokit.jacksonapt.deser.array.cast.JsDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.JsMapLike;
import org.dominokit.jacksonapt.stream.impl.JsIntegerStack;
import org.dominokit.jacksonapt.utils.JsDateFormat;

public class JsJacksonContext implements JacksonContext{
    @Override
    public DateFormat dateFormat() {
        return new JsDateFormat();
    }

    @Override
    public IntegerStackFactory integerStackFactory() {
        return JsIntegerStack::new;
    }

    @Override
    public MapLikeFactory mapLikeFactory() {
        return JsMapLike::new;
    }

    @Override
    public ValueStringifier stringifier() {
        return value -> Global.JSON.stringify(value);
    }

    @Override
    public StringArrayReader stringArrayReader() {
        return new JsStringArrayReader();
    }

    @Override
    public ShortArrayReader shortArrayReader() {
        return new JsShortArrayReader();
    }

    @Override
    public IntegerArrayReader integerArrayReader() {
        return new JsIntegerArrayReader();
    }

    @Override
    public DoubleArrayReader doubleArrayReader() {
        return new JsDoubleArrayReader();
    }

    @Override
    public JsonSerializerParameters defaultSerializerParameters() {
        return GwtJacksonJsonSerializerParameters.DEFAULT;
    }

    @Override
    public JsonDeserializerParameters defaultDeserializerParameters() {
        return GwtJacksonJsonDeserializerParameters.DEFAULT;
    }
}
