package com.cosmoloj.language.common.number.lexeme.compound;

/**
 *
 * @author Samuel Andrés
 */
public class ApproximateNumericLiteral extends UnsignedNumericLiteral {

    public ApproximateNumericLiteral(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    @Override
    public Double parse(final String codePoints) {
        return Double.parseDouble(codePoints);
    }

    @Override
    public final boolean isDecimal() {
        return true;
    }
}
