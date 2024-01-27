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
            case 6 -> SimpleCrsShell.VerticalCrs.INSTANCE_OF
                    .or(SimpleCrsShell.ParametricCrs.class::isInstance)
                    .or(SimpleCrsShell.TemporalCrs.class::isInstance);
            case 7 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
            case 8 -> SimpleCrsShell.TemporalCrs.INSTANCE_OF
                    .or(Scope.class::isInstance)
                    .or(Extent.class::isInstance)
                    .or(Identifier.class::isInstance)
                    .or(Remark.class::isInstance);
            default -> {
                if (odd()) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else {
                    yield Predicates.of(Scope.class::isInstance)
                    .or(Extent.class::isInstance)
                    .or(Identifier.class::isInstance)
                    .or(Remark.class::isInstance);
                }
            }
        };
    }

    @Override
    public CompoundCrs build() {
        return new CompoundCrs(first(), last(), index(), token(2), token(4), token(6),
                (size() >= 10 && testToken(8, SimpleCrsShell.TemporalCrs.class::isInstance)) ? token(8) : null,
                firstToken(Scope.class::isInstance),
                tokens(Extent.class::isInstance),
                tokens(Identifier.class::isInstance),
                firstToken(Remark.class::isInstance));
    }
}
