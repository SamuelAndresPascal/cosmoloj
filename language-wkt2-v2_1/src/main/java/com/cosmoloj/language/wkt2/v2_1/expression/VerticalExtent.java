package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;

/**
 *
 * @author Samuel Andrés
 */
public class VerticalExtent extends Extent {

    private final SignedNumericLiteral minimumHeight;
    private final SignedNumericLiteral maximumHeight;
    private final Unit.Length lengthUnit;

    public VerticalExtent(final int start, final int end, final int index,
            final SignedNumericLiteral minimumHeight, final SignedNumericLiteral maximumHeight,
            final Unit.Length lengthUnit) {
        super(start, end, index);
        this.minimumHeight = minimumHeight;
        this.maximumHeight = maximumHeight;
        this.lengthUnit = lengthUnit;
    }

    public SignedNumericLiteral getMinimumHeight() {
        return minimumHeight;
    }

    public SignedNumericLiteral getMaximumHeight() {
        return maximumHeight;
    }

    public Unit.Length getLengthUnit() {
        return lengthUnit;
    }
}
