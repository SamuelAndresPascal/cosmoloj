package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Authority extends AbstractExpression {

    private final QuotedName name;
    private final QuotedName code;

    public Authority(final int start, final int end, final int index,
            final QuotedName name, final QuotedName code) {
        super(start, end, index);
        this.name = name;
        this.code = code;
    }

    public QuotedName getName() {
        return this.name;
    }

    public QuotedName getCode() {
        return this.code;
    }
}
