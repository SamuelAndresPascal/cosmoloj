package com.cosmoloj.unit.simple.api;

/**
 *
 * @author Samuel Andrés
 */
public interface Factor {

    Unit dim();

    int numerator();

    int denominator();

    default double power() {
      return denominator() == 1 ? numerator() : ((double) numerator()) / denominator();
    }
}
