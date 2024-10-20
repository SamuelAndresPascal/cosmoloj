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
public @interface InProceedings {
    String title() default "";
    String subtitle() default "";
    String pages() default "";
    String url() default "";
    String issue() default "";
    String volume() default "";
    String month() default "";
    int year() default 0;
}
