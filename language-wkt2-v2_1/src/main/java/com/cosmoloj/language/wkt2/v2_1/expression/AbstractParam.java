package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AbstractParam extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof AbstractParam;

    private final QuotedLatinText name;
    private final List<Identifier> identifiers;

    public AbstractParam(final int start, final int end, final int index, final QuotedLatinText name,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return name;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }
}
