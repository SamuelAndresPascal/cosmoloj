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
public @interface Misc {
    String title() default "";
    String issn() default "";
    String eIssn() default "";
    String url() default "";
}
