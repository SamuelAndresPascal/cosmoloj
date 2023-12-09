package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.ExponentSeparator;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 *
 * @author Samuel Andrés
 */
public class ApproximateNumericLiteralBuilder extends LexemeSequenceLexemeBuilder<ApproximateNumericLiteral>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(ExactNumericLiteral.EXACT_NUMERIC_LITERAL,
                ExponentSeparator.EXPONENT_SEPARATOR,
                SignedInteger.SIGNED_INTEGER);
    }

    @Override
    public ApproximateNumericLiteral build() {
        return new ApproximateNumericLiteral(codePoints(), first(), last(), index());
    }
}
