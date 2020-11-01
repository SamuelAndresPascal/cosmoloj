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
        switch (era) {
            case 0:
                return BC;
            case 1:
                return AD;
            default:
                throw new DateTimeException("Invalid era: " + era);
        }
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
