package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt.cts.lexeme.AxisDirectionName;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Axis extends AbstractExpression {

    private final QuotedName name;
    private final EnumLexeme<AxisDirectionName> direction;

    public Axis(final int start, final int end, final int index, final QuotedName name,
            final EnumLexeme<AxisDirectionName> direction) {
        super(start, end, index);
        this.name = name;
        this.direction = direction;
    }

    public QuotedName getName() {
        return this.name;
    }

    public EnumLexeme<AxisDirectionName> getDirection() {
        return this.direction;
    }
}
