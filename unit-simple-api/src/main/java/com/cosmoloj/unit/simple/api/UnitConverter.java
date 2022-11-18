package com.cosmoloj.unit.simple.api;

/**
 *
 * @author Samuel Andrés
 */
public interface UnitConverter {

    double scale();
    double offset();
    UnitConverter inverse();
    UnitConverter linear();
    UnitConverter linearPow(double pow);
    double convert(double value);
    UnitConverter concatenate(UnitConverter unitConverter);
}
