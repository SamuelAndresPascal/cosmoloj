package com.cosmoloj.math.util.set.rational;

import com.cosmoloj.math.util.set.EquivalenceRelation;
import com.cosmoloj.math.util.set.Field;
import com.cosmoloj.math.util.set.OrderRelation;

/**
 *
 * @author Samuel Andrés
 */
public final class IntRationalSet implements Field<IntRational> {

    private static final IntRationalSet INSTANCE = new IntRationalSet();

    public static IntRationalSet getInstance() {
        return INSTANCE;
    }

    @Override
    public IntRational associative(final IntRational a, final IntRational b) {
        if (a.intDenominator() == b.intDenominator()) {
            return IntRational.of(a.signedIntNumerator() + b.signedIntNumerator(), a.intDenominator());
        } else {
            return IntRational.of(
                    a.signedIntNumerator() * b.intDenominator() + b.signedIntNumerator() * a.intDenominator(),
                    a.intDenominator() * b.intDenominator());
        }
    }

    @Override
    public IntRational symetrical(final IntRational e) {
        return IntRational.of(-e.signedIntNumerator(), e.intDenominator());
    }

    @Override
    public IntRational multiplication(final IntRational a, final IntRational b) {
        return IntRational.of(a.intNumerator() * b.intNumerator() * a.sign() * b.sign(),
                a.intDenominator() * b.intDenominator());
    }

    public IntRational multiplication(final IntRational e, final int value) {
        return multiplication(e, IntRational.of(value));
    }

    @Override
    public IntRational inverse(final IntRational e) {
        return IntRational.of(e.intDenominator() * e.sign(), e.intNumerator());
    }

    @EquivalenceRelation
    public boolean eq(final IntRational a, final IntRational b) {
        return compare(a, b) == 0;
    }

    @OrderRelation
    public int compare(final IntRational a, final IntRational b) {
        return a.signedIntNumerator() * b.intDenominator() - b.signedIntNumerator() * a.intDenominator();
    }

    @Override
    public IntRational neutral() {
        return IntRational.ZERO;
    }

    @Override
    public IntRational identity() {
        return IntRational.ONE;
    }

    public IntRational abs(final IntRational e) {
        return IntRational.of(e.intNumerator(), e.intDenominator());
    }
}
