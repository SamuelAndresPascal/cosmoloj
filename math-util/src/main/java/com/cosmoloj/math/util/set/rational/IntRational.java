package com.cosmoloj.math.util.set.rational;

import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.math.util.set.FieldElement;

/**
 *
 * @author Samuel Andrés
 */
public final class IntRational extends Number
        implements Comparable<IntRational>, FieldElement<IntRational, IntRationalSet> {

    private static final IntRationalSet SET = IntRationalSet.getInstance();

    public static final IntRational ZERO = new IntRational(0);

    public static final IntRational ONE = new IntRational(1);

    private final int numerator;
    private final int denominator;
    private final int sign;

    private IntRational(final int numerator, final int denominator) {
        this.numerator = Math.abs(numerator);
        this.denominator = Math.abs(denominator);
        this.sign = (numerator < 0) ^ (denominator < 0) ? -1 : 1;
    }

    private IntRational(final IntRational numerator, final int denominator) {
        this(numerator.numerator * numerator.sign, denominator * numerator.denominator);
    }

    private IntRational(final int numerator, final IntRational denominator) {
        this(numerator * denominator.denominator, denominator.numerator *  denominator.sign);
    }

    private IntRational(final IntRational numerator, final IntRational denominator) {
        this(numerator.numerator * denominator.denominator * numerator.sign,
                denominator.numerator * numerator.denominator *  denominator.sign);
    }

    private IntRational(final IntRational numerator) {
        this(numerator.numerator *  numerator.sign, numerator.denominator);
    }

    private IntRational(final int numerator) {
        this(numerator, 1);
    }

    public int intNumerator() {
        return this.numerator;
    }

    public int signedIntNumerator() {
        return this.sign * this.numerator;
    }

    public int intDenominator() {
        return this.denominator;
    }

    public int sign() {
        return this.sign;
    }

    public boolean eq(final IntRational r) {
        return set().eq(this, r);
    }

    private int pgcd() {
        return MathUtil.pgcd(numerator, denominator);
    }

    public IntRational toIrreductible() {
        final int pgcd = pgcd();
        if (pgcd == 1) {
            return this;
        }

        return IntRational.of(signedIntNumerator() / pgcd, intDenominator() / pgcd);
    }

    @Override
    public int compareTo(final IntRational o) {
        return set().compare(this, o);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.numerator;
        hash = 59 * hash + this.denominator;
        hash = 59 * hash + this.sign;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IntRational)) {
            return false;
        }
        final IntRational other = (IntRational) obj;
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

    public IntRational abs() {
        return set().abs(this);
    }

    @Override
    public double doubleValue() {
        return ((double) this.signedIntNumerator()) / this.intDenominator();
    }

    @Override
    public int intValue() {
        return this.signedIntNumerator() / this.intDenominator();
    }

    @Override
    public long longValue() {
        return (long) intValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public IntRationalSet set() {
        return SET;
    }

    @Override
    public IntRational associative(final IntRational e) {
        return set().associative(this, e);
    }

    @Override
    public IntRational symetrical() {
        return set().symetrical(this);
    }

    @Override
    public IntRational mult(final IntRational e) {
        return set().multiplication(this, e);
    }

    public IntRational mult(final int e) {
        return set().multiplication(this, e);
    }

    @Override
    public IntRational inverse() {
        return set().inverse(this);
    }

    public static IntRational of(final int numerator, final int denominator) {
        return new IntRational(numerator, denominator);
    }

    public static IntRational of(final IntRational numerator, final int denominator) {
        return new IntRational(numerator, denominator);
    }

    public static IntRational of(final int numerator, final IntRational denominator) {
        return new IntRational(numerator, denominator);
    }

    public static IntRational of(final IntRational numerator, final IntRational denominator) {
        return new IntRational(numerator, denominator);
    }

    public static IntRational of(final IntRational value) {
        return new IntRational(value);
    }

    public static IntRational of(final int value) {
        return new IntRational(value);
    }
}
