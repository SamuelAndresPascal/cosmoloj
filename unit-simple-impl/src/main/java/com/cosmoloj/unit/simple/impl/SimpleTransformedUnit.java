package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.TransformedUnit;
import com.cosmoloj.unit.simple.api.Unit;
import com.cosmoloj.unit.simple.api.UnitConverter;

/**
 *
 * @author Samuel Andrés
 */
public final class SimpleTransformedUnit extends SimpleUnit implements TransformedUnit {

    private final Unit reference;
    private final UnitConverter toReference;

    private SimpleTransformedUnit(final UnitConverter toReference, final Unit reference) {
        this.toReference = toReference;
        this.reference = reference;
    }

    @Override
    public Unit reference() {
        return reference;
    }

    @Override
    public UnitConverter toReference() {
        return toReference;
    }

    public static TransformedUnit of(final UnitConverter toReference, final Unit reference) {
        return new SimpleTransformedUnit(toReference, reference);
    }
}
