package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <B>
 * @param <D>
 */
public abstract class BaseDatumCrsBuilder<B extends BaseCrs.BaseDatumCrs<D>, D extends AbstractExpression>
        extends CheckTokenBuilder<Token, B> implements PredicateListTokenBuilder<Token> {

    private final Predicate<? super Token> datumPredicate;
    private final WktKeyword label;

    protected BaseDatumCrsBuilder(final Predicate<? super Token> datumType, final WktKeyword label) {
        this.datumPredicate = datumType;
        this.label = label;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(label,
                LeftDelimiter.class::isInstance,
                QuotedLatinText.class::isInstance,
                SpecialSymbol.COMMA,
                this.datumPredicate,
                RightDelimiter.class::isInstance);
    }

    public static BaseDatumCrsBuilder<BaseCrs.BaseVerticalCrs, NameAndAnchorDatum.VerticalDatum> baseVerticalCrs() {
        return new BaseDatumCrsBuilder<>(NameAndAnchorDatum.VerticalDatum.class::isInstance, WktKeyword.BASEVERTCRS) {

            @Override
            public BaseCrs.BaseVerticalCrs build() {
                return new BaseCrs.BaseVerticalCrs(first(), last(), index(), token(2), token(4));
            }
        };
    }

    public static BaseDatumCrsBuilder<BaseCrs.BaseEngineeringCrs, NameAndAnchorDatum.EngineeringDatum>
        baseEngineeringCrs() {
            return new BaseDatumCrsBuilder<>(NameAndAnchorDatum.EngineeringDatum.class::isInstance,
                    WktKeyword.BASEENGCRS) {

                @Override
                public BaseCrs.BaseEngineeringCrs build() {
                    return new BaseCrs.BaseEngineeringCrs(first(), last(), index(), token(2), token(4));
                }
            };
    }

    public static BaseDatumCrsBuilder<BaseCrs.BaseParametricCrs, NameAndAnchorDatum.ParametricDatum>
        baseParametricCrs() {
            return new BaseDatumCrsBuilder<>(NameAndAnchorDatum.ParametricDatum.class::isInstance,
                    WktKeyword.BASEPARAMCRS) {

                @Override
                public BaseCrs.BaseParametricCrs build() {
                    return new BaseCrs.BaseParametricCrs(first(), last(), index(), token(2), token(4));
                }
            };
    }

    public static BaseDatumCrsBuilder<BaseCrs.BaseTemporalCrs, NameAndAnchorDatum.TemporalDatum> baseTemporalCrs() {
        return new BaseDatumCrsBuilder<>(NameAndAnchorDatum.TemporalDatum.class::isInstance, WktKeyword.BASETIMECRS) {

            @Override
            public BaseCrs.BaseTemporalCrs build() {
                return new BaseCrs.BaseTemporalCrs(first(), last(), index(), token(2), token(4));
            }
        };
    }
}
