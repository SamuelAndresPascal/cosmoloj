package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 */
public class BaseGeodeticCrs extends BaseCrs.BaseDatumCrs<GeodeticDatum> {

    private final Unit.Angle unit;

    public BaseGeodeticCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final GeodeticDatum datum, final Unit.Angle unit) {
        super(start, end, index, name, datum);
        this.unit = unit;
    }

    public Unit.Angle getUnit() {
        return unit;
    }
}
