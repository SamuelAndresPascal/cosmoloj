package com.cosmoloj.math.operation.projection;

/**
 *
 * @author Samuel Andrés
 */
public enum Aspect {
    NORTH_POLE(Math.toRadians(90.)), SOUTH_POLE(Math.toRadians(-90.));

    private final double phi0;

    Aspect(final double phi0) {
        this.phi0 = phi0;
    }

    public static Aspect interpret(final double phi) {
        return phi > 0. ? Aspect.NORTH_POLE : Aspect.SOUTH_POLE;
    }

    public double phi0() {
        return phi0;
    }
}
