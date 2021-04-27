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
        return switch (era) {
            case 0 -> BO;
            case 1 -> AO;
            default -> throw new DateTimeException("Invalid era: " + era);
        };
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
