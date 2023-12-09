package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;signed numeric literal&gt; ::= [&lt;sign&gt; ] &lt;unsigned numeric literal&gt;
 * </pre>
 *
 * @author Samuel Andrés
 */
public class SignedNumericLiteralBuilder extends LexemeSequenceLexemeBuilder<SignedNumericLiteral>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(UnsignedNumericLiteral.INSTANCE_OF.or(Sign.MINUS).or(Sign.PLUS),
                UnsignedNumericLiteral.INSTANCE_OF);
    }

    protected final boolean isDecimal() {
        return size() == 1 ? ((UnsignedNumericLiteral) token(0)).isDecimal()
                : ((UnsignedNumericLiteral) token(1)).isDecimal();
    }

    @Override
    public SignedNumericLiteral build() {
        return new SignedNumericLiteral(codePoints(), first(), last(), index(), isDecimal());
    }
}
