package com.cosmoloj.math.tabular.n;

import com.cosmoloj.math.tabular.Tabular;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <div class="fr">
 * Itérateur de parcours des index d'un tableau d'ordre quelconque.
 * </div>
 *
 * @author Samuel Andrés
 */
public class TabularIterator implements Iterator<int[]> {

    /**
     * <div class="fr">
     * Tableau d'état des index de l'élément courant. La valeur contenue à l'index i représente l'état de parcours selon
     * l'ordre correspondant.
     * </div>
     */
    private final int[] indexStates;

    /**
     * <div class="fr">
     * Tableau de la condition de fin de parcours. La valeur contenue à l'index i représente la taille du tableau selon
     * l'ordre correspondant.
     * </div>
     */
    private final int[] indexStop;

    /**
     * <div class="fr">
     * Détection de la fin de parcours (vrai après que la dernière combinaison
     * d'indices a été renvoyée par la méthode next()).
     * </div>
     */
    private boolean end = false;

    public TabularIterator(final Tabular t) {
        this(t.getDimensions());
    }

    public TabularIterator(final int[] dimensions) {
        // Ordre du tableau
        final int resultOrder = dimensions.length;
        indexStates = new int[resultOrder];
        indexStop = new int[resultOrder];
        initStateAndStop(indexStates, indexStop, dimensions);
        if (resultOrder == 0) {
            end = true;
        }
    }

    @Override
    public boolean hasNext() {
        return !end;
    }

    @Override
    public int[] next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        final int[] copyOf = Arrays.copyOf(indexStates, indexStates.length);
        if (!Arrays.equals(indexStates, indexStop)) {
            nextIndex(indexStates, indexStop);
        } else {
            end = true;
        }
        return copyOf;
    }

    /**
     * <div class="fr">
     * Initialise un tableau de départ et un tableau d'arrivée du parcours des
     * index à partir d'un tableau indiquant la dimension de chacun d'entre eux.
     * </div>
     *
     * @param states <span class="fr">tableau d'état de parcours à initialiser pour chaque ordre</span>
     * @param stop <span class="fr">tableau de condition de fin de parcours à initialiser pour chaque ordre</span>
     * @param dimensions <span class="fr">taille du tableau selon chaque ordre</span>
     */
    public static void initStateAndStop(final int[] states, final int[] stop, final int[] dimensions) {
        for (int i = 0; i < dimensions.length; i++) {
            states[i] = 0;
            stop[i] = dimensions[i] - 1;
        }
    }

    /**
     * <div class="fr">
     * Incrémente le tableau de l'index courant.
     * </div>
     *
     * @param indexStates <span class="fr">tableau d'état de parcours pour chaque ordre</span>
     * @param indexStop <span class="fr">tableau de condition de fin de parcours pour chaque ordre</span>
     */
    private static void nextIndex(final int[] indexStates, final int[] indexStop) {
        int i = 0;
        while (true) {
            int l = indexStates[i];
            if (l == indexStop[i]) {
                indexStates[i] = 0;
                i++;
            } else {
                indexStates[i]++;
                break;
            }
        }
    }
}
