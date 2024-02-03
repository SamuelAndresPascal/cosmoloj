package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
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
public class PrimeMeridianBuilder extends CheckTokenBuilder<Token, PrimeMeridian>
        implements PredicateIndexTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.PRIMEM.or(WktKeyword.PRIMEMERIDIAN);
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> SignedNumericLiteral.class::isInstance;
            case 5 -> pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
            case 6 -> pb(Identifier.class, Unit.Angle.class);
            default -> {
                if (odd() && beyond(6)) {
                    yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                } else if (even() && beyond(7)) {
                    yield Identifier.class::isInstance;
                }
                yield Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return (even() && beyond(7)) ? SpecialSymbol.COMMA : Predicates.yes();
    }

    @Override
    public PrimeMeridian build() {
        return new PrimeMeridian(first(), last(), index(), token(2), token(4),
                firstToken(Unit.Angle.class),
                tokens(Identifier.class));
    }
}
