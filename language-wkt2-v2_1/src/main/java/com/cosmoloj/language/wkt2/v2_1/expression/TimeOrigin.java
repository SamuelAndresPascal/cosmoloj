package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public class TimeOrigin extends AbstractExpression {

    private final Lexeme description;

    public TimeOrigin(final int start, final int end, final int index, final Lexeme description) {
        super(start, end, index);
        this.description = description;
    }

    public Lexeme getDescription() {
        return this.description;
    }
}
