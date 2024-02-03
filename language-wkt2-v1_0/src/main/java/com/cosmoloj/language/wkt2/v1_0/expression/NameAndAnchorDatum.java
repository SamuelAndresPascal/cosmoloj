package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <A>
 */
public abstract class NameAndAnchorDatum<A> extends AbstractExpression {

    private final QuotedLatinText name;
    private final A anchor;
    private final List<Identifier> identifiers;

    public NameAndAnchorDatum(final int start, final int end, final int index, final QuotedLatinText name,
            final A anchor, final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.anchor = anchor;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public A getAnchor() {
        return this.anchor;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public static class VerticalDatum extends NameAndAnchorDatum<Anchor> {

        public VerticalDatum(final int start, final int end, final int index, final QuotedLatinText name,
                final Anchor anchor, final List<Identifier> identifiers) {
            super(start, end, index, name, anchor, identifiers);
        }
    }

    public static class EngineeringDatum extends NameAndAnchorDatum<Anchor> {

        public EngineeringDatum(final int start, final int end, final int index, final QuotedLatinText name,
                final Anchor anchor, final List<Identifier> identifiers) {
            super(start, end, index, name, anchor, identifiers);
        }
    }

    public static class ParametricDatum extends NameAndAnchorDatum<Anchor> {

        public ParametricDatum(final int start, final int end, final int index, final QuotedLatinText name,
                final Anchor anchor, final List<Identifier> identifiers) {
            super(start, end, index, name, anchor, identifiers);
        }
    }

    public static class TemporalDatum extends NameAndAnchorDatum<TimeOrigin> {

        public TemporalDatum(final int start, final int end, final int index, final QuotedLatinText name,
                final TimeOrigin anchor, final List<Identifier> identifiers) {
            super(start, end, index, name, anchor, identifiers);
        }
    }
}
