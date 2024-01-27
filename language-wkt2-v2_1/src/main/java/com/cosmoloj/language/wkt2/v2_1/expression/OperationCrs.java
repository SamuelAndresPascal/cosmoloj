package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class OperationCrs extends AbstractExpression {

    private final Crs crs;

    protected OperationCrs(final int first, final int last, final int index, final Crs crs) {
        super(first, last, index);
        this.crs = crs;
    }

    public Crs getCrs() {
        return this.crs;
    }

    public static class SourceCrs extends OperationCrs {

        public SourceCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }

    public static class TargetCrs extends OperationCrs {

        public TargetCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }

    public static class InterpolationCrs extends OperationCrs {

        public InterpolationCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }
}
