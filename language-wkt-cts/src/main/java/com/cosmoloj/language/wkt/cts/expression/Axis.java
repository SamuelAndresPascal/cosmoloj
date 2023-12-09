package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.cts.lexeme.AxisDirectionName;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Axis extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Axis;

    private final QuotedName name;
    private final AxisDirectionName.Lexeme direction;

    public Axis(final int start, final int end, final int index, final QuotedName name,
            final AxisDirectionName.Lexeme direction) {
        super(start, end, index);
        this.name = name;
        this.direction = direction;
    }

    public QuotedName getName() {
        return this.name;
    }

    public AxisDirectionName.Lexeme getDirection() {
        return this.direction;
    }
}
