package com.progressoft.brix.domino.gwtjackson;

import com.progressoft.brix.domino.gwtjackson.deser.array.cast.JsDoubleArrayReader;
import com.progressoft.brix.domino.gwtjackson.deser.array.cast.JsIntegerArrayReader;
import com.progressoft.brix.domino.gwtjackson.deser.array.cast.JsShortArrayReader;
import com.progressoft.brix.domino.gwtjackson.deser.array.cast.JsStringArrayReader;
import com.progressoft.brix.domino.gwtjackson.deser.bean.JsMapLike;
import com.progressoft.brix.domino.gwtjackson.deser.map.key.DateKeyParser;
import com.progressoft.brix.domino.gwtjackson.deser.map.key.JsDateKeyParser;
import com.progressoft.brix.domino.gwtjackson.stream.impl.JsIntegerStack;
import com.progressoft.brix.domino.gwtjackson.utils.JsDateFormat;
import elemental2.core.Global;

import java.util.Date;

public class JsJacksonContext implements JacksonContext{
    @Override
    public DateFormat dateFormat() {
        return new DateFormat() {
            @Override
            public String format(Date date) {
                return JsDateFormat.format(date);
            }

            @Override
            public String format(JsonSerializerParameters params, Date date) {
                return JsDateFormat.format(params, date);
            }

            @Override
            public Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date) {
                return JsDateFormat.parse(useBrowserTimezone, pattern, hasTz, date);
            }

            @Override
            public <D extends Date> DateKeyParser<D> makeDateKeyParser() {
                return new JsDateKeyParser<>();
            }
        };
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
