package com.cosmoloj.util.bib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Samuel Andrés
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Pages {
    int from();
    int to();
}
