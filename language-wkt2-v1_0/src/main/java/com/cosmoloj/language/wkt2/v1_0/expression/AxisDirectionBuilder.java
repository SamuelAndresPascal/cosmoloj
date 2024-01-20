package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.Direction;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisDirectionBuilder extends CheckTokenBuilder<Token, AxisDirection>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(Predicates.or(Direction.values()),
                SimpleNumber.Bearing.INSTANCE_OF.or(Meridian.INSTANCE_OF));
    }

    @Override
    public AxisDirection build() {
        return new AxisDirection(first(), last(), index(), token(0), token(1));
    }
}
