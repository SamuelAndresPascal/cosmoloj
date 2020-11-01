package com.cosmoloj.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

/**
 *
 * @author Samuel Andrés
 */
public enum JulianDayEra implements Era {

    BO, AO;

    public static JulianDayEra of(final int era) {
        switch (era) {
            case 0:
                return BO;
            case 1:
                return AO;
            default:
                throw new DateTimeException("Invalid era: " + era);
        }
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
