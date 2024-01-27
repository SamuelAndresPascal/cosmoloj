package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
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
            case 4 -> OperationCrs.SourceCrs.class::isInstance;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> OperationCrs.TargetCrs.class::isInstance;
            case 7 -> SpecialSymbol.COMMA;
            case 8 -> Method.OperationMethod.class::isInstance;
            default -> odd() ? Predicates.of(RightDelimiter.class::isInstance).or(SpecialSymbol.COMMA)
                : Predicates.of(Parameter.class::isInstance)
                            .or(ParameterFile.class::isInstance)
                            .or(SimpleNumber.Accuracy.class::isInstance)
                            .or(OperationCrs.InterpolationCrs.class::isInstance)
                            .or(SimpleNumber.Accuracy.class::isInstance)
                            .or(Usage.class::isInstance)
                            .or(Identifier.class::isInstance)
                            .or(Remark.class::isInstance);
        };
    }

    @Override
    public Operation.CoordinateOperation build() {

        return new Operation.CoordinateOperation(first(), last(), index(), token(2), token(4), token(6), token(8),
                tokens(AbstractParam.class::isInstance),
                firstToken(OperationCrs.InterpolationCrs.class::isInstance),
                firstToken(SimpleNumber.Accuracy.class::isInstance),
                tokens(Usage.class::isInstance),
                tokens(Identifier.class::isInstance),
                firstToken(Remark.class::isInstance));
    }
}
