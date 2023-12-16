package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Uri extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Uri;

    private final QuotedLatinText name;

    public Uri(final int start, final int end, final int index, final QuotedLatinText name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedLatinText getValue() {
        return this.name;
    }
}
