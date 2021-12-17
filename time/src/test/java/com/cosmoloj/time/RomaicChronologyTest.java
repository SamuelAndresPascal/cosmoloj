package com.cosmoloj.time;

import com.cosmoloj.time.chrono.RomaicChronology;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class RomaicChronologyTest {

    private static final RomaicChronology INSTANCE = RomaicChronology.INSTANCE;

    @Test
    public void isLeapYear() {
        Assertions.assertTrue(INSTANCE.isLeapYear(7532)); // février 2024
        Assertions.assertFalse(INSTANCE.isLeapYear(7531)); // février 2023
        Assertions.assertFalse(INSTANCE.isLeapYear(7530)); // février 2022
        Assertions.assertFalse(INSTANCE.isLeapYear(7529)); // février 2021
        Assertions.assertTrue(INSTANCE.isLeapYear(7528)); // février 2020
    }

}
