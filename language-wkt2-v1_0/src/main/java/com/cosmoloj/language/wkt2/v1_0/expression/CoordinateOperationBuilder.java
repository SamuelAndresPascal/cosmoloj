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
 */
public class CoordinateOperationBuilder
        extends CheckTokenBuilder<Token, Operation.CoordinateOperation>
        implements PredicateIndexTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.COORDINATEOPERATION;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> OperationCrs.SourceCrs.INSTANCE_OF_SOURCE_CRS;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> OperationCrs.TargetCrs.INSTANCE_OF_TARGET_CRS;
            case 7 -> SpecialSymbol.COMMA;
            case 8 -> Method.OperationMethod.INSTANCE_OF_OPERATION_METHOD;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA)
                       : Parameter.INSTANCE_OF
                            .or(ParameterFile.INSTANCE_OF)
                            .or(SimpleNumber.Accuracy.INSTANCE_OF)
                            .or(OperationCrs.InterpolationCrs.INSTANCE_OF_INTERPOLATION_CRS)
                            .or(SimpleNumber.Accuracy.INSTANCE_OF)
                            .or(Scope.INSTANCE_OF)
                            .or(Extent.INSTANCE_OF)
                            .or(Identifier.class::isInstance)
                            .or(Remark.INSTANCE_OF);
        };
    }

    @Override
    public Operation.CoordinateOperation build() {

        return new Operation.CoordinateOperation(first(), last(), index(), token(2), token(4), token(6), token(8),
                tokens(AbstractParam.INSTANCE_OF),
                firstToken(OperationCrs.InterpolationCrs.INSTANCE_OF_INTERPOLATION_CRS),
                firstToken(SimpleNumber.Accuracy.INSTANCE_OF),
                firstToken(Scope.INSTANCE_OF),
                tokens(Extent.INSTANCE_OF),
                tokens(Identifier.class::isInstance),
                firstToken(Remark.INSTANCE_OF));
    }
}
