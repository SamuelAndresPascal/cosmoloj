package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class LocalCs extends CoordinateSystem {

    private final QuotedName name;
    private final LocalDatum datum;
    private final Unit linearUnit;
    private final List<Axis> axis;
    private final Authority authority;

    public LocalCs(final int start, final int end, final int index, final QuotedName name, final LocalDatum datum,
            final Unit linearUnit, final List<Axis> axis, final Authority authority) {
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

    public LocalDatum getDatum() {
        return this.datum;
    }

    public Unit getLinearUnit() {
        return this.linearUnit;
    }

    public List<Axis> getAxis() {
        return this.axis;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
