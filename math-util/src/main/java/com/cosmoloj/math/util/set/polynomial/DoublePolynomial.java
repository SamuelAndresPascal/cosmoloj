package com.cosmoloj.math.util.set.polynomial;

import com.cosmoloj.math.util.set.RingElement;

/**
 * <div class="fr">
 * This class represents polynomials with double coefficients.
 * It is an adaptions from : http://introcs.cs.princeton.edu/java/92symbolic/Polynomial.java.html
 * </div>
 *
 * <pre>
 *  zero(x) =     0
 *  p(x) =        4x^3 + 3x^2 + 2x + 1
 *  q(x) =        3x^2 + 5
 *  p(x) + q(x) = 4x^3 + 6x^2 + 2x + 6
 *  p(x) * q(x) = 12x^5 + 9x^4 + 26x^3 + 18x^2 + 10x + 5
 *  p(q(x))     = 108x^6 + 567x^4 + 996x^2 + 586
 *  0 - p(x)    = -4x^3 - 3x^2 - 2x - 1
 *  p(3)        = 142
 *  p'(x)       = 12x^2 + 6x + 2
 *  p''(x)      = 24x + 6
 * </pre>
 *
 * @author Samuel Andrés
 */
public final class DoublePolynomial extends ObjectPolynomial<Double>
        implements RingElement<DoublePolynomial, DoublePolynomialSet> {

    private static final DoublePolynomialSet SET = DoublePolynomialSet.getInstance();

    private DoublePolynomial(final Double... coeff) {
        super(coeff);
    }

    private DoublePolynomial(final Double a, final int degree) {
        super(a, degree, 0.);
    }

    @Override
    public int degree() {
        int d = length() - 1;
        while (coefficient(d).equals(0.) && d != 0) {
            d -= 1;
        }
        return d;
    }

    @Override
    public int degreeMin() {
        int d = 0;
        while (coefficient(d).equals(0.) && d < degree()) {
            d += 1;
        }
        return d;
    }

    @Override
    public DoublePolynomial incrementDegree(final int incr) {
        if (incr == 0) {
            return this;
        } else if (incr < 0) {

            final Double[] coeffs = new Double[degree() + 1 + incr];

            for (int i = 0; i < coeffs.length; i++) {
                coeffs[i] = coefficient(i - incr);
            }

            return DoublePolynomial.of(coeffs);

        } else {
            final Double[] coeffs = new Double[degree() + 1 + incr];

            for (int i = 0; i < incr; i++) {
                coeffs[i] = 0.;
            }

            for (int i = incr; i < coeffs.length; i++) {
                coeffs[i] = coefficient(i - incr);
            }

            return DoublePolynomial.of(coeffs);
        }
    }

    @Override
    public DoublePolynomial inverse() {
        return set().inverse(this);
    }

    @Override
    public DoublePolynomial symetrical() {
        return set().symetrical(this);
    }

    @Override
    public DoublePolynomial associative(final DoublePolynomial r) {
        return set().addition(this, r);
    }

    @Override
    public DoublePolynomial mult(final DoublePolynomial r) {
        return set().multiplication(this, r);
    }

    @Override
    public DoublePolynomial mult(final Double s) {
        return set().multiplication(s, this);
    }

    public DoublePolynomial compose(final DoublePolynomial r) {
        return set().compose(this, r);
    }

    public boolean eq(final DoublePolynomial r) {
        return set().eq(this, r);
    }

    @Override
    public DoublePolynomialSet set() {
        return SET;
    }

    @Override
    public double evaluate(final double x) {
        double p = 0;
        for (int i = degree(); i >= 0; i--) {
            p = coefficient(i) + (x * p);
        }
        return p;
    }

    @Override
    public DoublePolynomial differentiate() {
        if (degree() == 0) {
            return new DoublePolynomial(0., 0);
        }
        final Double[] coeffs = DoublePolynomialSet.initCoeffs(degree() - 1);

        for (int i = 0; i < degree(); i++) {
            coeffs[i] = (i + 1) * coefficient(i + 1);
        }
        return new DoublePolynomial(coeffs);
    }

    @Override
    public String toString() {

        if (degree() ==  0) {
            return coefficient(0).toString();
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = degree(); i >= 0; i--) {

            if (coefficient(i).equals(0.)) {
                continue;
            } else if (coefficient(i) > 0.) {
                sb.append(" + ").append(coefficient(i));
            } else if (coefficient(i) < 0) {
                sb.append(" - ").append(Math.abs(coefficient(i)));
            }

            if (i > 1) {
                sb.append('x').append('^').append(i);
            } else if (i == 1) {
                sb.append('x');
            }
        }
        return sb.toString();
    }

    public String toString2() {

        if (degree() ==  0) {
            return coefficient(0).toString();
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= degree(); i++) {

            if (coefficient(i).equals(0.)) {
                continue;
            } else if (coefficient(i) > 0.) {
                sb.append(" + ").append(coefficient(i));
            } else if (coefficient(i) < 0) {
                sb.append(" - ").append(Math.abs(coefficient(i)));
            }

            if (i > 1) {
                sb.append('x').append('^').append(i);
            } else if (i == 1) {
                sb.append('x');
            }
        }
        return sb.toString();
    }

    public static DoublePolynomial of(final double[] coeffs) {
        final Double[] coeffsObj = new Double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            coeffsObj[i] = coeffs[i];
        }
        return of(coeffsObj);
    }

    public static DoublePolynomial of(final Double... coeffs) {
        return new DoublePolynomial(coeffs);
    }

    public static DoublePolynomial of(final RationalPolynomial rationalPolynomial) {
        final Double[] coeffs = new Double[rationalPolynomial.degree() + 1];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = rationalPolynomial.coefficient(i).doubleValue();
        }
        return of(coeffs);
    }

    public static DoublePolynomial of(final Double a, final int degree) {
        return new DoublePolynomial(a, degree);
    }
}
