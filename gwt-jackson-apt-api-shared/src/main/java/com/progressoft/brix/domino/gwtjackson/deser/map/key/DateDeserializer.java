package com.progressoft.brix.domino.gwtjackson.deser.map.key;

import java.util.Date;

public interface DateDeserializer<D extends Date> {

    /**
     * <p>deserializeMillis</p>
     *
     * @param millis a long.
     * @return a D object.
     */
    D deserializeMillis(long millis);

    /**
     * <p>deserializeDate</p>
     *
     * @param date a {@link Date} object.
     * @return a D object.
     */
    D deserializeDate(Date date);
}
