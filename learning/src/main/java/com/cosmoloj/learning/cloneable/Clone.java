package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class Clone implements Cloneable {

    private final String name;

    public Clone(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Clone clone() throws CloneNotSupportedException {
        final Clone clone = (Clone) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Clone{" + "name=" + name + '}';
    }
}
