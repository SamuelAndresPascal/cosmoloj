package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ProjectedCs extends HorzCs {

    private final QuotedName name;
    private final GeographicCs geogCs;
    private final Projection projection;
    private final List<Parameter> parameters;
    private final Unit linearUnit;
    private final Axis axis1;
    private final Axis axis2;
    private final Authority authority;

    public ProjectedCs(final int start, final int end, final int index, final QuotedName name,
            final GeographicCs geogCs, final Projection projection, final List<Parameter> parameters,
            final Unit linearUnit, final Axis axis1, final Axis axis2, final Authority authority) {
        super(start, end, index);
        this.name = name;
        this.geogCs = geogCs;
        this.projection = projection;
        this.parameters = parameters;
        this.linearUnit = linearUnit;
        this.axis1 = axis1;
        this.axis2 = axis2;
        this.authority = authority;
    }

    @Override
    public QuotedName getName() {
        return this.name;
    }

    public GeographicCs getGeogCs() {
        return this.geogCs;
    }

    public Projection getProjection() {
        return this.projection;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

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
