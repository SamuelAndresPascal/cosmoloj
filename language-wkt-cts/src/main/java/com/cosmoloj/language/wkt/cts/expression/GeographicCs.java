package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class GeographicCs extends HorzCs {

    private final QuotedName name;
    private final Datum datum;
    private final PrimeMeridian primeMeridian;
    private final Unit angularUnit;
    @Deprecated // utilisé dans la version WKT SF pour les GEOGCS 3d visiblement remplacés par les Compound CS
    private final Unit linearUnit;
    private final Axis axis1;
    private final Axis axis2;
    private final Authority authority;

    public GeographicCs(final int start, final int end, final int index, final QuotedName name, final Datum datum,
            final PrimeMeridian primeMeridian, final Unit angularUnit, final Unit linearUnit, final Axis axis1,
            final Axis axis2, final Authority authority) {
        super(start, end, index);
        this.name = name;
        this.datum = datum;
        this.primeMeridian = primeMeridian;
        this.angularUnit = angularUnit;
        this.linearUnit = linearUnit;
        this.axis1 = axis1;
        this.axis2 = axis2;
        this.authority = authority;
    }

    @Override
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

    @Deprecated
    public Unit getLinearUnit() {
        return this.linearUnit;
    }

    public Axis getAxis1() {
        return this.axis1;
    }

    public Axis getAxis2() {
        return this.axis2;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
