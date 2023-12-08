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
    TechReportKind kind();
    String title() default "";
    String institution() default "";
    String number() default "";
    String version() default "";
    int year() default -1;
    String url() default "";
}
