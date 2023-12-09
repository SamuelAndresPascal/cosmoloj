package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public class VariantIndex extends NamedIndex {

    private final VariantTabularN.Variant variant;

    public VariantIndex(final char index, final int dimension, final VariantTabularN.Variant variant) {
        super(index, dimension);
        this.variant = variant;
    }

    public VariantTabularN.Variant getVariant() {
        return variant;
    }

    @Override
    public String toString() {
        return super.toString() + " " + variant;
    }

}
