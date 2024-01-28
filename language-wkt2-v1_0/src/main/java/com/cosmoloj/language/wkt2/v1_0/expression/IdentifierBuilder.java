package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class IdentifierBuilder extends CheckTokenBuilder<Token, Identifier>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.ID.or(WktKeyword.AUTHORITY),
                LeftDelimiter.class::isInstance,
                QuotedLatinText.class::isInstance, // name
                pb(RightDelimiter.class).or(SpecialSymbol.COMMA),
                pb(QuotedLatinText.class, SignedNumericLiteral.class), // identifier
                pb(RightDelimiter.class).or(SpecialSymbol.COMMA),
                pb(Citation.class, Uri.class, SignedNumericLiteral.class, QuotedLatinText.class),
                pb(RightDelimiter.class).or(SpecialSymbol.COMMA),
                pb(Citation.class, Uri.class),
                pb(RightDelimiter.class).or(SpecialSymbol.COMMA),
                Uri.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return currentIndex == 6 || currentIndex == 8 || currentIndex == 10 ? SpecialSymbol.COMMA : Predicates.yes();
    }

    @Override
    public Identifier build() {

        final int size = size();

        final Uri uri;
        if (size == 12) {
            uri = (Uri) token(10);
        } else if (size == 10 && testToken(8, Uri.class::isInstance)) {
            uri = (Uri) token(8);
        } else if (size == 8 && testToken(6, Uri.class::isInstance)) {
            uri = (Uri) token(6);
        } else {
            uri = null;
        }

        final Citation citation;
        if (size == 12) {
            citation = (Citation) token(8);
        } else if (size == 10) {
            if (testToken(8, Citation.class::isInstance)) {
                citation = (Citation) token(8);
            } else if (testToken(6, Citation.class::isInstance)) {
                citation = (Citation) token(6);
            } else {
                citation = null;
            }
        } else if (size == 8 && testToken(6, Citation.class::isInstance)) {
            citation = (Citation) token(6);
        } else {
            citation = null;
        }

        final Lexeme version;
        if (size >= 8 && (token(6) instanceof SignedNumericLiteral || token(6) instanceof QuotedLatinText)) {
            version = (Lexeme) token(6);
        } else {
            version = null;
        }

        return new Identifier<>(first(), last(), index(), token(2), token(4), version, citation, uri);
    }
}
