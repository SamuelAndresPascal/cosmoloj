package com.cosmoloj.math.util.set.polynomial;

import com.cosmoloj.math.util.set.Ring;

/**
 *
 * @author Samuel Andrés
 */
public class DoublePolynomialSet implements Ring<DoublePolynomial> {

    private static final DoublePolynomialSet INSTANCE = new DoublePolynomialSet();

    public static DoublePolynomialSet getInstance() {
        return INSTANCE;
    }

    @Override
    public DoublePolynomial inverse(final DoublePolynomial e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoublePolynomial identity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoublePolynomial symetrical(final DoublePolynomial e) {
        final Double[] coeffs = new Double[e.degree() + 1];

        for (int i = 0; i <= e.degree(); i++) {
            coeffs[i] = -e.coefficient(i);
        }

        return DoublePolynomial.of(coeffs);
    }

    @Override
    public DoublePolynomial neutral() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoublePolynomial associative(final DoublePolynomial a, final DoublePolynomial b) {
        final Double[] coeffs = initCoeffs(Math.max(a.degree(), b.degree()));

        for (int i = 0; i <= a.degree(); i++) {
            coeffs[i] = coeffs[i] + a.coefficient(i);
        }

        for (int i = 0; i <= b.degree(); i++) {
            coeffs[i] = coeffs[i] + b.coefficient(i);
        }

        return DoublePolynomial.of(coeffs);
    }

    @Override
    public DoublePolynomial multiplication(final DoublePolynomial a, final DoublePolynomial b) {
        final Double[] coeffs = initCoeffs(a.degree() + b.degree());

        for (int i = 0; i <= a.degree(); i++) {
            for (int j = 0; j <= b.degree(); j++) {
                coeffs[i + j] = coeffs[i + j] + a.coefficient(i) * b.coefficient(j);
            }
        }

        return DoublePolynomial.of(coeffs);
    }

    public DoublePolynomial multiplication(final Double s, final DoublePolynomial a) {
        return multiplication(DoublePolynomial.of(s), a);
    }

    public DoublePolynomial compose(final DoublePolynomial a, final DoublePolynomial b) {
        DoublePolynomial c = DoublePolynomial.of(0., 0);
        for (int i = a.degree(); i >= 0; i--) {
            c = addition(DoublePolynomial.of(a.coefficient(i), 0), multiplication(b, c));
        }
        return c;
    }

    public boolean eq(final DoublePolynomial a, final DoublePolynomial b) {
        if (a.degree() != b.degree()) {
            return false;
        }
        for (int i = a.degree(); i >= 0; i--) {
            if (!a.coefficient(i).equals(b.coefficient(i))) {
                return false;
            }
        }
        return true;
    }

    public static Double[] initCoeffs(final int degree) {
        final Double[] coeffs = new Double[degree + 1];

        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = 0.;
        }

        return coeffs;
    }
}
