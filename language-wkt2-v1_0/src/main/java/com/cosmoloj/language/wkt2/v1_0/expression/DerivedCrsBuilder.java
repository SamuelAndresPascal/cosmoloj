package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <CRS>
 * @param <B>
 * @param <O>
 * @param <M>
 * @param <P>
 */
public abstract class DerivedCrsBuilder<CRS extends DerivedCrs<B, O, M, P>,
        B extends BaseCrs, O extends Operation<M, P>, M extends Method, P extends AbstractParam>
        extends CheckTokenBuilder<Token, CRS>
        implements PredicateIndexTokenBuilder<Token> {

    private final Predicate<? super Token> basePredicate;
    private final Predicate<? super Token> operationPredicate;
    private final Predicate<? super Token> labelPredicate;

    protected DerivedCrsBuilder(final Predicate<? super Token> baseCrsType,
            final Predicate<? super Token> operationType, final WktKeyword... labels) {
        this.basePredicate = baseCrsType;
        this.operationPredicate = operationType;
        Predicate<? super Token> l = labels[0];
        for (int i = 1; i < labels.length; i++) {
            l = l.or(labels[i]);
        }
        this.labelPredicate = l;
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> labelPredicate;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> basePredicate;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> operationPredicate;
            case 7 -> SpecialSymbol.COMMA;
            case 8 -> CoordinateSystem.class::isInstance;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA)
                       : Scope.INSTANCE_OF
                        .or(Extent.class::isInstance)
                        .or(Identifier.INSTANCE_OF)
                        .or(Remark.INSTANCE_OF);
        };
    }

    public static DerivedCrsBuilder<DerivedCrs.ProjectedCrs, BaseGeodeticCrs, Operation.MapProjection,
            Method.MapProjectionMethod, Parameter> projectedCrs() {

                return new DerivedCrsBuilder<>(BaseGeodeticCrs.INSTANCE_OF,
                        Operation.MapProjection.INSTANCE_OF_MAP_PROJECTION,
                        WktKeyword.PROJCRS, WktKeyword.PROJECTEDCRS, WktKeyword.PROJCS) {
                    @Override
                    public DerivedCrs.ProjectedCrs build() {

                        return new DerivedCrs.ProjectedCrs(first(), last(), index(), token(2), token(4), token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }

    public static DerivedCrsBuilder<DerivedCrs.DerivedGeodeticCrs, BaseGeodeticCrs, Operation.DerivingConversion,
            Method.OperationMethod, AbstractParam> derivedGeodeticCrs() {
                return new DerivedCrsBuilder<>(BaseGeodeticCrs.INSTANCE_OF,
                        Operation.DerivingConversion.INSTANCE_OF_DERIVING_CONVERSION,
                        WktKeyword.GEODCRS, WktKeyword.GEODETICCRS, WktKeyword.GEOCCS, WktKeyword.GEOGCS) {

                    @Override
                    public DerivedCrs.DerivedGeodeticCrs build() {

                        return new DerivedCrs.DerivedGeodeticCrs(first(), last(), index(), token(2), token(4), token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }

    public static DerivedCrsBuilder<DerivedCrs.DerivedVerticalCrs, BaseCrs.BaseVerticalCrs,
            Operation.DerivingConversion, Method.OperationMethod, AbstractParam> derivedVerticalCrs() {
                return new DerivedCrsBuilder<>(BaseCrs.BaseVerticalCrs.BASE_VERTICAL_CRS,
                        Operation.DerivingConversion.INSTANCE_OF_DERIVING_CONVERSION,
                        WktKeyword.VERTCRS, WktKeyword.VERTICALCRS, WktKeyword.VERT_CS) {

                    @Override
                    public DerivedCrs.DerivedVerticalCrs build() {

                        return new DerivedCrs.DerivedVerticalCrs(first(), last(), index(), token(2), token(4), token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }

    public static <B extends BaseCrs> DerivedCrsBuilder<DerivedCrs.DerivedEngineeringCrs<B>, B,
            Operation.DerivingConversion, Method.OperationMethod, AbstractParam> derivedEngineeringCrs(
                    final Predicate<? super Token> baseCrsType) {
                return new DerivedCrsBuilder<>(baseCrsType,
                        Operation.DerivingConversion.INSTANCE_OF_DERIVING_CONVERSION,
                        WktKeyword.ENGCRS, WktKeyword.ENGINEERINGCRS, WktKeyword.LOCAL_CS) {

                    @Override
                    public DerivedCrs.DerivedEngineeringCrs<B> build() {

                        return new DerivedCrs.DerivedEngineeringCrs<>(first(), last(), index(), token(2), token(4),
                                token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }

    public static DerivedCrsBuilder<DerivedCrs.DerivedParametricCrs, BaseCrs.BaseParametricCrs,
            Operation.DerivingConversion, Method.OperationMethod, AbstractParam> derivedParametricCrs() {
                return new DerivedCrsBuilder<>(BaseCrs.BaseParametricCrs.BASE_PARAMETRIC_CRS,
                        Operation.DerivingConversion.INSTANCE_OF_DERIVING_CONVERSION,
                        WktKeyword.PARAMETRICCRS) {

                    @Override
                    public DerivedCrs.DerivedParametricCrs build() {

                        return new DerivedCrs.DerivedParametricCrs(first(), last(), index(), token(2), token(4),
                                token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }

    public static DerivedCrsBuilder<DerivedCrs.DerivedTemporalCrs, BaseCrs.BaseTemporalCrs,
            Operation.DerivingConversion, Method.OperationMethod, AbstractParam> derivedTemporalCrs() {
                return new DerivedCrsBuilder<>(BaseCrs.BaseTemporalCrs.BASE_TEMPORAL_CRS,
                        Operation.DerivingConversion.INSTANCE_OF_DERIVING_CONVERSION,
                        WktKeyword.TIMECRS) {

                    @Override
                    public DerivedCrs.DerivedTemporalCrs build() {

                        return new DerivedCrs.DerivedTemporalCrs(first(), last(), index(), token(2), token(4), token(6),
                                token(8),
                                firstToken(Scope.INSTANCE_OF),
                                tokens(Extent.class::isInstance),
                                tokens(Identifier.INSTANCE_OF),
                                firstToken(Remark.INSTANCE_OF));
                    }
                };
            }
}
