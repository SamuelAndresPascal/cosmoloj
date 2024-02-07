package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.RangeMeaningType;

/**
 *
 * @author Samuel Andrés
 */
public class AxisRangeMeaning extends AbstractExpression {

    private final EnumLexeme<RangeMeaningType> type;

    protected AxisRangeMeaning(final int start, final int end, final int index,
            final EnumLexeme<RangeMeaningType> type) {
        super(start, end, index);
        this.type = type;
    }

    public EnumLexeme<RangeMeaningType> getType() {
        return type;
    }
}
