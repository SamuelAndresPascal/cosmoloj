package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Meridian extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Meridian;

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
