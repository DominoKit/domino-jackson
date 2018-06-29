package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.stream.impl.DefaultJsonWriter;

/**
 * <p>ServerValueStringifier class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@GwtIncompatible
public class ServerValueStringifier implements JacksonContext.ValueStringifier{
    /** {@inheritDoc} */
    @Override
    public String stringify(String value) {
        StringBuilder out=new StringBuilder();
        out.append("\"");
        DefaultJsonWriter.encodeString(value, out);
        out.append("\"");
        return out.toString();
    }
}
