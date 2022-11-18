package com.cosmoloj.unit.simple.api;

/**
 *
 * @author Samuel Andrés
 */
public interface Unit extends Factor {

    default UnitConverter getConverterTo(final Unit target) {
        return target.toBase().inverse().concatenate(toBase());
    }

    UnitConverter toBase();

    @Override
    default Unit dim() {
        return this;
    }

    @Override
    default int numerator() {
        return 1;
    }

    @Override
    default int denominator() {
        return 1;
    }

    TransformedUnit shift(double value);

    TransformedUnit scaleMultiply(double value);

    TransformedUnit scaleDivide(double value);

    Factor factor(int numerator, int denominator);

    Factor factor(int numerator);
}
