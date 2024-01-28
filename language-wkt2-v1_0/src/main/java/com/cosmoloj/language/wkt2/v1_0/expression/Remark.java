package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedUnicodeText;

/**
 *
 * @author Samuel Andrés
 */
public class Remark extends AbstractExpression {

    private final QuotedUnicodeText text;

    public Remark(final int start, final int end, final int index, final QuotedUnicodeText text) {
        super(start, end, index);
        this.text = text;
    }

    public QuotedUnicodeText getText() {
        return this.text;
    }
}
