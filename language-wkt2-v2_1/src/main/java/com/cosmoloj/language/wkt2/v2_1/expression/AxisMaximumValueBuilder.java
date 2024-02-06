package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisMaximumValueBuilder extends CheckTokenBuilder<Lexeme, AxisMaximumValue>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(
                WktKeyword.AXISMINVALUE.or(WktKeyword.AXISMAXVALUE),
                LeftDelimiter.class::isInstance,
                SignedNumericLiteral.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public AxisMaximumValue build() {
        return new AxisMaximumValue(first(), last(), index(), token(2));
    }
}
