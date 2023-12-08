package com.cosmoloj.math.util.sequence;

import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.math.util.set.rational.IntRational;
import java.util.function.IntFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class RationalSequenceTest {

    @Test
    @DisplayName("should reproduce arctan Taylor development coeffs")
    public void arctan_test() {
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(0));
        Assertions.assertEquals(IntRational.ONE, RationalSequence.ARCTAN.apply(1));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(2));
        Assertions.assertEquals(IntRational.of(-1, 3), RationalSequence.ARCTAN.apply(3));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(4));
        Assertions.assertEquals(IntRational.of(1, 5), RationalSequence.ARCTAN.apply(5));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(6));
        Assertions.assertEquals(IntRational.of(-1, 7), RationalSequence.ARCTAN.apply(7));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(8));
        Assertions.assertEquals(IntRational.of(1, 9), RationalSequence.ARCTAN.apply(9));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(10));
        Assertions.assertEquals(IntRational.of(-1, 11), RationalSequence.ARCTAN.apply(11));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCTAN.apply(12));
    }

    @Test
    @DisplayName("should reproduce argth Taylor development coeffs")
    public void argth_test() {
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(0));
        Assertions.assertEquals(IntRational.ONE, RationalSequence.ARGTH.apply(1));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(2));
        Assertions.assertEquals(IntRational.of(1, 3), RationalSequence.ARGTH.apply(3));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(4));
        Assertions.assertEquals(IntRational.of(1, 5), RationalSequence.ARGTH.apply(5));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(6));
        Assertions.assertEquals(IntRational.of(1, 7), RationalSequence.ARGTH.apply(7));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(8));
        Assertions.assertEquals(IntRational.of(1, 9), RationalSequence.ARGTH.apply(9));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(10));
        Assertions.assertEquals(IntRational.of(1, 11), RationalSequence.ARGTH.apply(11));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARGTH.apply(12));
    }

    @Test
    @DisplayName("should reproduce sin Taylor development coeffs")
    public void sin_test() {
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(0));
        Assertions.assertEquals(IntRational.ONE, RationalSequence.SIN.apply(1));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(2));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(3)), RationalSequence.SIN.apply(3));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(4));
        Assertions.assertEquals(IntRational.of(1, (int) MathUtil.factorial(5)), RationalSequence.SIN.apply(5));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(6));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(7)), RationalSequence.SIN.apply(7));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(8));
        Assertions.assertEquals(IntRational.of(1, (int) MathUtil.factorial(9)), RationalSequence.SIN.apply(9));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(10));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(11)), RationalSequence.SIN.apply(11));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.SIN.apply(12));
    }

    @Test
    @DisplayName("should reproduce cos Taylor development coeffs")
    public void cos_test() {
        Assertions.assertEquals(IntRational.ONE, RationalSequence.COS.apply(0));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(1));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(2)), RationalSequence.COS.apply(2));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(3));
        Assertions.assertEquals(IntRational.of(1, (int) MathUtil.factorial(4)), RationalSequence.COS.apply(4));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(5));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(6)), RationalSequence.COS.apply(6));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(7));
        Assertions.assertEquals(IntRational.of(1, (int) MathUtil.factorial(8)), RationalSequence.COS.apply(8));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(9));
        Assertions.assertEquals(IntRational.of(-1, (int) MathUtil.factorial(10)), RationalSequence.COS.apply(10));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.COS.apply(11));
        Assertions.assertEquals(IntRational.of(1, (int) MathUtil.factorial(12)), RationalSequence.COS.apply(12));
    }

    @Test
    @DisplayName("should reproduce arcsin Taylor development coeffs")
    public void arcsin_test() {
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(0));
        Assertions.assertEquals(IntRational.ONE, RationalSequence.ARCSIN.apply(1));
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(2));
        Assertions.assertEquals(IntRational.of(1, 2 * 3).toIrreductible(),
                RationalSequence.ARCSIN.apply(3).toIrreductible());
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(4));
        Assertions.assertEquals(IntRational.of(3, 2 * 4 * 5).toIrreductible(),
                RationalSequence.ARCSIN.apply(5).toIrreductible());
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(6));
        Assertions.assertEquals(IntRational.of(3 * 5, 2 * 4 * 6 * 7).toIrreductible(),
                RationalSequence.ARCSIN.apply(7).toIrreductible());
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(8));
        Assertions.assertEquals(IntRational.of(3 * 5 * 7, 2 * 4 * 6 * 8 * 9).toIrreductible(),
                RationalSequence.ARCSIN.apply(9).toIrreductible());
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(10));
        Assertions.assertEquals(IntRational.of(3 * 5 * 7 * 9, 2 * 4 * 6 * 8 * 10 * 11).toIrreductible(),
                RationalSequence.ARCSIN.apply(11).toIrreductible());
        Assertions.assertEquals(IntRational.ZERO, RationalSequence.ARCSIN.apply(12));
    }

    @Test
    @DisplayName("should reproduce square root (1 + x) Taylor development coeffs")
    public void sqrt_test() {

        final IntFunction<IntRational> sqrt = new RationalSequence.Pow(IntRational.of(1, 2));

        Assertions.assertEquals(IntRational.ONE, sqrt.apply(0));
        Assertions.assertEquals(IntRational.of(1, 2), sqrt.apply(1));
        Assertions.assertEquals(IntRational.of(-1, 8), sqrt.apply(2));
        Assertions.assertEquals(IntRational.of(1, 16), sqrt.apply(3).toIrreductible());
        Assertions.assertEquals(IntRational.of(-5, 128), sqrt.apply(4).toIrreductible());
    }
}
