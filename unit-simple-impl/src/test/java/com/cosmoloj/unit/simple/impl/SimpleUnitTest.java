package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.Unit;
import com.cosmoloj.unit.simple.api.UnitConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public final class SimpleUnitTest {

    private SimpleUnitTest() {
    }

    @Test
    public void transformedUnitConversion() {
        final Unit m = new SimpleFundamentalUnit();
        final Unit km = m.scaleMultiply(1000);
        final Unit cm = m.scaleDivide(100);
        final UnitConverter cmToKm = cm.getConverterTo(km);

        Assertions.assertEquals(0.00003, cmToKm.convert(3.), 1e-10);
        Assertions.assertEquals(3., cmToKm.inverse().convert(0.00003), 1e-10);
    }

    @Test
    public void derivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit();
        final Unit km = m.scaleMultiply(1000);

        final Unit km2 = SimpleDerivedUnit.of(km.factor(2));
        final Unit cm = m.scaleDivide(100);
        final Unit cm2 = SimpleDerivedUnit.of(cm.factor(2));
        final UnitConverter km2Tocm2 = km2.getConverterTo(cm2);

        Assertions.assertEquals(30000000000., km2Tocm2.convert(3.), 1e-10);
        Assertions.assertEquals(3., km2Tocm2.inverse().convert(30000000000.), 1e-10);
    }

    @Test
    public void combinedDimensionDerivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit();
        final Unit kg = new SimpleFundamentalUnit();
        final Unit g = kg.scaleDivide(1000);
        final Unit ton = kg.scaleMultiply(1000);
        final Unit gPerM2 = SimpleDerivedUnit.of(g, m.factor(-2));
        final Unit km = m.scaleMultiply(1000);
        final Unit tonPerKm2 = SimpleDerivedUnit.of(ton, km.factor(-2));
        final Unit cm = m.scaleDivide(100);
        final Unit tonPerCm2 = SimpleDerivedUnit.of(ton, cm.factor(-2));
        final UnitConverter gPerM2ToTonPerKm2 = gPerM2.getConverterTo(tonPerKm2);
        final UnitConverter gPerM2ToTonPerCm2 = gPerM2.getConverterTo(tonPerCm2);

        Assertions.assertEquals(1., gPerM2ToTonPerKm2.convert(1.), 1e-10);
        Assertions.assertEquals(3., gPerM2ToTonPerKm2.inverse().convert(3.), 1e-10);
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.convert(1.), 1e-20);
        Assertions.assertEquals(3e-10, gPerM2ToTonPerCm2.convert(3.), 1e-20);
        Assertions.assertEquals(0., gPerM2ToTonPerCm2.offset());
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.scale(), 1e-10);
        Assertions.assertEquals(-0., gPerM2ToTonPerCm2.inverse().offset());
        Assertions.assertEquals(3., gPerM2ToTonPerCm2.inverse().convert(3e-10), 1e-10);
    }

    @Test
    public void temperatures() {

        final Unit k = new SimpleFundamentalUnit();
        final Unit c = k.shift(273.15);
        final UnitConverter kToC = k.getConverterTo(c);

        Assertions.assertEquals(-273.15, kToC.convert(0), 1e-10);
        Assertions.assertEquals(273.15, kToC.inverse().convert(0), 1e-10);

        // en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
        final Unit m = new SimpleFundamentalUnit();
        final Unit cPerM = SimpleDerivedUnit.of(c, m.factor(-1));
        final Unit kPerM = SimpleDerivedUnit.of(k, m.factor(-1));
        final UnitConverter kPerMToCPerM = kPerM.getConverterTo(cPerM);
        Assertions.assertEquals(3., kPerMToCPerM.convert(3.), 1e-10);
        Assertions.assertEquals(3., kPerMToCPerM.inverse().convert(3.), 1e-10);
    }
}
