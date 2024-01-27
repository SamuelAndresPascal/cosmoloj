package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <D> <span class="fr">type de datum</span>
 * @param <A> <span class="fr">type d'attache</span>
 */
public abstract class NameAndAnchorDatumBuilder<D extends NameAndAnchorDatum<A>, A extends AbstractExpression>
        extends CheckTokenBuilder<Token, D> implements PredicateIndexTokenBuilder<Token>,
        ConstraintBeforePredicateTokenBuilder<Token> {


    private final Predicate<? super Token> anchorPredicate;
    private final Predicate<? super Token> labels;

    NameAndAnchorDatumBuilder(final Predicate<? super Token> anchorPredicate, final WktKeyword... labels) {
        this.anchorPredicate = anchorPredicate;
        Predicate<? super Token> l = labels[0];
        for (int i = 1; i < labels.length; i++) {
            l = l.or(labels[i]);
        }
        this.labels = l;
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> labels;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            default -> {
                if (odd()) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else {
                    yield anchorPredicate.or(Identifier.class::isInstance);
                }
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintBefore(final int before) {
        if (even() && beyond(2)) {
            return switch (before) {
                case 1 -> SpecialSymbol.COMMA;
                case 2 -> current(anchorPredicate) ? QuotedLatinText.class::isInstance : t -> true;
                default -> t -> true;
            };
        }
        return t -> true;
    }

    protected final A extractAnchor() {
        return firstToken(this.anchorPredicate);
    }

    public static NameAndAnchorDatumBuilder<NameAndAnchorDatum.VerticalDatum, Anchor> verticalDatum() {
        return new NameAndAnchorDatumBuilder<>(Anchor.class::isInstance, WktKeyword.VDATUM, WktKeyword.VERTICALDATUM,
                WktKeyword.VERT_DATUM) {

            @Override
            public NameAndAnchorDatum.VerticalDatum build() {

                return new NameAndAnchorDatum.VerticalDatum(first(), last(), index(), token(2),
                        extractAnchor(), tokens(Identifier.class::isInstance));
            }
        };
    }

    public static NameAndAnchorDatumBuilder<NameAndAnchorDatum.EngineeringDatum, Anchor> engineeringDatum() {
        return new NameAndAnchorDatumBuilder<>(Anchor.class::isInstance, WktKeyword.EDATUM, WktKeyword.ENGINEERINGDATUM,
                WktKeyword.LOCAL_DATUM) {

            @Override
            public NameAndAnchorDatum.EngineeringDatum build() {

                return new NameAndAnchorDatum.EngineeringDatum(first(), last(), index(), token(2),
                        extractAnchor(), tokens(Identifier.class::isInstance));
            }
        };
    }

    public static NameAndAnchorDatumBuilder<NameAndAnchorDatum.ParametricDatum, Anchor> parametricDatum() {
        return new NameAndAnchorDatumBuilder<>(
                Anchor.class::isInstance, WktKeyword.PDATUM, WktKeyword.PARAMETRICDATUM) {

            @Override
            public NameAndAnchorDatum.ParametricDatum build() {

                return new NameAndAnchorDatum.ParametricDatum(first(), last(), index(), token(2),
                        extractAnchor(), tokens(Identifier.class::isInstance));
            }
        };
    }

    public static NameAndAnchorDatumBuilder<NameAndAnchorDatum.TemporalDatum, TimeOrigin> temporalDatum() {
        return new NameAndAnchorDatumBuilder<>(TimeOrigin.class::isInstance, WktKeyword.TDATUM, WktKeyword.TIMEDATUM) {
            @Override
            public NameAndAnchorDatum.TemporalDatum build() {

                return new NameAndAnchorDatum.TemporalDatum(first(), last(), index(), token(2),
                        extractAnchor(), tokens(Identifier.class::isInstance));
            }
        };
    }
}
