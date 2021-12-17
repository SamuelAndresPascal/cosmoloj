package com.cosmoloj.time;

import com.cosmoloj.time.chrono.GregorianChronology;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class GregorianChronologyTest {

    private static final GregorianChronology INSTANCE = GregorianChronology.INSTANCE;

    @Test
    public void isLeapYear() {
        Assertions.assertTrue(INSTANCE.isLeapYear(2024));
        Assertions.assertFalse(INSTANCE.isLeapYear(2023));
        Assertions.assertFalse(INSTANCE.isLeapYear(2022));
        Assertions.assertFalse(INSTANCE.isLeapYear(2021));
        Assertions.assertTrue(INSTANCE.isLeapYear(2020));
    }

}
