package com.cosmoloj.processor.bibliography.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Samuel Andrés
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Entry {

    EntryType entryType();

    String address() default "";
    String annote() default "";
    String author() default "";
    String booktitle() default "";
    String chapter() default "";
    String crossref() default "";
    String edition() default "";
    String editor() default "";
    String howpublished() default "";
    String institution() default "";
    String journal() default "";
    String month() default "";
    String note() default "";
    String number() default "";
    String organization() default "";
    String pages() default "";
    String publisher() default "";
    String school() default "";
    String series() default "";
    String title() default "";
    String type() default "";
    String volume() default "";
    String year() default "";

    // non-standard

    String doi() default "";
    String issn() default "";
    String eissn() default "";
    String isbn() default "";
    String url() default "";
}
