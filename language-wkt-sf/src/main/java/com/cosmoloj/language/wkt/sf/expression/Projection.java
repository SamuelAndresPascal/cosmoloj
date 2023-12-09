package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Projection extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Projection;

    private final QuotedName name;

    public Projection(final int start, final int end, final int index, final QuotedName name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedName getName() {
        return this.name;
    }
}
