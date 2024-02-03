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
            case 4 -> Method.OperationMethod.class::isInstance;
            default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                       : pb(ParameterAbridged.class,
                               ParameterFile.class,
                               Scope.class,
                               Extent.class,
                               Identifier.class,
                               Remark.class);
        };
    }

    @Override
    public Operation.AbridgedTransformation build() {

        return new Operation.AbridgedTransformation(first(), last(), index(), token(2), token(4),
                tokens(AbstractParam.class),
                firstToken(Scope.class),
                tokens(Extent.class),
                tokens(Identifier.class),
                firstToken(Remark.class));
    }
}
