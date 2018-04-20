package org.dominokit.jacksonapt;

import static java.util.Objects.isNull;

public class JacksonContextProvider {

    static JacksonContext jacksonContext;

    public interface Initializer{
        void init();
    }

    public static JacksonContext get(){
        if(isNull(jacksonContext))
            initContext();
        return jacksonContext;
    }

    private static void initContext() {
        jacksonContext=new JsJacksonContext();
    }
}
