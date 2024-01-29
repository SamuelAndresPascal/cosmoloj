package com.cosmoloj.learning.immutable;

/**
 *
 * @author Samuel Andrés
 */
public class Immuable implements Able {

    private final int value;

    public Immuable(final int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.value;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Immuable other = (Immuable) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
}
