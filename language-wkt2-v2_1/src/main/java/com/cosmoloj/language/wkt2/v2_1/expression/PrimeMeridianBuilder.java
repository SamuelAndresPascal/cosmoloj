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
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.comma;
            case 4 -> SignedNumericLiteral.INSTANCE_OF;
            case 5 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
            case 6 -> Identifier.INSTANCE_OF.or(Unit.Angle.INSTANCE_OF_ANGLE);
            default -> {
                if (odd() && beyond(6)) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                } else if (even() && beyond(7)) {
                    yield Identifier.INSTANCE_OF;
                }
                yield t -> false;
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return (even() && beyond(7)) ? SpecialSymbol.comma : t -> true;
    }

    @Override
    public PrimeMeridian build() {
        return new PrimeMeridian(first(), last(), index(), token(2), token(4),
                firstToken(Unit.Angle.INSTANCE_OF_ANGLE),
                tokens(Identifier.INSTANCE_OF));
    }
}
