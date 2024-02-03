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
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> HorizontalCrs.HORIZONTAL_CRS;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> pb(SimpleCrsShell.VerticalCrs.class,
                    SimpleCrsShell.ParametricCrs.class,
                    SimpleCrsShell.TemporalCrs.class);
            case 7 -> pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
            case 8 -> pb(SimpleCrsShell.TemporalCrs.class, Scope.class, Extent.class, Identifier.class, Remark.class);
            default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                : pb(Scope.class, Extent.class, Identifier.class, Remark.class);
        };
    }

    @Override
    public CompoundCrs build() {
        return new CompoundCrs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                (size() >= 10 && testToken(8, SimpleCrsShell.TemporalCrs.class)) ? token(8) : null,
                firstToken(Scope.class),
                tokens(Extent.class),
                tokens(Identifier.class),
                firstToken(Remark.class));
    }
}
