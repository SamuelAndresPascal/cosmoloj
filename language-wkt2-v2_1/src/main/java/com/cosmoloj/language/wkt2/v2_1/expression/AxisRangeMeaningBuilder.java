package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.RangeMeaningType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisRangeMeaningBuilder extends CheckTokenBuilder<Lexeme, AxisRangeMeaning>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(
                WktKeyword.RANGEMEANING,
                LeftDelimiter.class::isInstance,
                Predicates.in(RangeMeaningType.class),
                RightDelimiter.class::isInstance);
    }

    @Override
    public AxisRangeMeaning build() {
        return new AxisRangeMeaning(first(), last(), index(), token(2));
    }
}
