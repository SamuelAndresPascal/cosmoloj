package com.cosmoloj.math.util.set.polynomial;

/**
 *
 * @author Samuel Andrés
 * @param <C>
 */
public abstract class ObjectPolynomial<C> implements Polynomial<C> {

    private final C[] coeffs;

    public ObjectPolynomial(final C... coeff) {
        this.coeffs = coeff;
    }

    public ObjectPolynomial(final C a, final int degree, final C zero) {
        coeffs = (C[]) new Object[degree + 1];
        coeffs[degree] = a;
        for (int i = 0; i < degree; i++) {
            coeffs[i] = zero;
        }
    }

    @Override
    public C coefficient(final int degree) {
        return coeffs[degree];
    }

    protected int length() {
        return coeffs.length;
    }

    @Override
    public C[] toCoefficients() {
        return coeffs.clone();
    }
}
