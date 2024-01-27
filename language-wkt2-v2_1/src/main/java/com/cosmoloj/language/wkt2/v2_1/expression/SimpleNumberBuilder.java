package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <N>
 */
public abstract class SimpleNumberBuilder<N extends SimpleNumber> extends CheckTokenBuilder<Lexeme, N>
        implements PredicateListTokenBuilder<Lexeme> {

    private final WktKeyword label;

    protected SimpleNumberBuilder(final WktKeyword label) {
        this.label = label;
    }

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(this.label,
                LeftDelimiter.class::isInstance,
                SignedNumericLiteral.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    public static class BearingBuilder extends SimpleNumberBuilder<SimpleNumber.Bearing> {

        public BearingBuilder() {
            super(WktKeyword.BEARING);
        }

        @Override
        public SimpleNumber.Bearing build() {
            return new SimpleNumber.Bearing(first(), last(), index(), (SignedNumericLiteral) token(2));
        }
    }

    public static class AccuracyBuilder extends SimpleNumberBuilder<SimpleNumber.Accuracy> {

        public AccuracyBuilder() {
            super(WktKeyword.OPERATIONACCURACY);
        }

        @Override
        public SimpleNumber.Accuracy build() {
            return new SimpleNumber.Accuracy(first(), last(), index(), (SignedNumericLiteral) token(2));
        }
    }
}
