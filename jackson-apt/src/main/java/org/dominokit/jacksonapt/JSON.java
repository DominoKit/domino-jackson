package org.dominokit.jacksonapt;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class JSON {
    public native static String stringify(Object jsonObj);
}
