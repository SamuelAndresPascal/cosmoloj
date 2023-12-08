package com.cosmoloj.math.util.set.polynomial;

/**
 *
 * @author Samuel Andrés
 * @param <C>
 */
public interface Polynomial<C> {

    /**
     *
     * @param degree <span class="fr">degré</span>
     * @return <span class="fr">coefficient correspondant au degré donné en paramètre</span>
     */
    C coefficient(int degree);

    /**
     * <div class="en">
     * Return the degree of this polynomial (0 for the zero polynomial)
     * </div>
     *
     * @return <span class="fr">degré effectif du polynôme</span>
     */
    int degree();

    int degreeMin();

    Polynomial<C> incrementDegree(int incr);

    Polynomial<C> mult(C s);


    /**
     * <div class="en">Computes and returns the polynomial evaluated at x.</div>
     *
     * @param x <span class="fr">valeur à laquelle évaluer le polynôme</span>
     * @return <span class="fr">évaluation du polynôme</span>
     */
    double evaluate(double x);

    Polynomial<C> differentiate();

    C[] toCoefficients();
}
