package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisOrder extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof AxisOrder;

    private final UnsignedInteger value;

    public AxisOrder(final int start, final int end, final int index, final UnsignedInteger value) {
        super(start, end, index);
        this.value = value;
    }

    public UnsignedInteger getValue() {
        return value;
    }
}
