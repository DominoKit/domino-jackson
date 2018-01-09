package com.progressoft.brix.domino.jacksonapt;

import com.progressoft.brix.domino.jacksonapt.deser.array.cast.DefaultDoubleArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.DefaultIntegerArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.DefaultShortArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.array.cast.DefaultStringArrayReader;
import com.progressoft.brix.domino.jacksonapt.deser.bean.DefaultMapLike;
import com.progressoft.brix.domino.jacksonapt.stream.impl.DefaultIntegerStack;
import com.progressoft.brix.domino.jacksonapt.utils.DefaultDateFormat;

public class ServerJacksonContext implements JacksonContext{

    private static final ValueStringifier VALUE_STRINGIFIER=new ServerValueStringifier();

    @Override
    public DateFormat dateFormat() {
        return new DefaultDateFormat();
    }

    @Override
    public IntegerStackFactory integerStackFactory() {
        return DefaultIntegerStack::new;
    }

    @Override
    public MapLikeFactory mapLikeFactory() {
        return DefaultMapLike::new;
    }

    @Override
    public ValueStringifier stringifier() {
        return VALUE_STRINGIFIER;
    }

    @Override
    public StringArrayReader stringArrayReader() {
        return new DefaultStringArrayReader();
    }

    @Override
    public ShortArrayReader shortArrayReader() {
        return new DefaultShortArrayReader();
    }

    @Override
    public IntegerArrayReader integerArrayReader() {
        return new DefaultIntegerArrayReader();
    }

    @Override
    public DoubleArrayReader doubleArrayReader() {
        return new DefaultDoubleArrayReader();
    }

    @Override
    public JsonSerializerParameters defaultSerializerParameters() {
        return ServerJacksonJsonSerializerParameters.DEFAULT;
    }

    @Override
    public JsonDeserializerParameters defaultDeserializerParameters() {
        return ServerJacksonJsonDeserializerParameters.DEFAULT;
    }
}
