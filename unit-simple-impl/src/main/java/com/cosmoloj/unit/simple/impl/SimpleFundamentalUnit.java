package com.cosmoloj.unit.simple.impl;

import com.cosmoloj.unit.simple.api.FundamentalUnit;
import com.cosmoloj.unit.simple.api.UnitConverter;

/**
 *
 * @author Samuel Andrés
 */
public class SimpleFundamentalUnit extends SimpleUnit implements FundamentalUnit {

    @Override
    public UnitConverter toBase() {
        return SimpleUnitConverter.identity();
    }
}
