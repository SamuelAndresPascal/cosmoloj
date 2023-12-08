package com.cosmoloj.math.util.set.polynomial;

import com.cosmoloj.math.util.complex.Complex;
import java.util.stream.DoubleStream;

/**
 *
 * @author Samuel Andrés
 */
public final class PolynomialUtil {

    private PolynomialUtil() {
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré croissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre croissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static Complex hornerAsc(final Complex[] coeffs, final Complex x) {
        Complex result = coeffs[coeffs.length - 1];

        if (coeffs.length != 1) {
            for (int i = coeffs.length - 2; i >= 0; i--) {
                result = Complex.add(Complex.mult(result, x), coeffs[i]);
            }
        }
        return result;
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré décroissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre décroissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static Complex hornerDesc(final Complex[] coeffs, final Complex x) {
        Complex result = coeffs[0];

        if (coeffs.length != 1) {
            for (int i = 1; i < coeffs.length; i++) {
                result = Complex.add(Complex.mult(result, x), coeffs[i]);
            }
        }
        return result;
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré croissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre croissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static double hornerAsc(final double[] coeffs, final double x) {
        double result = coeffs[coeffs.length - 1];

        if (coeffs.length != 1) {
            for (int i = coeffs.length - 2; i >= 0; i--) {
                result = result * x + coeffs[i];
            }
        }
        return result;
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré croissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre croissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static double hornerAsc(final DoubleStream coeffs, final double x) {
        return hornerAsc(coeffs.toArray(), x);
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré décroissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre décroissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static double hornerDesc(final double[] coeffs, final double x) {
        double result = coeffs[0];

        if (coeffs.length != 1) {
            for (int i = 1; i < coeffs.length; i++) {
                result = result * x + coeffs[i];
            }
        }
        return result;
    }

    /**
     * <div class="fr">Valeur du polynôme représenté par ses coefficients de degré décroissant à partir de 0, pour t.
     * </div>
     * @param coeffs <span class="fr">coefficients du polynôme, par ordre décroissant de degré</span>
     * @param x <span class="fr">valeur d'évaluation</span>
     * @return <span class="fr">évaluation du polynôme à la valeur indiquée</span>
     */
    public static double hornerDesc(final DoubleStream coeffs, final double x) {
        return hornerDesc(coeffs.toArray(), x);
    }
}
