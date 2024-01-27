package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public class Usage extends AbstractExpression {

    private final Scope scope;
    private final Extent extent;

    public Usage(final int start, final int end, final int index, final Scope scope, final Extent extent) {
        super(start, end, index);
        this.scope = scope;
        this.extent = extent;
    }

    public Scope getScope() {
        return scope;
    }

    public Extent getExtent() {
        return extent;
    }
}
