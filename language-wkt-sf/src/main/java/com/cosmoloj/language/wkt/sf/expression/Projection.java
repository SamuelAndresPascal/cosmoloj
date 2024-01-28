package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public class Projection extends AbstractExpression {

    private final QuotedName name;

    public Projection(final int start, final int end, final int index, final QuotedName name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedName getName() {
        return this.name;
    }
}
