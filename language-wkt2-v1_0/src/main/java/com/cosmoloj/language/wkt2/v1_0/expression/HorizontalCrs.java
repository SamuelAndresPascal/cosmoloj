package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Expression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public interface HorizontalCrs extends Expression {

    Predicate<Object> HORIZONTAL_CRS = t -> t instanceof HorizontalCrs;
}
