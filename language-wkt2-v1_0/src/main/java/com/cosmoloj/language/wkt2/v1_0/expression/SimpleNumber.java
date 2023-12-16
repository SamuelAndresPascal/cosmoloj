package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class SimpleNumber extends AbstractExpression {

    private final SignedNumericLiteral value;

    public SimpleNumber(final int start, final int end, final int index, final SignedNumericLiteral value) {
        super(start, end, index);
        this.value = value;
    }

    public SignedNumericLiteral getValue() {
        return value;
    }

    public static class Bearing extends SimpleNumber {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Bearing;

        public Bearing(final int start, final int end, final int index, final SignedNumericLiteral value) {
            super(start, end, index, value);
        }
    }

    public static class Accuracy extends SimpleNumber {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Accuracy;

        public Accuracy(final int start, final int end, final int index, final SignedNumericLiteral value) {
            super(start, end, index, value);
        }
    }
}
