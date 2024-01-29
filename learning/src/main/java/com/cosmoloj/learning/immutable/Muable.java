package com.cosmoloj.learning.immutable;

/**
 *
 * @author Samuel Andrés
 */
public class Muable implements Able {

    private int value;

    public Muable(final int v) {
        this.value = v;
    }

    @Override
    public int value() {
        return value;
    }

    public void value(final int v) {
        this.value = v;
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
        final Muable other = (Muable) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
}
