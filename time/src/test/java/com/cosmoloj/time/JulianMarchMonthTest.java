package com.cosmoloj.time;

import com.cosmoloj.time.JulianMarchMonth;
import static com.cosmoloj.time.JulianMarchMonth.APRIL;
import static com.cosmoloj.time.JulianMarchMonth.AUGUST;
import static com.cosmoloj.time.JulianMarchMonth.DECEMBER;
import static com.cosmoloj.time.JulianMarchMonth.FEBRUARY;
import static com.cosmoloj.time.JulianMarchMonth.JANUARY;
import static com.cosmoloj.time.JulianMarchMonth.JULY;
import static com.cosmoloj.time.JulianMarchMonth.JUNE;
import static com.cosmoloj.time.JulianMarchMonth.MARCH;
import static com.cosmoloj.time.JulianMarchMonth.MAY;
import static com.cosmoloj.time.JulianMarchMonth.NOVEMBER;
import static com.cosmoloj.time.JulianMarchMonth.OCTOBER;
import static com.cosmoloj.time.JulianMarchMonth.SEPTEMBER;
import java.time.format.TextStyle;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
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
        final JulianMarchMonth[] expResult
                = {MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, JANUARY, FEBRUARY};
        final JulianMarchMonth[] result = JulianMarchMonth.values();
        Assertions.assertArrayEquals(expResult, result);
    }

    @Test
    public void testValueOf() {
        Assertions.assertEquals(MARCH, JulianMarchMonth.valueOf("MARCH"));
        Assertions.assertEquals(APRIL, JulianMarchMonth.valueOf("APRIL"));
        Assertions.assertEquals(MAY, JulianMarchMonth.valueOf("MAY"));
        Assertions.assertEquals(JUNE, JulianMarchMonth.valueOf("JUNE"));
        Assertions.assertEquals(JULY, JulianMarchMonth.valueOf("JULY"));
        Assertions.assertEquals(AUGUST, JulianMarchMonth.valueOf("AUGUST"));
        Assertions.assertEquals(SEPTEMBER, JulianMarchMonth.valueOf("SEPTEMBER"));
        Assertions.assertEquals(OCTOBER, JulianMarchMonth.valueOf("OCTOBER"));
        Assertions.assertEquals(NOVEMBER, JulianMarchMonth.valueOf("NOVEMBER"));
        Assertions.assertEquals(DECEMBER, JulianMarchMonth.valueOf("DECEMBER"));
        Assertions.assertEquals(JANUARY, JulianMarchMonth.valueOf("JANUARY"));
        Assertions.assertEquals(FEBRUARY, JulianMarchMonth.valueOf("FEBRUARY"));
    }

    @Test
    public void testOf() {
        Assertions.assertEquals(MARCH, JulianMarchMonth.of(1));
        Assertions.assertEquals(APRIL, JulianMarchMonth.of(2));
        Assertions.assertEquals(MAY, JulianMarchMonth.of(3));
        Assertions.assertEquals(JUNE, JulianMarchMonth.of(4));
        Assertions.assertEquals(JULY, JulianMarchMonth.of(5));
        Assertions.assertEquals(AUGUST, JulianMarchMonth.of(6));
        Assertions.assertEquals(SEPTEMBER, JulianMarchMonth.of(7));
        Assertions.assertEquals(OCTOBER, JulianMarchMonth.of(8));
        Assertions.assertEquals(NOVEMBER, JulianMarchMonth.of(9));
        Assertions.assertEquals(DECEMBER, JulianMarchMonth.of(10));
        Assertions.assertEquals(JANUARY, JulianMarchMonth.of(11));
        Assertions.assertEquals(FEBRUARY, JulianMarchMonth.of(12));
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
        Assertions.assertEquals(1, MARCH.getValue());
        Assertions.assertEquals(2, APRIL.getValue());
        Assertions.assertEquals(3, MAY.getValue());
        Assertions.assertEquals(4, JUNE.getValue());
        Assertions.assertEquals(5, JULY.getValue());
        Assertions.assertEquals(6, AUGUST.getValue());
        Assertions.assertEquals(7, SEPTEMBER.getValue());
        Assertions.assertEquals(8, OCTOBER.getValue());
        Assertions.assertEquals(9, NOVEMBER.getValue());
        Assertions.assertEquals(10, DECEMBER.getValue());
        Assertions.assertEquals(11, JANUARY.getValue());
        Assertions.assertEquals(12, FEBRUARY.getValue());
    }

    @Test @Disabled
    public void testGetDisplayName() {
        TextStyle style = TextStyle.FULL;
        Locale locale = null;
        JulianMarchMonth instance = null;
        String expResult = "";
        String result = MARCH.getDisplayName(style, Locale.ENGLISH);
        Assertions.assertEquals("MARCH", result);
    }

    @Test @Disabled
    public void testRange() {
        Assertions.assertEquals(ValueRange.of(1, 31), MARCH.range(MONTH_OF_YEAR));
    }

    @Test
    public void testPlus() {
        Assertions.assertEquals(APRIL, MARCH.plus(1));
        Assertions.assertEquals(MAY, APRIL.plus(1));
        Assertions.assertEquals(JUNE, MAY.plus(1));
        Assertions.assertEquals(JULY, JUNE.plus(1));
        Assertions.assertEquals(AUGUST, JULY.plus(1));
        Assertions.assertEquals(SEPTEMBER, AUGUST.plus(1));
        Assertions.assertEquals(OCTOBER, SEPTEMBER.plus(1));
        Assertions.assertEquals(NOVEMBER, OCTOBER.plus(1));
        Assertions.assertEquals(DECEMBER, NOVEMBER.plus(1));
        Assertions.assertEquals(JANUARY, DECEMBER.plus(1));
        Assertions.assertEquals(FEBRUARY, JANUARY.plus(1));
        Assertions.assertEquals(MARCH, FEBRUARY.plus(1));
    }

    @Test
    public void testMinus() {
        Assertions.assertEquals(FEBRUARY, MARCH.minus(1));
        Assertions.assertEquals(MARCH, APRIL.minus(1));
        Assertions.assertEquals(APRIL, MAY.minus(1));
        Assertions.assertEquals(MAY, JUNE.minus(1));
        Assertions.assertEquals(JUNE, JULY.minus(1));
        Assertions.assertEquals(JULY, AUGUST.minus(1));
        Assertions.assertEquals(AUGUST, SEPTEMBER.minus(1));
        Assertions.assertEquals(SEPTEMBER, OCTOBER.minus(1));
        Assertions.assertEquals(OCTOBER, NOVEMBER.minus(1));
        Assertions.assertEquals(NOVEMBER, DECEMBER.minus(1));
        Assertions.assertEquals(DECEMBER, JANUARY.minus(1));
        Assertions.assertEquals(JANUARY, FEBRUARY.minus(1));
    }

    @Test
    public void testLength() {
        Assertions.assertEquals(31, MARCH.length(true));
        Assertions.assertEquals(30, APRIL.length(true));
        Assertions.assertEquals(31, MAY.length(true));
        Assertions.assertEquals(30, JUNE.length(true));
        Assertions.assertEquals(31, JULY.length(true));
        Assertions.assertEquals(31, AUGUST.length(true));
        Assertions.assertEquals(30, SEPTEMBER.length(true));
        Assertions.assertEquals(31, OCTOBER.length(true));
        Assertions.assertEquals(30, NOVEMBER.length(true));
        Assertions.assertEquals(31, DECEMBER.length(true));
        Assertions.assertEquals(31, JANUARY.length(true));
        Assertions.assertEquals(29, FEBRUARY.length(true));

        Assertions.assertEquals(31, MARCH.length(false));
        Assertions.assertEquals(30, APRIL.length(false));
        Assertions.assertEquals(31, MAY.length(false));
        Assertions.assertEquals(30, JUNE.length(false));
        Assertions.assertEquals(31, JULY.length(false));
        Assertions.assertEquals(31, AUGUST.length(false));
        Assertions.assertEquals(30, SEPTEMBER.length(false));
        Assertions.assertEquals(31, OCTOBER.length(false));
        Assertions.assertEquals(30, NOVEMBER.length(false));
        Assertions.assertEquals(31, DECEMBER.length(false));
        Assertions.assertEquals(31, JANUARY.length(false));
        Assertions.assertEquals(28, FEBRUARY.length(false));
    }

    @Test
    public void testMinLength() {
        Assertions.assertEquals(31, MARCH.minLength());
        Assertions.assertEquals(30, APRIL.minLength());
        Assertions.assertEquals(31, MAY.minLength());
        Assertions.assertEquals(30, JUNE.minLength());
        Assertions.assertEquals(31, JULY.minLength());
        Assertions.assertEquals(31, AUGUST.minLength());
        Assertions.assertEquals(30, SEPTEMBER.minLength());
        Assertions.assertEquals(31, OCTOBER.minLength());
        Assertions.assertEquals(30, NOVEMBER.minLength());
        Assertions.assertEquals(31, DECEMBER.minLength());
        Assertions.assertEquals(31, JANUARY.minLength());
        Assertions.assertEquals(28, FEBRUARY.minLength());
    }

    @Test
    public void testMaxLength() {
        Assertions.assertEquals(31, MARCH.maxLength());
        Assertions.assertEquals(30, APRIL.maxLength());
        Assertions.assertEquals(31, MAY.maxLength());
        Assertions.assertEquals(30, JUNE.maxLength());
        Assertions.assertEquals(31, JULY.maxLength());
        Assertions.assertEquals(31, AUGUST.maxLength());
        Assertions.assertEquals(30, SEPTEMBER.maxLength());
        Assertions.assertEquals(31, OCTOBER.maxLength());
        Assertions.assertEquals(30, NOVEMBER.maxLength());
        Assertions.assertEquals(31, DECEMBER.maxLength());
        Assertions.assertEquals(31, JANUARY.maxLength());
        Assertions.assertEquals(29, FEBRUARY.maxLength());
    }

    @Test
    public void testFirstDayOfYear() {
        Assertions.assertEquals(1, MARCH.firstDayOfYear(true));
    }

    @Test
    public void testFirstMonthOfQuarter() {
        Assertions.assertEquals(MARCH, MARCH.firstMonthOfQuarter());
        Assertions.assertEquals(MARCH, APRIL.firstMonthOfQuarter());
        Assertions.assertEquals(MARCH, MAY.firstMonthOfQuarter());
        Assertions.assertEquals(JUNE, JUNE.firstMonthOfQuarter());
        Assertions.assertEquals(JUNE, JULY.firstMonthOfQuarter());
        Assertions.assertEquals(JUNE, AUGUST.firstMonthOfQuarter());
        Assertions.assertEquals(SEPTEMBER, SEPTEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(SEPTEMBER, OCTOBER.firstMonthOfQuarter());
        Assertions.assertEquals(SEPTEMBER, NOVEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(DECEMBER, DECEMBER.firstMonthOfQuarter());
        Assertions.assertEquals(DECEMBER, JANUARY.firstMonthOfQuarter());
        Assertions.assertEquals(DECEMBER, FEBRUARY.firstMonthOfQuarter());
    }
}
