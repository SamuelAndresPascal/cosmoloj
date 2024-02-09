package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisRangeBuilder extends CheckTokenBuilder<Token, AxisRange>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(
                pb(AxisMinimumValue.class, AxisMaximumValue.class, AxisRangeMeaning.class),
                SpecialSymbol.COMMA,
                pb(AxisMaximumValue.class, AxisRangeMeaning.class),
                SpecialSymbol.COMMA,
                AxisRangeMeaning.class::isInstance);
    }

    @Override
    public AxisRange build() {
        return new AxisRange(first(), last(), index(),
                firstToken(AxisMinimumValue.class),
                firstToken(AxisMaximumValue.class),
                firstToken(AxisRangeMeaning.class));
    }
}
