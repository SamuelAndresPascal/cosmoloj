package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 */
public class Anchor extends AbstractExpression {

    private final QuotedLatinText description;

    public Anchor(final int start, final int end, final int index, final QuotedLatinText description) {
        super(start, end, index);
        this.description = description;
    }

    public QuotedLatinText getDescription() {
        return this.description;
    }
}
