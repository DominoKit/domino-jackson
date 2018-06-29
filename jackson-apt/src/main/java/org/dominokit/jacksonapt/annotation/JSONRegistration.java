package org.dominokit.jacksonapt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
public @interface JSONRegistration {
    String value();
}
