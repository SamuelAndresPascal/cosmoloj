package com.cosmoloj.language.wkt.cts.expression;

/**
 *
 * @author Samuel Andrés
 */
public class InverseMt extends MathTransform {

    private final MathTransform transform;

    public InverseMt(final int start, final int end, final int index, final MathTransform transform) {
        super(start, end, index);
        this.transform = transform;
    }

    public MathTransform getTransform() {
        return this.transform;
    }
}
