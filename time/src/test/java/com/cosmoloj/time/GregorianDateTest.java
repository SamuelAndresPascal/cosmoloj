package com.cosmoloj.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class GregorianDateTest {

    @Test
    public void testOf0() {
        final LocalDate local = LocalDate.of(2016, Month.JANUARY, 16);
        final GregorianDate gregorian = GregorianDate.ofEpochDay(local.toEpochDay());
        Assertions.assertEquals(16, gregorian.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, gregorian.getMonth());
        Assertions.assertEquals(2016, gregorian.getYear());
    }

    @Test
    public void regressionFalseBissextile() {
        final GregorianDate date1 = GregorianDate.of(2022, Month.FEBRUARY, 28);

        final GregorianDate date2 = date1.plusDays(1);
        Assertions.assertEquals(1, date2.getDayOfMonth());
        Assertions.assertEquals(3, date2.getMonthValue());
        Assertions.assertEquals(Month.MARCH, date2.getMonth());
        Assertions.assertEquals(2022, date2.getYear());

        final GregorianDate date3 = date1.plusDays(2);
        Assertions.assertEquals(2, date3.getDayOfMonth());
        Assertions.assertEquals(3, date3.getMonthValue());
        Assertions.assertEquals(Month.MARCH, date3.getMonth());
        Assertions.assertEquals(2022, date3.getYear());
    }

    @Test
    public void testOfEpochDay() {
        for (long i = -1000000; i <= 1000000; i++) {
            final LocalDate local = LocalDate.ofEpochDay(i);
            final GregorianDate gregorian = GregorianDate.ofEpochDay(i);
            Assertions.assertEquals(local.getDayOfMonth(), gregorian.getDayOfMonth());
            Assertions.assertEquals(local.getMonth(), gregorian.getMonth());
            Assertions.assertEquals(local.getYear(), gregorian.getYear());
        }
    }

    @Test
    public void testToEpochDay() {
        Assertions.assertEquals(0, GregorianDate.of(1970, 1, 1).toEpochDay());
        Assertions.assertEquals(-GregorianDate.DAYS_0000_TO_1970, GregorianDate.of(0000, 1, 1).toEpochDay());
        Assertions.assertEquals(-GregorianDate.DAYS_0000_TO_1970 - 365, GregorianDate.of(-1, 1, 1).toEpochDay());
    }

    @Test
    public void testDayOfYear() {
        Assertions.assertEquals(1, GregorianDate.of(2016, 1, 1).getDayOfYear());
        Assertions.assertEquals(61, GregorianDate.of(2016, 3, 1).getDayOfYear());
        Assertions.assertEquals(1, GregorianDate.of(2015, 1, 1).getDayOfYear());
        Assertions.assertEquals(60, GregorianDate.of(2015, 3, 1).getDayOfYear());

        for (long i = -1000000; i <= 1000000; i++) {
            Assertions.assertEquals(LocalDate.ofEpochDay(i).getDayOfYear(), GregorianDate.ofEpochDay(i).getDayOfYear());
        }
    }

    @Test
    public void testDayOfYear2() {
        Assertions.assertEquals(1, GregorianDate.of(2016, 1, 1).getDayOfYear2());
        Assertions.assertEquals(61, GregorianDate.of(2016, 3, 1).getDayOfYear2());
        Assertions.assertEquals(1, GregorianDate.of(2015, 1, 1).getDayOfYear2());
        Assertions.assertEquals(60, GregorianDate.of(2015, 3, 1).getDayOfYear2());

        for (long i = -1000000; i <= 1000000; i++) {
            Assertions.assertEquals(
                    LocalDate.ofEpochDay(i).getDayOfYear(), GregorianDate.ofEpochDay(i).getDayOfYear2());
        }
    }

    @Test
    public void testDayOfWeek() {
        Assertions.assertEquals(DayOfWeek.THURSDAY, GregorianDate.of(1970, 1, 1).getDayOfWeek());
        Assertions.assertEquals(LocalDate.now().getDayOfWeek(), GregorianDate.from(LocalDate.now()).getDayOfWeek());
    }

    @Test
    public void testLengthOfMonth() {
        Assertions.assertEquals(31, GregorianDate.of(2016, 1, 1).lengthOfMonth());

        Assertions.assertEquals(29, GregorianDate.of(2016, 2, 1).lengthOfMonth());
        Assertions.assertEquals(28, GregorianDate.of(2015, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, GregorianDate.of(2000, 2, 1).lengthOfMonth());
        Assertions.assertEquals(28, GregorianDate.of(1900, 2, 1).lengthOfMonth());
        Assertions.assertEquals(28, GregorianDate.of(1700, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, GregorianDate.of(1600, 2, 1).lengthOfMonth());
        Assertions.assertEquals(28, GregorianDate.of(1500, 2, 1).lengthOfMonth());

        Assertions.assertEquals(31, GregorianDate.of(2016, 3, 1).lengthOfMonth());
        Assertions.assertEquals(30, GregorianDate.of(2016, 4, 1).lengthOfMonth());
        Assertions.assertEquals(31, GregorianDate.of(2016, 5, 1).lengthOfMonth());
        Assertions.assertEquals(30, GregorianDate.of(2016, 6, 1).lengthOfMonth());
        Assertions.assertEquals(31, GregorianDate.of(2016, 7, 1).lengthOfMonth());
        Assertions.assertEquals(31, GregorianDate.of(2016, 8, 1).lengthOfMonth());
        Assertions.assertEquals(30, GregorianDate.of(2016, 9, 1).lengthOfMonth());
        Assertions.assertEquals(31, GregorianDate.of(2016, 10, 1).lengthOfMonth());
        Assertions.assertEquals(30, GregorianDate.of(2016, 11, 1).lengthOfMonth());
        Assertions.assertEquals(31, GregorianDate.of(2016, 12, 1).lengthOfMonth());
    }

    @Test
    public void testLengthOfYear() {
        Assertions.assertEquals(366, GregorianDate.of(2000, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, GregorianDate.of(2000, 1, 1).lengthOfYear());
        Assertions.assertEquals(365, GregorianDate.of(1900, 12, 31).lengthOfYear());
        Assertions.assertEquals(365, GregorianDate.of(1900, 1, 1).lengthOfYear());
        Assertions.assertEquals(366, GregorianDate.of(2016, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, GregorianDate.of(2016, 1, 1).lengthOfYear());
        Assertions.assertEquals(365, GregorianDate.of(2015, 12, 31).lengthOfYear());
        Assertions.assertEquals(365, GregorianDate.of(2015, 1, 1).lengthOfYear());
    }
}
