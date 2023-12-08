package com.cosmoloj.util.sql;

import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Samuel Andrés
 */
public interface QueryExpression extends Consumer<QueryBuilder> {

    @Override
    default QueryExpression andThen(final Consumer<? super QueryBuilder> after) {
        Objects.requireNonNull(after);
        return (QueryBuilder t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
