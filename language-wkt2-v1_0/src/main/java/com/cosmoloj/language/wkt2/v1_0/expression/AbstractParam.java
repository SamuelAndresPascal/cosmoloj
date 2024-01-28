package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class AbstractParam extends AbstractExpression {

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
