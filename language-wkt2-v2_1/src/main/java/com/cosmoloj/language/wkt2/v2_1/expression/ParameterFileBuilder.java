package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
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
public class ParameterFileBuilder extends CheckTokenBuilder<Token, ParameterFile>
        implements PredicateIndexTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.PARAMETERFILE;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.comma;
            case 4 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 5 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
            case 6 -> Identifier.INSTANCE_OF;
            default -> {
                if (odd()) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                } else {
                    yield Identifier.INSTANCE_OF;
                }
            }
        };
    }

    @Override
    public ParameterFile build() {
        return new ParameterFile(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
    }
}