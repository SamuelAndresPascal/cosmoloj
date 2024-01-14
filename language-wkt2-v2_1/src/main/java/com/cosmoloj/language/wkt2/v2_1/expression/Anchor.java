package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Anchor extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Anchor;

    private final QuotedLatinText description;

    public Anchor(final int start, final int end, final int index, final QuotedLatinText description) {
        super(start, end, index);
        this.description = description;
    }

    public QuotedLatinText getDescription() {
        return this.description;
    }
}
