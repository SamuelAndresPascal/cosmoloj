package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
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
public class AbridgedTransformationBuilder
        extends CheckTokenBuilder<Token, Operation.AbridgedTransformation>
        implements OperationBuilder<Operation.AbridgedTransformation, Method.OperationMethod, AbstractParam> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.ABRIDGEDTRANSFORMATION;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> Method.OperationMethod.INSTANCE_OF_OPERATION_METHOD;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA)
                       : ParameterAbridged.PARAMETER_ABRIDGED
                            .or(ParameterFile.INSTANCE_OF)
                            .or(Scope.INSTANCE_OF)
                            .or(Extent.class::isInstance)
                            .or(Identifier.INSTANCE_OF)
                            .or(Remark.INSTANCE_OF);
        };
    }

    @Override
    public Operation.AbridgedTransformation build() {

        return new Operation.AbridgedTransformation(first(), last(), index(), token(2), token(4),
                tokens(AbstractParam.INSTANCE_OF),
                firstToken(Scope.INSTANCE_OF),
                tokens(Extent.class::isInstance),
                tokens(Identifier.INSTANCE_OF),
                firstToken(Remark.INSTANCE_OF));
    }
}
