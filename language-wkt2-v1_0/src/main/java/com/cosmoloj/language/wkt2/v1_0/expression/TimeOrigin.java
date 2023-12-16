package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class TimeOrigin extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof TimeOrigin;

    private final Lexeme description;

    public TimeOrigin(final int start, final int end, final int index, final Lexeme description) {
        super(start, end, index);
        this.description = description;
    }

    public Lexeme getDescription() {
        return this.description;
    }
}
