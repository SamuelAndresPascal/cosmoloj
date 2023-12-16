package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 *
 * @author Samuel Andrés
 */
public class DatetimeBuilder extends LexemeSequenceLexemeBuilder<Datetime>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(GregorianDate.INSTANCE_OF, Clock.INSTANCE_OF);
    }

    @Override
    public Datetime build() {
        return new Datetime(codePoints(), first(), last(), index(), size() != 1);
    }
}
