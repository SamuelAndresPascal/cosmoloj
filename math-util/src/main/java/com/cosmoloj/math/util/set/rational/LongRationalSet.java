package com.cosmoloj.math.util.set.rational;

import com.cosmoloj.math.util.set.EquivalenceRelation;
import com.cosmoloj.math.util.set.Field;
import com.cosmoloj.math.util.set.OrderRelation;

/**
 *
 * @author Samuel Andrés
 */
public final class LongRationalSet implements Field<LongRational> {

    private static final LongRationalSet INSTANCE = new LongRationalSet();

    public static LongRationalSet getInstance() {
        return INSTANCE;
    }

    @Override
    public LongRational associative(final LongRational a, final LongRational b) {
        if (a.longDenominator() == b.longDenominator()) {
            return LongRational.of(a.signedIntNumerator() + b.signedIntNumerator(), a.longDenominator());
        } else {
            return LongRational.of(
                    a.signedIntNumerator() * b.longDenominator() + b.signedIntNumerator() * a.longDenominator(),
                    a.longDenominator() * b.longDenominator());
        }
    }

    @Override
    public LongRational symetrical(final LongRational e) {
        return LongRational.of(-e.signedIntNumerator(), e.longDenominator());
    }

    @Override
    public LongRational multiplication(final LongRational a, final LongRational b) {
        return LongRational.of(a.longNumerator() * b.longNumerator() * a.sign() * b.sign(),
                a.longDenominator() * b.longDenominator());
    }

    public LongRational multiplication(final LongRational e, final long value) {
        return multiplication(e, LongRational.of(value));
    }

    @Override
    public LongRational inverse(final LongRational e) {
        return LongRational.of(e.longDenominator() * e.sign(), e.longNumerator());
    }

    @EquivalenceRelation
    public boolean eq(final LongRational a, final LongRational b) {
        return compare(a, b) == 0;
    }

    @OrderRelation
    public int compare(final LongRational a, final LongRational b) {
        return Long.compare(a.longValue(), b.longValue());
    }

    @Override
    public LongRational neutral() {
        return LongRational.ZERO;
    }

    @Override
    public LongRational identity() {
        return LongRational.ONE;
    }

    public LongRational abs(final LongRational e) {
        return LongRational.of(e.longNumerator(), e.longDenominator());
    }
}
