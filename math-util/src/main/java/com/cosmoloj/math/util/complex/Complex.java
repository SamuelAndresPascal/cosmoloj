package com.cosmoloj.math.util.complex;

/**
 *
 * @author Samuel Andrés
 */
public final class Complex {

    public static final Complex ZERO = Complex.of(0., 0.);
    public static final Complex UNIT_REAL = Complex.real(1.);
    public static final Complex UNIT_IMAGINARY = Complex.imaginary(1.);

    private final double real;
    private final double imaginary;

    private Complex(final double real, final double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public Complex mult(final double arg) {
        return Complex.of(real * arg, imaginary * arg);
    }

    public Complex div(final double arg) {
        return Complex.of(real / arg, imaginary / arg);
    }

    public Complex add(final Complex second) {
        return Complex.of(real + second.real, imaginary + second.imaginary);
    }

    public Complex mult(final Complex second) {
        return Complex.of(real * second.real - imaginary * second.imaginary,
                real * second.imaginary + imaginary * second.real);
    }

    public Complex opposite() {
        return Complex.of(-real, -imaginary);
    }

    public double norm2() {
        return real * real + imaginary * imaginary;
    }

    public double norm() {
        return Math.sqrt(norm2());
    }

    public Complex inverse() {
        return Complex.of(real, -imaginary).div(norm2());
    }

    public Complex div(final Complex arg) {
        return mult(arg.inverse());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.real)
                ^ (Double.doubleToLongBits(this.real) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.imaginary)
                ^ (Double.doubleToLongBits(this.imaginary) >>> 32));
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (Double.doubleToLongBits(this.real) != Double.doubleToLongBits(other.real)) {
            return false;
        }
        if (Double.doubleToLongBits(this.imaginary) != Double.doubleToLongBits(other.imaginary)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return real + " + i " + imaginary;
    }

    public static Complex of(final double real, final double imaginary) {
        return new Complex(real, imaginary);
    }

    public static Complex real(final double real) {
        return new Complex(real, 0.);
    }

    public static Complex imaginary(final double imaginary) {
        return new Complex(0., imaginary);
    }

    public static Complex add(final Complex first, final Complex second) {
        return Complex.of(first.real + second.real, first.imaginary + second.imaginary);
    }

    public static Complex mult(final Complex first, final Complex second) {
        return Complex.of(first.real * second.real - first.imaginary * second.imaginary,
                first.real * second.imaginary + first.imaginary * second.real);
    }

    public static Complex opposite(final Complex arg) {
        return Complex.of(-arg.real, -arg.imaginary);
    }

    public static double[] add(final double[] first, final double[] second) {
        return new double[]{first[0] + second[0], first[1] + second[1]};
    }

    public static double[] mult(final double[] first, final double[] second) {
        return new double[]{first[0] * second[0] - first[1] * second[1], first[0] * second[1] + first[1] * second[0]};
    }

    public static double[] opposite(final double[] arg) {
        return new double[]{-arg[0], -arg[1]};
    }
}
