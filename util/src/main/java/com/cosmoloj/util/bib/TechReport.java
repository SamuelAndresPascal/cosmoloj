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
public @interface TechReport {
    String author() default "";
    String title();
    String institution() default "";
    int year();
    String type() default "";
    String number() default "";
    String version() default "";
    String url() default "";
}
