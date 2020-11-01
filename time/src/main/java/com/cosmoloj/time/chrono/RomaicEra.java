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
        switch (era) {
            case 0:
                return BAM;
            case 1:
                return AM;
            default:
                throw new DateTimeException("Invalid era: " + era);
        }
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
