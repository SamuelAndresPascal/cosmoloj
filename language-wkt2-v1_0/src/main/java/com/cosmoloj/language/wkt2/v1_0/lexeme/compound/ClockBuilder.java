package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;date value&gt; ::= &lt;years value&gt; &lt;minus sign&gt; &lt;months value&gt; &lt;minus sign&gt;
 * &lt;days value&gt;
 * </pre>
 *
 * @author Samuel Andrés
 */
public class ClockBuilder extends LexemeSequenceLexemeBuilder<Clock> implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(SpecialSymbol.T,
                UnsignedInteger.class::isInstance,
                pb(TimeZoneDesignator.class).or(SpecialSymbol.COLON),
                UnsignedInteger.class::isInstance,
                pb(TimeZoneDesignator.class).or(SpecialSymbol.COLON),
                ExactNumericLiteral.class::isInstance,
                TimeZoneDesignator.class::isInstance);
    }

    @Override
    public Clock build() {

        return new Clock(codePoints(), first(), last(), index());
    }
}
