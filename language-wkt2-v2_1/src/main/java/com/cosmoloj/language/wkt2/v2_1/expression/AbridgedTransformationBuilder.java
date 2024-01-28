package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
                : pb(ParameterAbridged.class, ParameterFile.class, Usage.class, Identifier.class, Remark.class);
        };
    }

    @Override
    public Operation.AbridgedTransformation build() {

        return new Operation.AbridgedTransformation(first(), last(), index(), token(2), token(4),
                tokens(AbstractParam.class::isInstance),
                tokens(Usage.class::isInstance),
                tokens(Identifier.class::isInstance),
                firstToken(Remark.class::isInstance));
    }
}
