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
public class CompoundCrsBuilder extends CheckTokenBuilder<Token, CompoundCrs>
        implements PredicateIndexTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.COMPOUNDCRS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> HorizontalCrs.HORIZONTAL_CRS;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> SimpleCrsShell.VerticalCrs.INSTANCE_OF
                    .or(SimpleCrsShell.ParametricCrs.INSTANCE_OF)
                    .or(SimpleCrsShell.TemporalCrs.INSTANCE_OF);
            case 7 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
            case 8 -> SimpleCrsShell.TemporalCrs.INSTANCE_OF
                    .or(Scope.INSTANCE_OF)
                    .or(Extent.INSTANCE_OF)
                    .or(Identifier.INSTANCE_OF)
                    .or(Remark.INSTANCE_OF);
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

    @Override
    public CompoundCrs build() {
        return new CompoundCrs(first(), last(), index(), token(2), token(4), token(6),
                (size() >= 10 && testToken(8, SimpleCrsShell.TemporalCrs.INSTANCE_OF)) ? token(8) : null,
                firstToken(Scope.INSTANCE_OF),
                tokens(Extent.INSTANCE_OF),
                tokens(Identifier.INSTANCE_OF),
                firstToken(Remark.INSTANCE_OF));
    }
}
