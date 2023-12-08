package com.cosmoloj.util.bib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Samuel Andrés
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Web {
    String[] value() default "";
    String title() default "";
    String[] url() default "";
    String institution() default "";
    int year() default 0;
}
