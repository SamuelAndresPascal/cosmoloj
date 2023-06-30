package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.Factor;
import com.cosmoloj.unit.simple.api.TransformedUnit;
import com.cosmoloj.unit.simple.api.Unit;

/**
 *
 * @author Samuel Andrés
 */
public abstract class SimpleUnit implements Unit {

  @Override
  public TransformedUnit shift(final double value) {
    return SimpleTransformedUnit.of(SimpleUnitConverter.translation(value), this);
  }

  @Override
  public TransformedUnit scaleMultiply(final double value) {
    return SimpleTransformedUnit.of(SimpleUnitConverter.linear(value), this);
  }

  @Override
  public TransformedUnit scaleDivide(final double value) {
    return this.scaleMultiply(1.0 / value);
  }

  @Override
  public Factor factor(final int numerator, final int denominator) {
    return SimpleFactor.of(this, numerator, denominator);
  }

  @Override
  public Factor factor(final int numerator) {
    return SimpleFactor.of(this, numerator);
  }
}
