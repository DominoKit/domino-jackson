package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.stream.impl.DefaultJsonWriter;

public class ServerValueStringifier implements JacksonContext.ValueStringifier{
    @Override
    public String stringify(String value) {
        StringBuilder out=new StringBuilder();
        out.append("\"");
        DefaultJsonWriter.encodeString(value, out);
        out.append("\"");
        return out.toString();
    }
}
