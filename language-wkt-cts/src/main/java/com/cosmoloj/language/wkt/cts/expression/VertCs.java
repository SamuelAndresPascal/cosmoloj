package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class VertCs extends CoordinateSystem {

    private final QuotedName name;
    private final VertDatum datum;
    private final Unit linearUnit;
    private final Axis axis;
    private final Authority authority;

    public VertCs(final int start, final int end, final int index, final QuotedName name, final VertDatum datum,
            final Unit linearUnit, final Axis axis, final Authority authority) {
        super(start, end, index);
        this.name = name;
        this.datum = datum;
        this.linearUnit = linearUnit;
        this.axis = axis;
        this.authority = authority;
    }

    @Override
    public QuotedName getName() {
        return this.name;
    }

    public VertDatum getDatum() {
        return this.datum;
    }

    public Unit getLinearUnit() {
        return this.linearUnit;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
