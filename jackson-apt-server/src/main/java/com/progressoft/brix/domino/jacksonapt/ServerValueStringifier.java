package com.progressoft.brix.domino.jacksonapt;

import com.progressoft.brix.domino.jacksonapt.stream.impl.DefaultJsonWriter;

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
