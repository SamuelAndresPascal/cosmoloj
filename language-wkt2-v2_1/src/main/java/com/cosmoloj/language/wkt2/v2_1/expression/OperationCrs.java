package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class OperationCrs extends AbstractExpression {

    private final Crs crs;

    public OperationCrs(final int first, final int last, final int index, final Crs crs) {
        super(first, last, index);
        this.crs = crs;
    }

    public Crs getCrs() {
        return this.crs;
    }

    public static class SourceCrs extends OperationCrs {

        public static final Predicate<Object> INSTANCE_OF_SOURCE_CRS = t -> t instanceof SourceCrs;

        public SourceCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }

    public static class TargetCrs extends OperationCrs {

        public static final Predicate<Object> INSTANCE_OF_TARGET_CRS = t -> t instanceof TargetCrs;

        public TargetCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }

    public static class InterpolationCrs extends OperationCrs {

        public static final Predicate<Object> INSTANCE_OF_INTERPOLATION_CRS = t -> t instanceof InterpolationCrs;

        public InterpolationCrs(final int first, final int last, final int index, final Crs crs) {
            super(first, last, index, crs);
        }
    }
}
