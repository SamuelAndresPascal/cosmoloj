package com.cosmoloj.language.wkt.cts.expression;

import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ConcatMt extends MathTransform {

    private final List<MathTransform> transforms;

    public ConcatMt(final int start, final int end, final int index, final List<MathTransform> transforms) {
        super(start, end, index);
        this.transforms = transforms;
    }

    public List<MathTransform> getTransforms() {
        return this.transforms;
    }
}
