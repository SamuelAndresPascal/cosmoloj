package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;

/**
 *
 * @author Samuel Andrés
 */
public class Meridian extends AbstractExpression {

    private final SignedNumericLiteral longitude;
    private final Unit.Angle unit;

    public Meridian(final int start, final int end, final int index, final SignedNumericLiteral irmLongitude,
            final Unit.Angle angleUnit) {
        super(start, end, index);
        this.longitude = irmLongitude;
        this.unit = angleUnit;
    }

    public SignedNumericLiteral getLongitude() {
        return longitude;
    }

    public Unit.Angle getUnit() {
        return unit;
    }
}
