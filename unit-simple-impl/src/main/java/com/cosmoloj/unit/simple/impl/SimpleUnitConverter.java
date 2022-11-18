package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.UnitConverter;

/**
 *
 * @author Samuel Andrés
 */
public final class SimpleUnitConverter implements UnitConverter {

    private final double scale;
    private final double offset;
    private final UnitConverter inverse;

    private SimpleUnitConverter(final double scale, final double offset, final UnitConverter inverse) {
        this.scale = scale;
        this.offset = offset;
        this.inverse = inverse;
    }

    private SimpleUnitConverter(final double scale, final double offset) {
        this.scale = scale;
        this.offset = offset;
        this.inverse = new SimpleUnitConverter(1. / scale, -offset / scale, this);
    }

    @Override
    public double scale() {
        return scale;
    }

    @Override
    public double offset() {
        return offset;
    }

    @Override
    public UnitConverter inverse() {
        return inverse;
    }

    @Override
    public UnitConverter linear() {
        // comparaison stricte volontaire sur un flottant
        if (offset == 0.) {
            return this;
        } else {
            return linear(scale);
        }
    }

    @Override
    public UnitConverter linearPow(final double pow) {
        // comparaison stricte volontaire sur des flottants
        if (offset == 0. && pow == 1.) {
            return this;
        } else {
            return linear(Math.pow(scale, pow));
        }
    }

    @Override
    public double convert(final double value) {
        return scale * value + offset;
    }

    @Override
    public UnitConverter concatenate(final UnitConverter converter) {
        return of(converter.scale() * scale, this.convert(converter.offset()));
    }

    private static final UnitConverter IDENTITY = linear(1.);

    public static UnitConverter of(final double scale, final double offset) {
        return new SimpleUnitConverter(scale, offset);
    }

    public static UnitConverter linear(final double scale) {
        return new SimpleUnitConverter(scale, 0.);
    }

    public static UnitConverter translation(final double offset) {
        return new SimpleUnitConverter(1., offset);
    }

    public static UnitConverter identity() {
        return IDENTITY;
    }

}
