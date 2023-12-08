package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public final class Column {

    private final String name;

    private Column(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Column of(final String name) {
        return new Column(name);
    }
}
