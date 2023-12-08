package com.cosmoloj.math.util.set.polynomial;

import com.cosmoloj.math.util.set.Ring;
import com.cosmoloj.math.util.set.rational.IntRational;

/**
 *
 * @author Samuel Andrés
 */
public class RationalPolynomialSet implements Ring<RationalPolynomial> {

    private static final RationalPolynomialSet INSTANCE = new RationalPolynomialSet();

    public static RationalPolynomialSet getInstance() {
        return INSTANCE;
    }

    @Override
    public RationalPolynomial multiplication(final RationalPolynomial a, final RationalPolynomial b) {
        final IntRational[] coeffs = initCoeffs(a.degree() + b.degree());

        for (int i = 0; i <= a.degree(); i++) {
            for (int j = 0; j <= b.degree(); j++) {
                coeffs[i + j] = coeffs[i + j].plus(a.coefficient(i).mult(b.coefficient(j)));
            }
        }

        return RationalPolynomial.of(coeffs);
    }

    public RationalPolynomial multiplication(final IntRational b, final RationalPolynomial a) {
        return multiplication(RationalPolynomial.of(b), a);
    }

    @Override
    public RationalPolynomial inverse(final RationalPolynomial e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RationalPolynomial identity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RationalPolynomial associative(final RationalPolynomial a, final RationalPolynomial b) {
        final IntRational[] coeffs = initCoeffs(Math.max(a.degree(), b.degree()));

        for (int i = 0; i <= a.degree(); i++) {
            coeffs[i] = coeffs[i].plus(a.coefficient(i));
        }

        for (int i = 0; i <= b.degree(); i++) {
            coeffs[i] = coeffs[i].plus(b.coefficient(i));
        }

        return RationalPolynomial.of(coeffs);
    }

    @Override
    public RationalPolynomial symetrical(final RationalPolynomial e) {
        final IntRational[] coeffs = new IntRational[e.degree() + 1];

        for (int i = 0; i <= e.degree(); i++) {
            coeffs[i] = e.coefficient(i).symetrical();
        }

        return RationalPolynomial.of(coeffs);
    }

    @Override
    public RationalPolynomial neutral() {
        throw new UnsupportedOperationException();
    }

    public RationalPolynomial compose(final RationalPolynomial a, final RationalPolynomial b) {
        RationalPolynomial c = RationalPolynomial.of(IntRational.ZERO, 0);
        for (int i = a.degree(); i >= 0; i--) {
            c = addition(RationalPolynomial.of(a.coefficient(i), 0), RationalPolynomialSet.this.multiplication(b, c));
        }
        return c;
    }

    public boolean eq(final RationalPolynomial a, final RationalPolynomial b) {
        if (a.degree() != b.degree()) {
            return false;
        }
        for (int i = a.degree(); i >= 0; i--) {
            if (!a.coefficient(i).eq(b.coefficient(i))) {
                return false;
            }
        }
        return true;
    }

    public static IntRational[] initCoeffs(final int degree) {
        final IntRational[] coeffs = new IntRational[degree + 1];

        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = IntRational.ZERO;
        }

        return coeffs;
    }
}
