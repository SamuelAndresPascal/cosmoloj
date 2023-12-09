package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public final class IndexFactory {

    private static IndexFactory INSTANCE;

    private IndexFactory() {
    }

    public static IndexFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IndexFactory();
        }
        return INSTANCE;
    }

    public VariantIndex get(final char index, final int size, final VariantTabularN.Variant variant) {
        return new VariantIndex(index, size, variant);
    }

    public NamedIndex get(final char index, final int size) {
        return new NamedIndex(index, size);
    }
}
