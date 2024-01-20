package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;

/**
 *
 * @author Samuel Andrés
 */
public class AxisDirection extends AbstractExpression {

    private final EnumLexeme<Direction> type;
    private final Expression complement;

    public AxisDirection(final int start, final int end, final int index,
            final EnumLexeme<Direction> direction, final Expression complement) {
        super(start, end, index);
        this.type = direction;
        this.complement = complement;
    }

    public EnumLexeme<Direction> getType() {
        return type;
    }

    public Expression getComplement() {
        return complement;
    }
}
