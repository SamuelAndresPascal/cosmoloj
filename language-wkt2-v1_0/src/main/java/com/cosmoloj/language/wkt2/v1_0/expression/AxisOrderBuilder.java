package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisOrderBuilder extends CheckTokenBuilder<Lexeme, AxisOrder>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(
                WktKeyword.ORDER,
                LeftDelimiter.class::isInstance,
                UnsignedInteger.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public AxisOrder build() {
        return new AxisOrder(first(), last(), index(), token(2));
    }
}
