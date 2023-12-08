package com.cosmoloj.math.util;


/**
 *
 * @author Samuel Andrés
 */
public final class MathUtil {

    public static final double RADIANS_IN_CIRCLE = 2 * Math.PI;

    public static final double DEGREES_IN_CIRCLE = 360.;

    public static final double ARCSECS_IN_CIRCLE = DEGREES_IN_CIRCLE * 3600.;

    private MathUtil() {
    }

    public static final double GAMMA_1_2 = Math.sqrt(Math.PI);

    public static int pgcd(final int a, final int b) {

        if (b == 0) {
           return a;
        }

        return pgcd(b, a % b);
    }

    public static long pgcd(final long a, final long b) {

        if (b == 0) {
           return a;
        }

        return pgcd(b, a % b);
    }

    public static int ppcm(final int a, final int b) {
        return Math.abs(a * b) / pgcd(a, b);
    }

    public static long ppcm(final long a, final long b) {
        return Math.abs(a * b) / pgcd(a, b);
    }

    public static long factorial(final int n) {
        long resultat = 1;
        if (n == 0) {
            return resultat;
        } else {
            for (int i = 0; i < n; i++) {
                resultat *= (i + 1);
            }
        }
        return resultat;
    }

    public static long oddFactorielle(final int n) {
        long resultat = 1;
        if (n == 0) {
            return resultat;
        } else {
            for (int i = 1; i < n; i += 2) {
                resultat *= (i + 1);
            }
        }
        return resultat;
    }

    public static long evenFactorielle(final int n) {
        long resultat = 1;
        if (n == 0) {
            return resultat;
        } else {
            for (int i = 0; i < n; i += 2) {
                resultat *= (i + 1);
            }
        }
        return resultat;
    }

    public static double factorialQuotient(final int n, final int d) {
        double resultat = 1;
        for (int i = Math.max(n, d); i > Math.min(n, d); i--) {
            resultat *= i;
        }
        if (d > n) {
            resultat = 1 / resultat;
        }
        return resultat;
    }

    public static double binomialCoeff(final int n, final int k) {
        return factorialQuotient(n, k) / factorial(n - k);
    }

    public static double gaussCoeffEven(final int n, final double p) {
        double resultat = 1;
        for (int i = n; i > -n; i--) {
            resultat *= (p + i);
        }
        return resultat / factorial(2 * n);
    }

    public static double gaussCoeffOdd(final int n, final double p) {
        double resultat = 1;
        for (int i = n; i > -n - 1; i--) {
            resultat *= (p + i);
        }
        return resultat / factorial(2 * n + 1);
    }

    public static double gaussCoeff(final int n, final double p) {
        if (n % 2 == 0) {
            return gaussCoeffEven(n / 2, p);
        } else {
            return gaussCoeffOdd(n / 2, p);
        }
    }

    public static double besselCoeff(final int n, final double p) {
        if (n % 2 == 0) {
            return gaussCoeffEven(n / 2, p) / 2;
        } else {
            return gaussCoeffOdd(n / 2, p) - gaussCoeffEven(n / 2, p) / 2;
        }
    }

    public static double asinh(final double x) {
        return Math.log(x + Math.sqrt(x * x + 1.));
    }

    public static double acosh(final double x) {
        return Math.log(x + Math.sqrt(x * x - 1.));
    }

    public static double atanh(final double x) {
        return 1. / 2. * Math.log((1. + x) / (1. - x));
    }

    public static double toArcSec(final double angdeg) {
        return angdeg * 3600.;
    }

    public static double arcSecToDegrees(final double angarcsec) {
        return angarcsec / 3600.;
    }

    public static double arcSecToRadians(final double angarcsec) {
        return Math.toRadians(arcSecToDegrees(angarcsec));
    }

    public static double mod(final double arg, final double modulo) {
        final double result = arg % modulo;
        return (result < 0. ^ modulo < 0.) ? result + modulo : result;
    }

