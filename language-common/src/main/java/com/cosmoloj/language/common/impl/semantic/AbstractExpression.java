package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.Expression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractExpression implements Expression {

    private final int first;
    private final int last;
    private final int index;

    protected AbstractExpression(final int first, final int last, final int index) {
        this.first = first;
        this.last = last;
        this.index = index;
    }

    @Override
    public int first() {
        return this.first;
    }

    @Override
    public int last() {
        return this.last;
    }

    @Override
    public int order() {
        return this.index;
    }
}
