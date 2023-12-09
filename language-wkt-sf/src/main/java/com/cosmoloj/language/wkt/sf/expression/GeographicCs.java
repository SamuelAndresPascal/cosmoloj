package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class GeographicCs extends SpatialReferenceSystem {

    private final QuotedName name;
    private final Datum datum;
    private final PrimeMeridian primeMeridian;
    private final Unit angularUnit;
    private final Unit linearUnit;

    public GeographicCs(final int start, final int end, final int index, final QuotedName name,
            final Datum datum, final PrimeMeridian primeMeridian, final Unit angularUnit,
            final Unit linearUnit) {
        super(start, end, index);
        this.name = name;
        this.datum = datum;
        this.primeMeridian = primeMeridian;
        this.angularUnit = angularUnit;
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

    public Unit getAngularUnit() {
        return this.angularUnit;
    }

    public Unit getLinearUnit() {
        return this.linearUnit;
    }
}
