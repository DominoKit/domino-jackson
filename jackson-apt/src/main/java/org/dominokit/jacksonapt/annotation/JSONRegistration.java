package org.dominokit.jacksonapt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <p>JSONRegistration class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@Target(ElementType.PACKAGE)
public @interface JSONRegistration {
    String value();
}
