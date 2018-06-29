package org.dominokit.jacksonapt;

import static java.util.Objects.isNull;

/**
 * <p>JacksonContextProvider class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JacksonContextProvider {

    static JacksonContext jacksonContext;


    /**
     * <p>get.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JacksonContext} object.
     */
    public static JacksonContext get(){
        if(isNull(jacksonContext))
            initContext();
        return jacksonContext;
    }

    private static void initContext() {
        jacksonContext=new ServerJacksonContext();
    }
}
