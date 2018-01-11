package com.progressoft.brix.domino.jacksonapt;

import com.google.gwt.core.shared.GwtIncompatible;

import java.util.ServiceLoader;

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
        ServiceLoader<Initializer> load = ServiceLoader.load(Initializer.class);
        if(load.iterator().hasNext())
            load.iterator().next().init();
    }
}
