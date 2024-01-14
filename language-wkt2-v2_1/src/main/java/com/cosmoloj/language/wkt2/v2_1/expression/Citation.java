package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Citation extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Citation;

    private final QuotedLatinText name;

    public Citation(final int start, final int end, final int index, final QuotedLatinText name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedLatinText getText() {
        return this.name;
    }
}
