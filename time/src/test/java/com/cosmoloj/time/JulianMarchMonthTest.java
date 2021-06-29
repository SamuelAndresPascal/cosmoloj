package com.cosmoloj.time;

import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class JulianMarchMonthTest {

    @Test
    public void testValues() {
        final JulianMarchMonth[] expResult = {
            JulianMarchMonth.MARCH,
            JulianMarchMonth.APRIL,
            JulianMarchMonth.MAY,
            JulianMarchMonth.JUNE,
            JulianMarchMonth.JULY,
            JulianMarchMonth.AUGUST,
            JulianMarchMonth.SEPTEMBER,
            JulianMarchMonth.OCTOBER,
            JulianMarchMonth.NOVEMBER,
            JulianMarchMonth.DECEMBER,
            JulianMarchMonth.JANUARY,
            JulianMarchMonth.FEBRUARY};
        final JulianMarchMonth[] result = JulianMarchMonth.values();
        Assertions.assertArrayEquals(expResult, result);
    }

    @Test
    public void testValueOf() {
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.valueOf("MARCH"));
        Assertions.assertEquals(JulianMarchMonth.APRIL, JulianMarchMonth.valueOf("APRIL"));
        Assertions.assertEquals(JulianMarchMonth.MAY, JulianMarchMonth.valueOf("MAY"));
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.valueOf("JUNE"));
        Assertions.assertEquals(JulianMarchMonth.JULY, JulianMarchMonth.valueOf("JULY"));
        Assertions.assertEquals(JulianMarchMonth.AUGUST, JulianMarchMonth.valueOf("AUGUST"));
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.valueOf("SEPTEMBER"));
        Assertions.assertEquals(JulianMarchMonth.OCTOBER, JulianMarchMonth.valueOf("OCTOBER"));
        Assertions.assertEquals(JulianMarchMonth.NOVEMBER, JulianMarchMonth.valueOf("NOVEMBER"));
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.valueOf("DECEMBER"));
        Assertions.assertEquals(JulianMarchMonth.JANUARY, JulianMarchMonth.valueOf("JANUARY"));
        Assertions.assertEquals(JulianMarchMonth.FEBRUARY, JulianMarchMonth.valueOf("FEBRUARY"));
    }

    @Test
    public void testOf() {
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.of(1));
        Assertions.assertEquals(JulianMarchMonth.APRIL, JulianMarchMonth.of(2));
        Assertions.assertEquals(JulianMarchMonth.MAY, JulianMarchMonth.of(3));
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.of(4));
        Assertions.assertEquals(JulianMarchMonth.JULY, JulianMarchMonth.of(5));
        Assertions.assertEquals(JulianMarchMonth.AUGUST, JulianMarchMonth.of(6));
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.of(7));
        Assertions.assertEquals(JulianMarchMonth.OCTOBER, JulianMarchMonth.of(8));
        Assertions.assertEquals(JulianMarchMonth.NOVEMBER, JulianMarchMonth.of(9));
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.of(10));
        Assertions.assertEquals(JulianMarchMonth.JANUARY, JulianMarchMonth.of(11));
        Assertions.assertEquals(JulianMarchMonth.FEBRUARY, JulianMarchMonth.of(12));
    }

    @Test @Disabled
    public void testFrom() {
        TemporalAccessor temporal = null;
        JulianMarchMonth expResult = null;
        JulianMarchMonth result = JulianMarchMonth.from(temporal);
        Assertions.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test
    public void testGetValue() {
        Assertions.assertEquals(1, JulianMarchMonth.MARCH.getValue());
        Assertions.assertEquals(2, JulianMarchMonth.APRIL.getValue());
        Assertions.assertEquals(3, JulianMarchMonth.MAY.getValue());
        Assertions.assertEquals(4, JulianMarchMonth.JUNE.getValue());
        Assertions.assertEquals(5, JulianMarchMonth.JULY.getValue());
        Assertions.assertEquals(6, JulianMarchMonth.AUGUST.getValue());
        Assertions.assertEquals(7, JulianMarchMonth.SEPTEMBER.getValue());
        Assertions.assertEquals(8, JulianMarchMonth.OCTOBER.getValue());
        Assertions.assertEquals(9, JulianMarchMonth.NOVEMBER.getValue());
        Assertions.assertEquals(10, JulianMarchMonth.DECEMBER.getValue());
        Assertions.assertEquals(11, JulianMarchMonth.JANUARY.getValue());
        Assertions.assertEquals(12, JulianMarchMonth.FEBRUARY.getValue());
    }

    @Test @Disabled
    public void testGetDisplayName() {
        TextStyle style = TextStyle.FULL;
        Locale locale = null;
        JulianMarchMonth instance = null;
        String expResult = "";
        String result = JulianMarchMonth.MARCH.getDisplayName(style, Locale.ENGLISH);
        Assertions.assertEquals("MARCH", result);
    }

    @Test @Disabled
    public void testRange() {
        Assertions.assertEquals(ValueRange.of(1, 31), JulianMarchMonth.MARCH.range(ChronoField.MONTH_OF_YEAR));
    }

    @Test
    public void testPlus() {
        Assertions.assertEquals(JulianMarchMonth.APRIL, JulianMarchMonth.MARCH.plus(1));
        Assertions.assertEquals(JulianMarchMonth.MAY, JulianMarchMonth.APRIL.plus(1));
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.MAY.plus(1));
        Assertions.assertEquals(JulianMarchMonth.JULY, JulianMarchMonth.JUNE.plus(1));
        Assertions.assertEquals(JulianMarchMonth.AUGUST, JulianMarchMonth.JULY.plus(1));
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.AUGUST.plus(1));
        Assertions.assertEquals(JulianMarchMonth.OCTOBER, JulianMarchMonth.SEPTEMBER.plus(1));
        Assertions.assertEquals(JulianMarchMonth.NOVEMBER, JulianMarchMonth.OCTOBER.plus(1));
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.NOVEMBER.plus(1));
        Assertions.assertEquals(JulianMarchMonth.JANUARY, JulianMarchMonth.DECEMBER.plus(1));
        Assertions.assertEquals(JulianMarchMonth.FEBRUARY, JulianMarchMonth.JANUARY.plus(1));
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.FEBRUARY.plus(1));
    }

    @Test
    public void testMinus() {
        Assertions.assertEquals(JulianMarchMonth.FEBRUARY, JulianMarchMonth.MARCH.minus(1));
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.APRIL.minus(1));
        Assertions.assertEquals(JulianMarchMonth.APRIL, JulianMarchMonth.MAY.minus(1));
        Assertions.assertEquals(JulianMarchMonth.MAY, JulianMarchMonth.JUNE.minus(1));
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.JULY.minus(1));
        Assertions.assertEquals(JulianMarchMonth.JULY, JulianMarchMonth.AUGUST.minus(1));
        Assertions.assertEquals(JulianMarchMonth.AUGUST, JulianMarchMonth.SEPTEMBER.minus(1));
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.OCTOBER.minus(1));
        Assertions.assertEquals(JulianMarchMonth.OCTOBER, JulianMarchMonth.NOVEMBER.minus(1));
        Assertions.assertEquals(JulianMarchMonth.NOVEMBER, JulianMarchMonth.DECEMBER.minus(1));
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.JANUARY.minus(1));
        Assertions.assertEquals(JulianMarchMonth.JANUARY, JulianMarchMonth.FEBRUARY.minus(1));
    }

    @Test
    public void testLength() {
        Assertions.assertEquals(31, JulianMarchMonth.MARCH.length(true));
        Assertions.assertEquals(30, JulianMarchMonth.APRIL.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.MAY.length(true));
        Assertions.assertEquals(30, JulianMarchMonth.JUNE.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.JULY.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.AUGUST.length(true));
        Assertions.assertEquals(30, JulianMarchMonth.SEPTEMBER.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.OCTOBER.length(true));
        Assertions.assertEquals(30, JulianMarchMonth.NOVEMBER.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.DECEMBER.length(true));
        Assertions.assertEquals(31, JulianMarchMonth.JANUARY.length(true));
        Assertions.assertEquals(29, JulianMarchMonth.FEBRUARY.length(true));

        Assertions.assertEquals(31, JulianMarchMonth.MARCH.length(false));
        Assertions.assertEquals(30, JulianMarchMonth.APRIL.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.MAY.length(false));
        Assertions.assertEquals(30, JulianMarchMonth.JUNE.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.JULY.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.AUGUST.length(false));
        Assertions.assertEquals(30, JulianMarchMonth.SEPTEMBER.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.OCTOBER.length(false));
        Assertions.assertEquals(30, JulianMarchMonth.NOVEMBER.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.DECEMBER.length(false));
        Assertions.assertEquals(31, JulianMarchMonth.JANUARY.length(false));
        Assertions.assertEquals(28, JulianMarchMonth.FEBRUARY.length(false));
    }

    @Test
    public void testMinLength() {
        Assertions.assertEquals(31, JulianMarchMonth.MARCH.minLength());
        Assertions.assertEquals(30, JulianMarchMonth.APRIL.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.MAY.minLength());
        Assertions.assertEquals(30, JulianMarchMonth.JUNE.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.JULY.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.AUGUST.minLength());
        Assertions.assertEquals(30, JulianMarchMonth.SEPTEMBER.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.OCTOBER.minLength());
        Assertions.assertEquals(30, JulianMarchMonth.NOVEMBER.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.DECEMBER.minLength());
        Assertions.assertEquals(31, JulianMarchMonth.JANUARY.minLength());
        Assertions.assertEquals(28, JulianMarchMonth.FEBRUARY.minLength());
    }

    @Test
    public void testMaxLength() {
        Assertions.assertEquals(31, JulianMarchMonth.MARCH.maxLength());
        Assertions.assertEquals(30, JulianMarchMonth.APRIL.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.MAY.maxLength());
        Assertions.assertEquals(30, JulianMarchMonth.JUNE.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.JULY.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.AUGUST.maxLength());
        Assertions.assertEquals(30, JulianMarchMonth.SEPTEMBER.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.OCTOBER.maxLength());
        Assertions.assertEquals(30, JulianMarchMonth.NOVEMBER.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.DECEMBER.maxLength());
        Assertions.assertEquals(31, JulianMarchMonth.JANUARY.maxLength());
        Assertions.assertEquals(29, JulianMarchMonth.FEBRUARY.maxLength());
    }

    @Test
    public void testFirstDayOfYear() {
        Assertions.assertEquals(1, JulianMarchMonth.MARCH.firstDayOfYear(true));
    }

    @Test
    public void testFirstMonthOfQuarter() {
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.MARCH.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.APRIL.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.MARCH, JulianMarchMonth.MAY.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.JUNE.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.JULY.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.JUNE, JulianMarchMonth.AUGUST.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.SEPTEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.OCTOBER.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.SEPTEMBER, JulianMarchMonth.NOVEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.DECEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.JANUARY.firstMonthOfQuarter());
        Assertions.assertEquals(JulianMarchMonth.DECEMBER, JulianMarchMonth.FEBRUARY.firstMonthOfQuarter());
    }
}
