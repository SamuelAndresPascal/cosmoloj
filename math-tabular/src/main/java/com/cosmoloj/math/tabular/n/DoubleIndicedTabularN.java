package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public interface DoubleIndicedTabularN extends IndicedTabularN, DoubleTabularN {

    double get(NamedIndex... component);
}
