package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class MathTransform extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof MathTransform;

    public MathTransform(final int start, final int end, final int index) {
        super(start, end, index);
    }

}
