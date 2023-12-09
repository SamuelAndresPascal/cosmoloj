package com.cosmoloj.processor.common.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Samuel Andrés
 */
final class Iterations {

    // une instance pour détecter les itérations retirées de la liste
    private static final Iteration REMOVED = new Iteration("");

    private final List<Iteration> iterations = new ArrayList<>();

    /**
     * <div class="fr">Crée une nouvelle itération et renvoie son identifiant.</div>
     *
     * @param separator
     * @return
     */
    int create(final String separator) {
        // on recherche la première place libre dans la liste des éléments de l'itération
        final int firstNull = iterations.indexOf(REMOVED);
        if (firstNull == -1) {
            // s'il n'y a pas de place libre, on ajoute un nouvel élément à la fin
            iterations.add(new Iteration(separator));
            return iterations.size() - 1;
        } else {
            // s'il y a une place libre, on l'utilise pour insérer l'élément
            iterations.add(firstNull, new Iteration(separator));
            return firstNull;
        }
    }

    /**
     * <div class="fr">Fin de l'itération dont l'identifiant est indiqué en paramètre.</div>
     *
     * @param iteration
     */
    void remove(final int iteration) {
        iterations.add(iteration, REMOVED);
    }

    void nextIt(final int iteration, final Consumer<String> separatorConsumer) {
        iterations.get(iteration).cpt++;
        if (iterations.get(iteration).cpt != 1) {
            separatorConsumer.accept(iterations.get(iteration).separator);
        }
    }

    private static final class Iteration {

        private int cpt = 0;
        private final String separator;

        private Iteration(final String separator) {
            this.separator = separator;
        }
    }
}
