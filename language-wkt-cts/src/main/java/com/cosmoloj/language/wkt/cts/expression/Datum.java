package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Datum extends AbstractExpression {

    private final QuotedName name;
    private final Spheroid spheroid;

    public Datum(final int start, final int end, final int index, final QuotedName name,
            final Spheroid spheroid) {
        super(start, end, index);
        this.name = name;
        this.spheroid = spheroid;
    }

    public QuotedName getName() {
        return this.name;
    }

    public Spheroid getSpheroid() {
        return this.spheroid;
    }
}
