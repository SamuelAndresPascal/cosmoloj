package com.cosmoloj.math.operation.trials;

/**
 * <div class="fr">Approximation d'un vecteur de doubles par itérations successives.</div>
 *
 * @author Samuel Andrés
 */
public interface DoubleVectorTrials {

    /**
     * <div class="fr">Calcul de l'approximation n à partir du vecteur calculé à l'approximation n - 1. Le résultat peut
     * être un résultat intermédiaire de l'approximation n.</div>
     *
     * @param input <span class="fr">approximation de l'itération n - 1</span>
     * @return <span class="fr">résultat final ou intermédiaire de l'approximation de l'itération n</span>
     */
    double[] trial(double[] input);

    /**
     *
     * @return <span class="fr">approximation initiale</span>
     */
    double[] init();

    /**
     * <div class="fr">Test d'arrêt basé sur la comparaison entre deux vecteurs définissant l'arrêt de la boucle.</div>
     * @param first
     * @param second
     * @return
     */
    boolean test(double[] first, double[] second);

    default double[] loop() {
        double[] it = trial(init());

        while (true) {
            final double[] tmp = trial(it);
            if (test(tmp, it)) {
                it = tmp;
                break;
            }
            it = tmp;
        }
        return it;
    }
}
