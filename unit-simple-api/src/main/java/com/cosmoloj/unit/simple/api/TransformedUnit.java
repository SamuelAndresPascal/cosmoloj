package com.cosmoloj.unit.simple.api;

/**
 *
 * @author Samuel Andrés
 */
public interface TransformedUnit extends Unit {

    Unit reference();

    UnitConverter toReference();

    @Override
    default UnitConverter toBase() {
      return this.reference().toBase().concatenate(this.toReference());
    }
}
