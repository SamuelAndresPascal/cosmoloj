package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ProjectedCs extends SpatialReferenceSystem {

    private final QuotedName name;
    private final GeographicCs geogCs;
    private final Projection projection;
    private final List<Parameter> parameters;
    private final Unit linearUnit;

    public ProjectedCs(final int start, final int end, final int index, final QuotedName name,
            final GeographicCs geogCs, final Projection projection, final List<Parameter> parameters,
            final Unit linearUnit) {
        super(start, end, index);
        this.name = name;
        this.geogCs = geogCs;
        this.projection = projection;
        this.parameters = parameters;
        this.linearUnit = linearUnit;
    }

    public QuotedName getName() {
        return this.name;
    }

    public GeographicCs getGeographicCs() {
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
}
