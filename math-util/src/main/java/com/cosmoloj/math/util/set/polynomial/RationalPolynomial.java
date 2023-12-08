package com.cosmoloj.math.util.set.polynomial;

import com.cosmoloj.math.util.set.RingElement;
import com.cosmoloj.math.util.set.rational.IntRational;
import java.util.function.IntFunction;

/**
 *
 * @author Samuel Andrés
 */
public final class RationalPolynomial extends ObjectPolynomial<IntRational>
        implements RingElement<RationalPolynomial, RationalPolynomialSet> {

    private static final RationalPolynomialSet SET = RationalPolynomialSet.getInstance();

    private RationalPolynomial(final IntRational... coeffs) {
        super(coeffs);
    }

    private RationalPolynomial(final IntRational a, final int degree) {
        super(a, degree, IntRational.ZERO);
    }

    @Override
    public int degree() {
        int d = length() - 1;
        while (IntRational.ZERO.equals(coefficient(d)) && d != 0) {
            d -= 1;
        }
        return d;
    }

    @Override
    public int degreeMin() {
        int d = 0;
        while (IntRational.ZERO.equals(coefficient(d)) && d < degree()) {
            d += 1;
        }
        return d;
    }

    @Override
    public RationalPolynomial incrementDegree(final int incr) {
        if (incr == 0) {
            return this;
        } else if (incr < 0) {

            final IntRational[] coeffs = new IntRational[degree() + 1 + incr];

            for (int i = 0; i < coeffs.length; i++) {
                coeffs[i] = coefficient(i - incr);
            }

            return RationalPolynomial.of(coeffs);

        } else {
            final IntRational[] coeffs = new IntRational[degree() + 1 + incr];

            for (int i = 0; i < incr; i++) {
                coeffs[i] = IntRational.ZERO;
            }

            for (int i = incr; i < coeffs.length; i++) {
                coeffs[i] = coefficient(i - incr);
            }

            return RationalPolynomial.of(coeffs);
        }
    }

    @Override
    public RationalPolynomial mult(final RationalPolynomial e) {
        return set().multiplication(this, e);
    }

    @Override
    public RationalPolynomial mult(final IntRational s) {
        return set().multiplication(s, this);
    }

    @Override
    public RationalPolynomial inverse() {
        return set().inverse(this);
    }

    @Override
    public RationalPolynomial associative(final RationalPolynomial e) {
        return set().associative(this, e);
    }

    @Override
    public RationalPolynomial symetrical() {
        return set().symetrical(this);
    }

    @Override
    public RationalPolynomialSet set() {
        return SET;
    }

    public RationalPolynomial compose(final RationalPolynomial r) {
        return set().compose(this, r);
    }

    public boolean eq(final RationalPolynomial r) {
        return set().eq(this, r);
    }

    @Override
    public double evaluate(final double x) {
        double p = 0;
        for (int i = degree(); i >= 0; i--) {
            p = coefficient(i).doubleValue() + (x * p);
        }
        return p;
    }

    @Override
    public RationalPolynomial differentiate() {
        if (degree() == 0) {
            return new RationalPolynomial(IntRational.ZERO, 0);
        }

        final IntRational[] coeffs = RationalPolynomialSet.initCoeffs(degree() - 1);

        for (int i = 0; i < degree(); i++) {
            coeffs[i] = coefficient(i + 1).mult(i + 1);
        }
        return new RationalPolynomial(coeffs);
    }

    @Override
    public String toString() {

        if (degree() ==  0) {
            return coefficient(0).toString();
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = degree(); i >= 0; i--) {

            if (IntRational.ZERO.eq(coefficient(i))) {
                continue;
            } else if (IntRational.ZERO.compareTo(coefficient(i)) < 0) {
                sb.append(" + ").append(coefficient(i));
            } else if (IntRational.ZERO.compareTo(coefficient(i)) > 0) {
                sb.append(" - ").append(coefficient(i).abs());
            }

            if (i > 1) {
                sb.append('x').append('^').append(i);
            } else if (i == 1) {
                sb.append('x');
            }
        }
        return sb.toString();
    }

    public static RationalPolynomial of(final IntFunction<IntRational> sequence, final int order) {
        final IntRational[] coeffs = new IntRational[order + 1];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = sequence.apply(i);
        }
        return new RationalPolynomial(coeffs);
    }

    public static RationalPolynomial of(final IntRational... coeffs) {
        return new RationalPolynomial(coeffs);
    }

    public static RationalPolynomial of(final IntRational a, final int degree) {
        return new RationalPolynomial(a, degree);
    }
}
