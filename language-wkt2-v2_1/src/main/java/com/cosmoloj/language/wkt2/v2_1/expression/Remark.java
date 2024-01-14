package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedUnicodeText;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Remark extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Remark;

    private final QuotedUnicodeText text;

    public Remark(final int start, final int end, final int index, final QuotedUnicodeText text) {
        super(start, end, index);
        this.text = text;
    }

    public QuotedUnicodeText getText() {
        return this.text;
    }
}
