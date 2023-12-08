package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public enum Operator {
    EQ("="), LIKE("like"), GT(">"), GE(">="), LT("<"), LE("<="), NOT("not"), NOT_LIKE("not like"), NOT_EQ("<>");

    private final String sql;

    Operator(final String sql) {
        this.sql = sql;
    }

    public String sql() {
        return this.sql;
    }

    public Predicate predicate(final String field, final Object value) {
        return new Predicate(field, this, value);
    }
}
