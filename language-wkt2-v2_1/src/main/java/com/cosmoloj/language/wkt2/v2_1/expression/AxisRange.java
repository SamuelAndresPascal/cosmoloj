package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public class AxisRange extends AbstractExpression {

    private final AxisMinimumValue min;
    private final AxisMaximumValue max;
    private final AxisRangeMeaning meaning;

    protected AxisRange(final int start, final int end, final int index,
            final AxisMinimumValue min, final AxisMaximumValue max, final AxisRangeMeaning meaning) {
        super(start, end, index);
        this.min = min;
        this.max = max;
        this.meaning = meaning;
    }

    public AxisMinimumValue getMin() {
        return min;
    }

    public AxisMaximumValue getMax() {
        return max;
    }

    public AxisRangeMeaning getMeaning() {
        return meaning;
    }
}
