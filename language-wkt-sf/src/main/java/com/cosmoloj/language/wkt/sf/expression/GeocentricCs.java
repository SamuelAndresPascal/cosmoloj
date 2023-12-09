package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class GeocentricCs extends SpatialReferenceSystem {

    private final QuotedName name;
    private final Datum datum;
    private final PrimeMeridian primeMeridian;
    private final Unit linearUnit;

    public GeocentricCs(final int start, final int end, final int index, final QuotedName name,
            final Datum datum, final PrimeMeridian primeMeridian, final Unit linearUnit) {
        super(start, end, index);
        this.name = name;
        this.datum = datum;
        this.primeMeridian = primeMeridian;
        this.linearUnit = linearUnit;
    }

    public QuotedName getName() {
        return this.name;
    }

    public Datum getDatum() {
        return this.datum;
    }

    public PrimeMeridian getPrimeMeridian() {
        return this.primeMeridian;
    }

    public Unit getLinearUnit() {
        return this.linearUnit;
    }
}
