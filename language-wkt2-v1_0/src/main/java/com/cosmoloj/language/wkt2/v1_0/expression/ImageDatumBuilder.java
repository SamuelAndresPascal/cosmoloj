package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ImageDatumBuilder extends CheckTokenBuilder<Token, ImageDatum>
        implements PredicateIndexTokenBuilder<Token>, ConstraintBeforePredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.IDATUM.or(WktKeyword.IMAGEDATUM);
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> PixelInCell.Lexeme.class::isInstance;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA)
                       : Identifier.INSTANCE_OF.or(Anchor.INSTANCE_OF);
        };
    }

    @Override
    public Predicate<? super Token> constraintBefore(final int before) {
        if (even() && beyond(4)) {
            return switch (before) {
                case 1 -> SpecialSymbol.COMMA;
                case 2 -> current(Anchor.INSTANCE_OF) ? PixelInCell.Lexeme.class::isInstance : t -> true;
                default -> t -> true;
            };
        }
        return t -> true;
    }

    @Override
    public ImageDatum build() {

        return new ImageDatum(first(), last(), index(), token(2), token(4),
                (size() >= 8 && testToken(6, Anchor.INSTANCE_OF)) ? token(6) : null,
                tokens(Identifier.INSTANCE_OF));
    }
}
