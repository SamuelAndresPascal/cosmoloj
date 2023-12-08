package com.cosmoloj.math.util.sequence;

import com.cosmoloj.math.util.set.rational.IntRational;
import java.util.function.IntFunction;

/**
 *
 * @author Samuel Andrés
 */
public final class RationalSequence {

    private RationalSequence() {
    }

    public static final class Pow implements IntFunction<IntRational> {

        private final IntRational term;

        public Pow(final IntRational pow) {
            this.term = pow;
        }

        @Override
        public IntRational apply(final int n) {
            if (n == 0) {
                return IntRational.ONE;
            }
            return apply(n - 1).mult(IntRational.of(term.minus(IntRational.of(n - 1)), n));
        }
    }

    public static final EvenRationalSequence COS = new EvenRationalSequence() {
        @Override
        protected IntRational evenCase(final int n) {
            if (n == 0) {
                return IntRational.ONE;
            }
            return evenCase(n - 2).mult(IntRational.of(-1, n * (n - 1)));
        }
    };

    public static final OddRationalSequence SIN = new OddRationalSequence() {
        @Override
        protected IntRational oddCase(final int n) {
            if (n == 1) {
                return IntRational.ONE;
            }
            return oddCase(n - 2).mult(IntRational.of(-1, n * (n - 1)));
        }
    };

    public static final OddRationalSequence ARCSIN = new OddRationalSequence() {
        @Override
        protected IntRational oddCase(final int n) {
            if (n == 1) {
                return IntRational.ONE;
            }
            return oddCase(n - 2).mult(IntRational.of((n - 2) * (n - 2), (n - 1) * n));
        }
    };

    public static final OddRationalSequence ARCTAN = new OddRationalSequence() {
        @Override
        protected IntRational oddCase(final int n) {
            return IntRational.of((n / 2) % 2 == 0 ? 1 : -1, n);
        }
    };

    public static final OddRationalSequence ARGTH = new OddRationalSequence() {
        @Override
        protected IntRational oddCase(final int n) {
            return IntRational.of(1, n);
        }
    };

    public abstract static class OddRationalSequence implements IntFunction<IntRational> {

        @Override
        public IntRational apply(final int n) {

            if (n % 2 == 0) {
                return IntRational.ZERO;
            } else {
                return oddCase(n);
            }
        }

        protected abstract IntRational oddCase(int n);

    }

    public abstract static class EvenRationalSequence implements IntFunction<IntRational> {

        @Override
        public IntRational apply(final int n) {

            if (n % 2 == 1) {
                return IntRational.ZERO;
            } else {
                return evenCase(n);
            }
        }

        protected abstract IntRational evenCase(int n);

    }
}
