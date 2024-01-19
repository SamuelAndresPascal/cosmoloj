package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
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
 * @param <CRS>
 * @param <D>
 */
public abstract class SimpleCrsShellBuilder<CRS extends SimpleCrsShell<D>, D extends AbstractExpression>
        extends CheckTokenBuilder<Token, CRS> implements PredicateIndexTokenBuilder<Token> {

    private final Predicate<? super Token> datumPredicate;
    private final Predicate<? super Token> labels;

    protected SimpleCrsShellBuilder(final Predicate<? super Token> datumType, final WktKeyword... labels) {
        this.datumPredicate = datumType;
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
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> datumPredicate;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> CoordinateSystem.class::isInstance;
            default -> {
                if (odd()) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else {
                    yield Scope.INSTANCE_OF
                            .or(Extent.INSTANCE_OF)
                            .or(Identifier.INSTANCE_OF)
                            .or(Remark.INSTANCE_OF);
                }
            }
        };
    }

    public static SimpleCrsShellBuilder<SimpleCrsShell.VerticalCrs, NameAndAnchorDatum.VerticalDatum> verticalCrs() {
        return new SimpleCrsShellBuilder<>(NameAndAnchorDatum.VerticalDatum.VERTICAL_DATUM,
                WktKeyword.VERTCRS, WktKeyword.VERTICALCRS, WktKeyword.VERT_CS) {

                    @Override
                    public SimpleCrsShell.VerticalCrs build() {
                        return new SimpleCrsShell.VerticalCrs(first(), last(), index(), token(2), token(4), token(6),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.INSTANCE_OF),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
    }

    public static SimpleCrsShellBuilder<SimpleCrsShell.EngineeringCrs, NameAndAnchorDatum.EngineeringDatum>
        engineeringCrs() {
            return new SimpleCrsShellBuilder<>(NameAndAnchorDatum.EngineeringDatum.ENGINEERING_DATUM,
                    WktKeyword.ENGCRS, WktKeyword.ENGINEERINGCRS, WktKeyword.LOCAL_CS) {

                        @Override
                        public SimpleCrsShell.EngineeringCrs build() {
                            return new SimpleCrsShell.EngineeringCrs(first(), last(), index(), token(2), token(4),
                                    token(6),
                                    firstToken(Scope.INSTANCE_OF),
                                    tokens(Extent.INSTANCE_OF),
                                    tokens(Identifier.INSTANCE_OF),
                                    firstToken(Remark.INSTANCE_OF));
                        }
                    };
    }

    public static SimpleCrsShellBuilder<SimpleCrsShell.ImageCrs, ImageDatum> imageCrs() {
        return new SimpleCrsShellBuilder<>(ImageDatum.class::isInstance, WktKeyword.IMAGECRS) {

            @Override
            public SimpleCrsShell.ImageCrs build() {
                return new SimpleCrsShell.ImageCrs(first(), last(), index(), token(2), token(4), token(6),
                        firstToken(Scope.INSTANCE_OF),
                        tokens(Extent.INSTANCE_OF),
                        tokens(Identifier.INSTANCE_OF),
                        firstToken(Remark.INSTANCE_OF));
            }
        };
    }

    public static SimpleCrsShellBuilder<SimpleCrsShell.ParametricCrs, NameAndAnchorDatum.ParametricDatum>
        parametricCrs() {
            return new SimpleCrsShellBuilder<>(NameAndAnchorDatum.ParametricDatum.PARAMETRIC_DATUM,
                    WktKeyword.PARAMETRICCRS) {

                        @Override
                        public SimpleCrsShell.ParametricCrs build() {
                            return new SimpleCrsShell.ParametricCrs(first(), last(), index(), token(2), token(4),
                                    token(6),
                                    firstToken(Scope.INSTANCE_OF),
                                    tokens(Extent.INSTANCE_OF),
                                    tokens(Identifier.INSTANCE_OF),
                                    firstToken(Remark.INSTANCE_OF));
                        }
                    };
    }

    public static SimpleCrsShellBuilder<SimpleCrsShell.TemporalCrs, NameAndAnchorDatum.TemporalDatum> temporalCrs() {
            return new SimpleCrsShellBuilder<>(NameAndAnchorDatum.TemporalDatum.TEMPORAL_DATUM,
                WktKeyword.TIMECRS) {

                    @Override
                    public SimpleCrsShell.TemporalCrs build() {
                        return new SimpleCrsShell.TemporalCrs(first(), last(), index(), token(2), token(4), token(6),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.INSTANCE_OF),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
    }
}
