package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;signed integer&gt; ::= [ &lt;sign&gt; ] &lt;unsigned integer&gt;
 * </pre>
 *
 * @author Samuel Andrés
 */
public class SignedIntegerBuilder extends LexemeSequenceLexemeBuilder<SignedInteger>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(pb(UnsignedInteger.class).or(Sign.PLUS).or(Sign.MINUS),
                UnsignedInteger.class::isInstance);
    }

    @Override
    public SignedInteger build() {
        return new SignedInteger(codePoints(), first(), last(), index());
    }
}
