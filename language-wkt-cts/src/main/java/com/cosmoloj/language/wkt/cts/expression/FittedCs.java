package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class FittedCs extends CoordinateSystem {

    private final QuotedName name;
    private final MathTransform mathTransform;
    private final CoordinateSystem coordinateSystem;

    public FittedCs(final int start, final int end, final int index, final QuotedName name,
            final MathTransform mathTransform, final CoordinateSystem coordinateSystem) {
        super(start, end, index);
        this.name = name;
        this.mathTransform = mathTransform;
        this.coordinateSystem = coordinateSystem;
    }

    @Override
    public QuotedName getName() {
        return this.name;
    }

    public MathTransform getMathTransform() {
        return this.mathTransform;
    }

    public CoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }
}
