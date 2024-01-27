package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 */
public class BaseProjectedCrs extends BaseCrs {

    private final BaseGeodeticCrs baseGeodetic;
    private final Operation.MapProjection projection;

    public BaseProjectedCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final BaseGeodeticCrs baseGeodetic, final Operation.MapProjection projection) {
        super(start, end, index, name);
        this.baseGeodetic = baseGeodetic;
        this.projection = projection;
    }

    public BaseGeodeticCrs getBaseGeodeticCrs() {
        return this.baseGeodetic;
    }

    public Operation.MapProjection getProjection() {
        return this.projection;
    }
}
