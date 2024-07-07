package com.cosmoloj.math.operation.surface;

/**
 *
 * @author Samuel Andrés
 */
public final class Spheroid implements Surface {

    private static final Spheroid UNIT_SPHERE = Spheroid.ofRadius(1.);

    private final double r;

    private Spheroid(final double r) {
        this.r = r;
    }

    /**
     * @return <span class="en">radius</span>
     */
    public double r() {
        return r;
    }

    public static Spheroid ofRadius(final double r) {
        return new Spheroid(r);
    }

    public static Spheroid unit() {
        return UNIT_SPHERE;
    }
}
