package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.DerivedUnit;
import com.cosmoloj.unit.simple.api.Factor;
import com.cosmoloj.unit.simple.api.UnitConverter;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public final class SimpleDerivedUnit extends SimpleUnit implements DerivedUnit {

    private final List<Factor> definition;

    private SimpleDerivedUnit(final List<Factor> def) {
        this.definition = Collections.unmodifiableList(def);
    }

    @Override
    public List<Factor> definition() {
        return definition;
    }

    @Override
    public UnitConverter toBase() {
        /*
        En combinaison avec d'autres unités, il ne faut plus appliquer de décalage d'origine d'échelle (température)
        mais uniquement appliquer le facteur d'échelle.
        */
        UnitConverter transform = SimpleUnitConverter.identity();
        for (final Factor factor : this.definition) {
          transform = factor.dim().toBase().linearPow(factor.power()).concatenate(transform);
        }
        return transform;
    }

    public static DerivedUnit of(final List<Factor> def) {
        return new SimpleDerivedUnit(def);
    }

    public static DerivedUnit of(final Factor... def) {
        return of(List.of(def));
    }
}
