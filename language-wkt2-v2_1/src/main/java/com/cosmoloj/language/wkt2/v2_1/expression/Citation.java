package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 */
public class Citation extends AbstractExpression {

    private final QuotedLatinText name;

    public Citation(final int start, final int end, final int index, final QuotedLatinText name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedLatinText getText() {
        return this.name;
    }
}
