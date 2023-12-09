package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public class NamedIndex {

    private final char name;
    private int dimension;

    public NamedIndex(final char index, final int dimension) {
        this.name = index;
        this.dimension = dimension;
    }

    public char getName() {
        return this.name;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(final int dim) {
        this.dimension = dim;
    }

    @Override
    public String toString() {
        return name + " (" + dimension + ")";
    }

    /**
     *
     * @param idxs <span class="fr">tableau d'index nommés</span>
     * @return <span class="fr">les dimensions d'une suite d'indices nommés</span>
     */
    public static int[] getDimensions(final NamedIndex[] idxs) {
        final int[] result = new int[idxs.length];
        for (int i = 0; i < idxs.length; i++) {
            result[i] = idxs[i].dimension;
        }
        return result;
    }

    /**
     *
     * @param idxs <span class="fr">tableau d'index nommés</span>
     * @return <span class="fr">retourne les noms d'une suite d'indices nommés</span>
     */
    public static char[] getNames(final NamedIndex[] idxs) {
        final char[] result = new char[idxs.length];
        for (int i = 0; i < idxs.length; i++) {
            result[i] = idxs[i].name;
        }
        return result;
    }

    /**
     * <span class="fr">Recherche les positions d'une suite de noms d'indices dans un tableau</span>
     * @param toSearch <span class="fr">tableau des index dont on souhaite connaître la position</span>
     * @param toProcess <span class="fr">tablea des index dans lequel on souhaite connaître leur position</span>
     * @return <span class="fr">positions</span>
     */
    public static int[] getPositions(final char[] toSearch, final char[] toProcess) {
        final int[] result = new int[toSearch.length];
        for (int i = 0; i < result.length; i++) {
            final char c = toSearch[i];
            for (int j = 0; j < toProcess.length; j++) {
                if (c == toProcess[j]) {
                    result[i] = j;
                    break;
                }
            }
        }
        return result;
    }
}
