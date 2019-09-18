package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.array.cast.DefaultDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.DefaultMapLike;
import org.dominokit.jacksonapt.stream.impl.DefaultIntegerStack;
import org.dominokit.jacksonapt.utils.DefaultDateFormat;


/**
 * <p>ServerJacksonContext class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class ServerJacksonContext extends JsJacksonContext{

    @GwtIncompatible
    private static final ValueStringifier VALUE_STRINGIFIER = new ServerValueStringifier();

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public DateFormat dateFormat() {
        return new DefaultDateFormat();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public IntegerStackFactory integerStackFactory() {
        return DefaultIntegerStack::new;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public MapLikeFactory mapLikeFactory() {
        return DefaultMapLike::new;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public ValueStringifier stringifier() {
        return VALUE_STRINGIFIER;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public StringArrayReader stringArrayReader() {
        return new DefaultStringArrayReader();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public ShortArrayReader shortArrayReader() {
        return new DefaultShortArrayReader();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public IntegerArrayReader integerArrayReader() {
        return new DefaultIntegerArrayReader();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public DoubleArrayReader doubleArrayReader() {
        return new DefaultDoubleArrayReader();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public JsonSerializerParameters defaultSerializerParameters() {
        return ServerJacksonJsonSerializerParameters.DEFAULT;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public JsonDeserializerParameters defaultDeserializerParameters() {
        return ServerJacksonJsonDeserializerParameters.DEFAULT;
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public JsonSerializerParameters newSerializerParameters() {
        return new ServerJacksonJsonSerializerParameters();
    }

    /** {@inheritDoc} */
    @GwtIncompatible
    @Override
    public JsonDeserializerParameters newDeserializerParameters() {
        return new ServerJacksonJsonDeserializerParameters();
    }

}
