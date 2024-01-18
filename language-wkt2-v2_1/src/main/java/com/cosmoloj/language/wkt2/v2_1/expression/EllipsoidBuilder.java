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
public class EllipsoidBuilder extends CheckTokenBuilder<Token, Ellipsoid> implements PredicateIndexTokenBuilder<Token>,
        ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.ELLIPSOID.or(WktKeyword.SPHEROID);
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3, 5 -> SpecialSymbol.COMMA;
            case 4, 6 -> SignedNumericLiteral.INSTANCE_OF;
            case 7 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
            case 8 -> Identifier.INSTANCE_OF.or(Unit.Length.INSTANCE_OF_LENGTH);
            default -> {
                if (odd() && beyond(8)) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else if (even() && beyond(9)) {
                    yield Identifier.INSTANCE_OF;
                }
                yield t -> false;
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return (even() && beyond(9)) ? SpecialSymbol.COMMA : t -> true;
    }

    @Override
    public Ellipsoid build() {

        return new Ellipsoid(first(), last(), index(), token(0), token(2), token(4), token(6),
                (size() >= 10 && testToken(8, Unit.Length.INSTANCE_OF_LENGTH)) ? token(8) : null,
                tokens(Identifier.INSTANCE_OF));
    }
}
