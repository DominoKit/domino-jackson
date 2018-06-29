package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.array.cast.DefaultDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.DefaultMapLike;
import org.dominokit.jacksonapt.stream.impl.DefaultIntegerStack;
import org.dominokit.jacksonapt.utils.DefaultDateFormat;


public class ServerJacksonContext extends JsJacksonContext{

    @GwtIncompatible
    private static final ValueStringifier VALUE_STRINGIFIER = new ServerValueStringifier();

    @GwtIncompatible
    @Override
    public DateFormat dateFormat() {
        return new DefaultDateFormat();
    }

    @GwtIncompatible
    @Override
    public IntegerStackFactory integerStackFactory() {
        return DefaultIntegerStack::new;
    }

    @GwtIncompatible
    @Override
    public MapLikeFactory mapLikeFactory() {
        return DefaultMapLike::new;
    }

    @GwtIncompatible
    @Override
    public ValueStringifier stringifier() {
        return VALUE_STRINGIFIER;
    }

    @GwtIncompatible
    @Override
    public StringArrayReader stringArrayReader() {
        return new DefaultStringArrayReader();
    }

    @GwtIncompatible
    @Override
    public ShortArrayReader shortArrayReader() {
        return new DefaultShortArrayReader();
    }

    @GwtIncompatible
    @Override
    public IntegerArrayReader integerArrayReader() {
        return new DefaultIntegerArrayReader();
    }

    @GwtIncompatible
    @Override
    public DoubleArrayReader doubleArrayReader() {
        return new DefaultDoubleArrayReader();
    }

    @GwtIncompatible
    @Override
    public JsonSerializerParameters defaultSerializerParameters() {
        return ServerJacksonJsonSerializerParameters.DEFAULT;
    }

    @GwtIncompatible
    @Override
    public JsonDeserializerParameters defaultDeserializerParameters() {
        return ServerJacksonJsonDeserializerParameters.DEFAULT;
    }

}
