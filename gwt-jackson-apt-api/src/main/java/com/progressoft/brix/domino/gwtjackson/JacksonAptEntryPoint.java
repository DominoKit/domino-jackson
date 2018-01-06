package com.progressoft.brix.domino.gwtjackson;

import com.google.gwt.core.client.EntryPoint;

public class JacksonAptEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        JacksonContextProvider.jacksonContext=new JsJacksonContext();
    }
}
