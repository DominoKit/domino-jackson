package org.dominokit.jacksonapt;

import static java.util.Objects.isNull;

public class JacksonContextProvider {

    static JacksonContext jacksonContext;


    public static JacksonContext get(){
        if(isNull(jacksonContext))
            initContext();
        return jacksonContext;
    }

    private static void initContext() {
        jacksonContext=new ServerJacksonContext();
    }
}
