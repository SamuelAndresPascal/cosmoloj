package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Datum extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Datum;

    private final QuotedName name;
    private final Spheroid spheroid;

    public Datum(final int start, final int end, final int index, final QuotedName name,
            final Spheroid spheroid) {
        super(start, end, index);
        this.name = name;
        this.spheroid = spheroid;
    }

    public QuotedName getName() {
        return this.name;
    }

    public Spheroid getSpheroid() {
        return this.spheroid;
    }
}
