package com.progressoft.brix.domino.jacksonapt;

import com.progressoft.brix.domino.jacksonapt.deser.array.cast.JsDoubleArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.JsIntegerArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.JsShortArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.JsStringArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.bean.JsMapLike;
import com.progressoft.brix.domino.jacksonapt.deser.map.key.DateKeyParser;
import com.progressoft.brix.domino.jacksonapt.deser.map.key.JsDateKeyParser;
import com.progressoft.brix.domino.jacksonapt.stream.impl.JsIntegerStack;
import com.progressoft.brix.domino.jacksonapt.utils.JsDateFormat;
import elemental2.core.Global;

import java.util.Date;

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
