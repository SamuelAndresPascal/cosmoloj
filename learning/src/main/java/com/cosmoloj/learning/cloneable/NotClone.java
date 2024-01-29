package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class NotClone {

    private final String name;

    public NotClone(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public NotClone clone() throws CloneNotSupportedException {
        final NotClone clone = (NotClone) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "NotCloneable{" + "name=" + name + '}';
    }
}
