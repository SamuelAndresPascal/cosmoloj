package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.Factor;
import com.cosmoloj.unit.simple.api.Unit;

/**
 *
 * @author Samuel Andrés
 */
public final class SimpleFactor implements Factor {

    private final Unit dim;
    private final int numerator;
    private final int denominator;

    private SimpleFactor(final Unit dim, final int numerator, final int denominator) {
        this.dim = dim;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public Unit dim() {
        return dim;
    }

    @Override
    public int numerator() {
        return numerator;
    }

    @Override
    public int denominator() {
        return denominator;
    }

    public static Factor of(final Unit dim, final int numerator, final int denominator) {
        return new SimpleFactor(dim, numerator, denominator);
    }

    public static Factor of(final Unit dim, final int numerator) {
        return of(dim, numerator, 1);
    }

}
