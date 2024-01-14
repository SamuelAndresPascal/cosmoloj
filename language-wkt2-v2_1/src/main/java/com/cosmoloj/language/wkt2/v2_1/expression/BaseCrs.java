package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class BaseCrs extends AbstractExpression {

    private final QuotedLatinText name;

    BaseCrs(final int start, final int end, final int index, final QuotedLatinText name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedLatinText getName() {
        return name;
    }

    public abstract static class BaseDatumCrs<D extends AbstractExpression> extends BaseCrs {
        private final D datum;

        public BaseDatumCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final D datum) {
            super(start, end, index, name);
            this.datum = datum;
        }

        public D getDatum() {
            return datum;
        }
    }

    public static class BaseVerticalCrs extends BaseDatumCrs<NameAndAnchorDatum.VerticalDatum> {

        public static final Predicate<Object> BASE_VERTICAL_CRS = t -> t instanceof BaseVerticalCrs;

        public BaseVerticalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.VerticalDatum datum) {
            super(start, end, index, name, datum);
        }
    }

    public static class BaseEngineeringCrs extends BaseDatumCrs<NameAndAnchorDatum.EngineeringDatum> {

        public static final Predicate<Object> BASE_ENGINEERING_CRS = t -> t instanceof BaseEngineeringCrs;

        public BaseEngineeringCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.EngineeringDatum datum) {
            super(start, end, index, name, datum);
        }
    }

    public static class BaseParametricCrs extends BaseDatumCrs<NameAndAnchorDatum.ParametricDatum> {

        public static final Predicate<Object> BASE_PARAMETRIC_CRS = t -> t instanceof BaseParametricCrs;

        public BaseParametricCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.ParametricDatum datum) {
            super(start, end, index, name, datum);
        }
    }

    public static class BaseTemporalCrs extends BaseDatumCrs<NameAndAnchorDatum.TemporalDatum> {

        public static final Predicate<Object> BASE_TEMPORAL_CRS = t -> t instanceof BaseTemporalCrs;

        public BaseTemporalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.TemporalDatum datum) {
            super(start, end, index, name, datum);
        }
    }
}
