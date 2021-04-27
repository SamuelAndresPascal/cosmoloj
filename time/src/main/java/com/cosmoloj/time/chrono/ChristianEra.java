package com.cosmoloj.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

/**
 *
 * @author Samuel Andrés
 */
public enum ChristianEra implements Era {

    BC, AD;

    public static ChristianEra of(final int era) {
        return switch (era) {
            case 0 -> BC;
            case 1 -> AD;
            default -> throw new DateTimeException("Invalid era: " + era);
        };
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
