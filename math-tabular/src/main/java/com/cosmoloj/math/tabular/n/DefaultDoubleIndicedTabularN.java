package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleIndicedTabularN extends DefaultDoubleTabularN implements DoubleIndicedTabularN {

    private final NamedIndexManager indexManager;

    public DefaultDoubleIndicedTabularN(final Object tabular, final NamedIndex... vindices) {
        super(tabular, indicesToDimensions(vindices));
        this.indexManager = new NamedIndexManager(vindices);
    }

    protected static int[] indicesToDimensions(final NamedIndex[] vindices) {
        final int[] dimensions = new int[vindices.length];
        for (int i = 0; i < vindices.length; i++) {
            dimensions[i] = vindices[i].getDimension();
        }
        return dimensions;
    }

    @Override
    public double get(final NamedIndex... precomp) {
        return get(indexManager.component(precomp));
    }

    @Override
    public int getDimension(final char index) {
        final int position = getPosition(index);
        if (position >= 0) {
            return getDimension(position);
        }
        return 0;
    }

    @Override
    public int getPosition(final char index) {
        return indexManager.getPosition(index);
    }

    @Override
    public char getIndex(final int order) {
        return indexManager.getIndex(order);
    }

    @Override
    public char[] getIndexes() {
        return indexManager.getIndexes();
    }
}
