package com.cosmoloj.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

/**
 *
 * @author Samuel Andrés
 */
public enum RomaicEra implements Era {

    BAM, AM;

    public static RomaicEra of(final int era) {
        return switch (era) {
            case 0 -> BAM;
            case 1 -> AM;
            default -> throw new DateTimeException("Invalid era: " + era);
        };
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