    public static float mod(final float arg, final float modulo) {
        final float result = arg % modulo;
        return (result < 0. ^ modulo < 0.) ? result + modulo : result;
    }

    public interface Circle {

        double lap();

        default double mod(final double angle) {
            return MathUtil.mod(angle, lap());
        }

        default double diff(final double first, final double second) {
            final double dif = mod(first - second);
            return (dif >= lap() / 2.) ? (dif - lap()) : dif;
        }
    }

    public static final class Arcsec implements Circle {

        @Override
        public double lap() {
            return ARCSECS_IN_CIRCLE;
        }
    }

    public static final class Degree implements Circle {

        @Override
        public double lap() {
            return DEGREES_IN_CIRCLE;
        }
    }

    public static final class Radian implements Circle {

        @Override
        public double lap() {
            return RADIANS_IN_CIRCLE;
        }
    }

    /**
     *
     * @param angle <span class="en">An angle (in degree).</span>
     * @return <span class="en">The angle modulo 360. * 3600., always positive [0;360. * 3600.[</span>
     */
    public static double modArcsec(final double angle) {
        return mod(angle, ARCSECS_IN_CIRCLE);
    }

    /**
     *
     * @param angle <span class="en">An angle (in degree).</span>
     * @return <span class="en">The angle modulo 360., always positive [0;360.[</span>
     */
    public static double modDegree(final double angle) {
        return mod(angle, DEGREES_IN_CIRCLE);
    }

    /**
     *
     * @param angle <span class="en">An angle (in radian).</span>
     * @return <span class="en">The angle modulo 2*PI, always positive [0;2*PI[</span>
     */
    public static double modRadian(final double angle) {
        return mod(angle, RADIANS_IN_CIRCLE);
    }

    public static double diffDegree(final double first, final double second) {
        final double diff = modDegree(first - second);
        return (diff >= DEGREES_IN_CIRCLE / 2.) ? (diff - DEGREES_IN_CIRCLE) : diff;
    }

    public static double diffRadian(final double first, final double second) {
        final double diff = modRadian(first - second);
        return (diff >= Math.PI) ? (diff - RADIANS_IN_CIRCLE) : diff;
    }

    public static double toDegrees(final int degrees, final int minutes, final double seconds) {
        if (degrees == 0) {
            if (minutes == 0) {
                if (seconds > -60. && seconds < 60.) {
                    return seconds / 3600.;
                }
                throw new IllegalArgumentException("seconds out of range ]-60;60[");
            } else if (minutes < 0) {
                if (seconds < 0. || minutes < -59) {
                    throw new IllegalArgumentException("seconds < 0 or minutes < -59");
                }
                return (-seconds + 60 * minutes) / 3600.;
            }

            if (minutes > 59 || seconds < 0. || seconds >= 60.) {
                throw new IllegalArgumentException("minutes > 59 or seconds out of range ]0;60]");
            }
            return (seconds + 60 * minutes) / 3600.;
        } else if (degrees < 0) {
            if (seconds < 0. || minutes < 0 || degrees < -359) {
                throw new IllegalArgumentException("seconds < 0 or minutes < 0 or degrees < -359");
            }

            if (degrees < -359 || minutes > 59 || seconds >= 60.) {
                throw new IllegalArgumentException("seconds >= 60 or minutes > 59 or degrees < -359");
            }
            return (-seconds + 60 * (-minutes + 60 * degrees)) / 3600.;
        }

        if (degrees > 359 || minutes > 59 || seconds >= 60.) {
            throw new IllegalArgumentException("seconds >= 60 or minutes > 59 or degrees > 359");
        }
        return (seconds + 60 * (minutes + 60 * degrees)) / 3600.;
    }

    public static double toRadians(final int degrees, final int minutes, final double seconds) {
        return Math.toRadians(toDegrees(degrees, minutes, seconds));
    }
}
