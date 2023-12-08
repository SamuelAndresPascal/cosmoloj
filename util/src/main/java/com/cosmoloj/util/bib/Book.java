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
public @interface Book {
    String title() default "";
    String ssbn() default "";
    int year() default -1;
    String editor() default "";
    String url() default "";
}
