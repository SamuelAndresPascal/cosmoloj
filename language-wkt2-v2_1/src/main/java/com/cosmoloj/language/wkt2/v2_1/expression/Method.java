package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Method extends AbstractExpression {

    private final QuotedLatinText name;
    private final List<Identifier> identifiers;

    protected Method(final int start, final int end, final int index, final QuotedLatinText name,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public static class MapProjectionMethod extends Method {

        public MapProjectionMethod(final int start, final int end, final int index, final QuotedLatinText name,
                final List<Identifier> identifiers) {
            super(start, end, index, name, identifiers);
        }
    }

    public static class OperationMethod extends Method {

        public OperationMethod(final int start, final int end, final int index, final QuotedLatinText name,
                final List<Identifier> identifiers) {
            super(start, end, index, name, identifiers);
        }
    }
}
