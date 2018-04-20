package org.dominokit.jacksonapt;

import com.google.auto.service.AutoService;
import org.dominokit.jacksonapt.deser.array.cast.DefaultDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.DefaultMapLike;
import org.dominokit.jacksonapt.stream.impl.DefaultIntegerStack;
import org.dominokit.jacksonapt.utils.DefaultDateFormat;

@AutoService(JacksonContextProvider.Initializer.class)
public class ServerJacksonContext implements JacksonContext, JacksonContextProvider.Initializer {

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

    @Override
    public void init() {
        JacksonContextProvider.jacksonContext=new ServerJacksonContext();
    }
}
