package org.dominokit.jacksonapt;

import java.lang.annotation.*;

/**
 * <p>GwtIncompatible class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.TYPE, ElementType.METHOD,
        ElementType.CONSTRUCTOR, ElementType.FIELD })
@Documented
public @interface GwtIncompatible {
    String value() default "";
}
