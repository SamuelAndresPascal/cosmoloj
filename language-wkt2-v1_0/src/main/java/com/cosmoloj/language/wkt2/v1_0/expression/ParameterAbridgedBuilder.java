package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
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
public class ParameterAbridgedBuilder extends CheckTokenBuilder<Token, ParameterAbridged>
        implements PredicateIndexTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.PARAMETER;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> SignedNumericLiteral.INSTANCE_OF;
            case 5 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
            case 6 -> Identifier.INSTANCE_OF;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA) : Identifier.INSTANCE_OF;
        };
    }

    @Override
    public ParameterAbridged build() {
        return new ParameterAbridged(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
    }
}
