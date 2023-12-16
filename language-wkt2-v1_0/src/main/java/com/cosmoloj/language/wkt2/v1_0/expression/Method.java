package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Method extends AbstractExpression {

    private final QuotedLatinText name;
    private final List<Identifier> identifiers;

    public Method(final int start, final int end, final int index, final QuotedLatinText name,
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

        public static final Predicate<Object> INSTANCE_OF_MAP_PROJECTION_METHOD = t -> t instanceof MapProjectionMethod;

        public MapProjectionMethod(final int start, final int end, final int index, final QuotedLatinText name,
                final List<Identifier> identifiers) {
            super(start, end, index, name, identifiers);
        }
    }

    public static class OperationMethod extends Method {

        public static final Predicate<Object> INSTANCE_OF_OPERATION_METHOD = t -> t instanceof OperationMethod;

        public OperationMethod(final int start, final int end, final int index, final QuotedLatinText name,
                final List<Identifier> identifiers) {
            super(start, end, index, name, identifiers);
        }
    }
}
