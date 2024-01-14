package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Extent extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Extent;

    public Extent(final int first, final int last, final int index) {
        super(first, last, index);
    }
}
