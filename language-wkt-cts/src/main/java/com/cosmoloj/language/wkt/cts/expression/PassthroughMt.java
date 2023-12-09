package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;

/**
 *
 * @author Samuel Andrés
 */
public class PassthroughMt extends MathTransform {

    private final SignedNumericLiteral integer;
    private final MathTransform transform;

    public PassthroughMt(final int start, final int end, final int index, final SignedNumericLiteral integer,
            final MathTransform transform) {
        super(start, end, index);
        this.integer = integer;
        this.transform = transform;
    }

    public SignedNumericLiteral getInteger() {
        return this.integer;
    }

    public MathTransform getTransform() {
        return this.transform;
    }
}
