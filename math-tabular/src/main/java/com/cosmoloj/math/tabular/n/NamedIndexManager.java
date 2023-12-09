package com.cosmoloj.math.tabular.n;

import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
class NamedIndexManager {

    private final char[] indices;

    NamedIndexManager(final NamedIndex[] vindices) {
        indices = new char[vindices.length];
        for (int i = 0; i < vindices.length; i++) {
            indices[i] = vindices[i].getName();
        }
    }

    public int getPosition(final char index) {
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == index) {
                return i;
            }
        }
        return -1;
    }

    public char getIndex(final int order) {
        return indices[order];
    }

    public char[] getIndexes() {
        return Arrays.copyOf(indices, indices.length);
    }

    public int[] component(final NamedIndex... precomp) {

        final int[] component = new int[precomp.length];
        for (int i = 0; i < indices.length; i++) {
            for (final NamedIndex id : precomp) {
                if (id.getName() == indices[i]) {
                    component[i] = id.getDimension();
                    break;
                }
            }
        }
        return component;
    }
}
