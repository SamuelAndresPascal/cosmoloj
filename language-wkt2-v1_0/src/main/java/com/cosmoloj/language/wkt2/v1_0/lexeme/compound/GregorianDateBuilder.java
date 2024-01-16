package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;gregorian calendar date&gt; ::= &lt;year&gt; [&lt;hyphen&gt; &lt;month&gt; [&lt;hyphen&gt; &lt;day&gt;]]
 * &lt;gregorian ordinal date&gt; ::= &lt;year&gt; [&lt;hyphen&gt; &lt;ordinal day&gt;]
 * </pre>
 *
 * @author Samuel Andrés
 */
public class GregorianDateBuilder extends LexemeSequenceLexemeBuilder<GregorianDate>
        implements PredicateIndexTokenBuilder<Lexeme> {

    @Override
    public Predicate<? super Lexeme> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> UnsignedInteger.UNSIGNED_INTEGER;
            default -> {
                if (even()) {
                    yield UnsignedInteger.UNSIGNED_INTEGER;
                } else if (below(5)) {
                    yield SpecialSymbol.MINUS_SIGN;
                }
                yield t -> false;
            }
        };
    }

    @Override
    public GregorianDate build() {
        return new GregorianDate(codePoints(), first(), last(), index());
    }
}
