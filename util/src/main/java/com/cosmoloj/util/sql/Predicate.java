package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public record Predicate(String field, Operator op, Object value) {

    public String toSql() {
        if (value instanceof CharSequence || value instanceof Character) {
            return field + " " + op.sql() + " " + '\'' + value + '\'';
        } else if (value instanceof Column col) {
            return field + " " + op.sql() + " " + col.getName();
        } else {
            return field + " " + op.sql() + " " + value;
        }
    }
}
