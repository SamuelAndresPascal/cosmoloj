package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.Direction;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisDirection extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof AxisDirection;

    private final Direction.Lexeme type;
    private final Expression complement;

    public AxisDirection(final int start, final int end, final int index,
            final Direction.Lexeme direction, final Expression complement) {
        super(start, end, index);
        this.type = direction;
        this.complement = complement;
    }

    public Direction.Lexeme getType() {
        return type;
    }

    public Expression getComplement() {
        return complement;
    }
}
