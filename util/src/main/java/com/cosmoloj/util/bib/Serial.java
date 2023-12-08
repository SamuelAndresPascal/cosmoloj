package com.cosmoloj.util.bib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Month;

/**
 *
 * @author Samuel Andrés
 */
@Deprecated // déprécié associé à un article
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Serial {
    String volume() default "";
    String issue() default "";
    Month[] month() default {};
    int year() default -1;
    String editor() default "";
    SerialType type() default SerialType.NUMBER;
}
