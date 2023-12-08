package com.cosmoloj.math.util.set.rational;

import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.math.util.set.FieldElement;

/**
 *
 * @author Samuel Andrés
 */
public final class LongRational extends Number
        implements Comparable<LongRational>, FieldElement<LongRational, LongRationalSet> {

    private static final LongRationalSet SET = LongRationalSet.getInstance();

    public static final LongRational ZERO = new LongRational(0);

    public static final LongRational ONE = new LongRational(1);

    private final long numerator;
    private final long denominator;
    private final long sign;

    private LongRational(final long numerator, final long denominator) {
        this.numerator = Math.abs(numerator);
        this.denominator = Math.abs(denominator);
        this.sign = (numerator < 0) ^ (denominator < 0) ? -1 : 1;
    }

    private LongRational(final LongRational numerator, final long denominator) {
        this(numerator.numerator * numerator.sign, denominator * numerator.denominator);
    }

    private LongRational(final long numerator, final LongRational denominator) {
        this(numerator * denominator.denominator, denominator.numerator *  denominator.sign);
    }

    private LongRational(final LongRational numerator, final LongRational denominator) {
        this(numerator.numerator * denominator.denominator * numerator.sign,
                denominator.numerator * numerator.denominator *  denominator.sign);
    }

    private LongRational(final LongRational numerator) {
        this(numerator.numerator *  numerator.sign, numerator.denominator);
    }

    private LongRational(final long numerator) {
        this(numerator, 1);
    }

    public long longNumerator() {
        return this.numerator;
    }

    public long signedIntNumerator() {
        return this.sign * this.numerator;
    }

    public long longDenominator() {
        return this.denominator;
    }

    public long sign() {
        return this.sign;
    }

    public boolean eq(final LongRational r) {
        return set().eq(this, r);
    }

    private long pgcd() {
        return MathUtil.pgcd(numerator, denominator);
    }

    public LongRational toIrreductible() {
        final long pgcd = pgcd();
        if (pgcd == 1) {
            return this;
        }

        return LongRational.of(signedIntNumerator() / pgcd, longDenominator() / pgcd);
    }

    @Override
    public int compareTo(final LongRational o) {
        return set().compare(this, o);
    }

    @Override
    public int hashCode() {
        long hash = 3;
        hash = 59 * hash + this.numerator;
        hash = 59 * hash + this.denominator;
        hash = 59 * hash + this.sign;
        return Long.hashCode(hash);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LongRational)) {
            return false;
        }
        final LongRational other = (LongRational) obj;
        if (this.numerator != other.numerator) {
            return false;
        }
        if (this.denominator != other.denominator) {
            return false;
        }
        return this.sign == other.sign;
    }

    @Override
    public String toString() {

        if (this == ZERO) {
            return "0";
        } else if (this == ONE) {
            return "1";
        }

        final StringBuilder sb = new StringBuilder();
        if (sign < 0) {
            sb.append('-');
        }
        return sb.append(numerator).append('/').append(denominator).toString();
    }

    public LongRational abs() {
        return set().abs(this);
    }

    @Override
    public double doubleValue() {
        return ((double) this.signedIntNumerator()) / this.longDenominator();
    }

    @Override
    public int intValue() {
        return (int) longValue();
    }

    @Override
    public long longValue() {
        return this.signedIntNumerator() / this.longDenominator();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public LongRationalSet set() {
        return SET;
    }

    @Override
    public LongRational associative(final LongRational e) {
        return set().associative(this, e);
    }

    @Override
    public LongRational symetrical() {
        return set().symetrical(this);
    }

    @Override
    public LongRational mult(final LongRational e) {
        return set().multiplication(this, e);
    }

    public LongRational mult(final long e) {
        return set().multiplication(this, e);
    }

    @Override
    public LongRational inverse() {
        return set().inverse(this);
    }

    public static LongRational of(final long numerator, final long denominator) {
        return new LongRational(numerator, denominator);
    }

    public static LongRational of(final LongRational numerator, final long denominator) {
        return new LongRational(numerator, denominator);
    }

    public static LongRational of(final long numerator, final LongRational denominator) {
        return new LongRational(numerator, denominator);
    }

    public static LongRational of(final LongRational numerator, final LongRational denominator) {
        return new LongRational(numerator, denominator);
    }

    public static LongRational of(final LongRational value) {
        return new LongRational(value);
    }

    public static LongRational of(final long value) {
        return new LongRational(value);
    }
}
