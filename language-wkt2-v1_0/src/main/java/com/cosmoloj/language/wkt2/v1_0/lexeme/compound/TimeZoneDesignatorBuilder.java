package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;utc designator&gt; ::= Z
 * &lt;local time zone designator&gt; ::= {&lt;plus sign&gt; | &lt;minus sign&gt;} &lt;hour&gt;
 * [&lt;COLON&gt; &lt;minute&gt;]
 * </pre>
 *
 * @author Samuel Andrés
 */
public class TimeZoneDesignatorBuilder extends LexemeSequenceLexemeBuilder<TimeZoneDesignator>
        implements PredicateListTokenBuilder<Lexeme>, ConstraintLastPredicateTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(SpecialSymbol.Z.or(SpecialSymbol.plus_sign).or(SpecialSymbol.minus_sign),
                UnsignedInteger.UNSIGNED_INTEGER,
                SpecialSymbol.COLON,
                UnsignedInteger.UNSIGNED_INTEGER);
    }

    @Override
    public TimeZoneDesignator build() {
        return new TimeZoneDesignator(codePoints(), first(), last(), index());
    }

    @Override
    public Predicate<? super Lexeme> constraintLast(final int currentIndex) {
        return currentIndex == 1 ? SpecialSymbol.plus_sign.or(SpecialSymbol.minus_sign) : t -> true;
    }
}
