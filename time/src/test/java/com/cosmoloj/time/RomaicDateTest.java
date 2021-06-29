package com.cosmoloj.time;

import com.cosmoloj.time.chrono.RomaicChronology;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.IsoChronology;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class RomaicDateTest {

    @Test
    public void testOf0() {
        final LocalDate local = LocalDate.of(2016, Month.JANUARY, 16);
        final RomaicDate romaic = RomaicDate.ofEpochDay(local.toEpochDay());
        Assertions.assertEquals(3, romaic.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic.getMonth());
        Assertions.assertEquals(7524, romaic.getYear());

        Assertions.assertTrue(IsoChronology.INSTANCE.isLeapYear(2016));
        Assertions.assertTrue(RomaicChronology.INSTANCE.isLeapYear(7524));


        Assertions.assertTrue(IsoChronology.INSTANCE.isLeapYear(4));
        Assertions.assertTrue(RomaicChronology.INSTANCE.isLeapYear(5512));
        Assertions.assertTrue(IsoChronology.INSTANCE.isLeapYear(0));
        Assertions.assertTrue(RomaicChronology.INSTANCE.isLeapYear(5508));
        Assertions.assertTrue(IsoChronology.INSTANCE.isLeapYear(-4));
        Assertions.assertTrue(RomaicChronology.INSTANCE.isLeapYear(5504));
        Assertions.assertTrue(RomaicChronology.INSTANCE.isLeapYear(-5500));
    }

    @Test @Disabled
    public void testOfEpochDay() {

        final RomaicDate romaic1 = RomaicDate.ofEpochDay(LocalDate.of(2016, Month.JANUARY, 16).toEpochDay());
        Assertions.assertEquals(3, romaic1.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic1.getMonth());
        Assertions.assertEquals(2016, romaic1.getYear());

        final RomaicDate romaic2200 = RomaicDate.ofEpochDay(LocalDate.of(2200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(18, romaic2200.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic2200.getMonth());
        Assertions.assertEquals(2199, romaic2200.getYear());

        final RomaicDate romaic2100 = RomaicDate.ofEpochDay(LocalDate.of(2100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(19, romaic2100.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic2100.getMonth());
        Assertions.assertEquals(2099, romaic2100.getYear());

        final RomaicDate romaic2000 = RomaicDate.ofEpochDay(LocalDate.of(2000, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(19, romaic2000.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic2000.getMonth());
        Assertions.assertEquals(1999, romaic2000.getYear());

        final RomaicDate romaic1900 = RomaicDate.ofEpochDay(LocalDate.of(1900, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(20, romaic1900.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1900.getMonth());
        Assertions.assertEquals(1899, romaic1900.getYear());

        final RomaicDate romaic1800 = RomaicDate.ofEpochDay(LocalDate.of(1800, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(21, romaic1800.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1800.getMonth());
        Assertions.assertEquals(1799, romaic1800.getYear());

        final RomaicDate romaic1700 = RomaicDate.ofEpochDay(LocalDate.of(1700, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(22, romaic1700.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1700.getMonth());
        Assertions.assertEquals(1699, romaic1700.getYear());

        final RomaicDate romaic1600 = RomaicDate.ofEpochDay(LocalDate.of(1600, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(22, romaic1600.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1600.getMonth());
        Assertions.assertEquals(1599, romaic1600.getYear());

        final RomaicDate romaic1500 = RomaicDate.ofEpochDay(LocalDate.of(1500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(23, romaic1500.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1500.getMonth());
        Assertions.assertEquals(1499, romaic1500.getYear());

        final RomaicDate romaic1400 = RomaicDate.ofEpochDay(LocalDate.of(1400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(24, romaic1400.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1400.getMonth());
        Assertions.assertEquals(1399, romaic1400.getYear());

        final RomaicDate romaic1300 = RomaicDate.ofEpochDay(LocalDate.of(1300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(25, romaic1300.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1300.getMonth());
        Assertions.assertEquals(1299, romaic1300.getYear());

        final RomaicDate romaic1200 = RomaicDate.ofEpochDay(LocalDate.of(1200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(25, romaic1200.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1200.getMonth());
        Assertions.assertEquals(1199, romaic1200.getYear());

        final RomaicDate romaic1100 = RomaicDate.ofEpochDay(LocalDate.of(1100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(26, romaic1100.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1100.getMonth());
        Assertions.assertEquals(1099, romaic1100.getYear());

        final RomaicDate romaic1000 = RomaicDate.ofEpochDay(LocalDate.of(1000, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(27, romaic1000.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic1000.getMonth());
        Assertions.assertEquals(999, romaic1000.getYear());

        final RomaicDate romaic900 = RomaicDate.ofEpochDay(LocalDate.of(900, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(28, romaic900.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic900.getMonth());
        Assertions.assertEquals(899, romaic900.getYear());

        final RomaicDate romaic800 = RomaicDate.ofEpochDay(LocalDate.of(800, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(28, romaic800.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic800.getMonth());
        Assertions.assertEquals(799, romaic800.getYear());

        final RomaicDate romaic700 = RomaicDate.ofEpochDay(LocalDate.of(700, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(29, romaic700.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic700.getMonth());
        Assertions.assertEquals(699, romaic700.getYear());

        final RomaicDate romaic600 = RomaicDate.ofEpochDay(LocalDate.of(600, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(30, romaic600.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic600.getMonth());
        Assertions.assertEquals(599, romaic600.getYear());

        final RomaicDate romaic500 = RomaicDate.ofEpochDay(LocalDate.of(500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(31, romaic500.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic500.getMonth());
        Assertions.assertEquals(499, romaic500.getYear());

        final RomaicDate romaic400 = RomaicDate.ofEpochDay(LocalDate.of(400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(31, romaic400.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.DECEMBER, romaic400.getMonth());
        Assertions.assertEquals(399, romaic400.getYear());

        final RomaicDate romaic300 = RomaicDate.ofEpochDay(LocalDate.of(300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(1, romaic300.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic300.getMonth());
        Assertions.assertEquals(300, romaic300.getYear());

        final RomaicDate romaic200 = RomaicDate.ofEpochDay(LocalDate.of(200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(2, romaic200.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic200.getMonth());
        Assertions.assertEquals(200, romaic200.getYear());

        final RomaicDate romaic100 = RomaicDate.ofEpochDay(LocalDate.of(100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(3, romaic100.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic100.getMonth());
        Assertions.assertEquals(100, romaic100.getYear());

        final RomaicDate romaic0 = RomaicDate.ofEpochDay(LocalDate.of(0, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(3, romaic0.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaic0.getMonth());
        Assertions.assertEquals(0, romaic0.getYear());

        final RomaicDate romaicM100 = RomaicDate.ofEpochDay(LocalDate.of(-100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(4, romaicM100.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaicM100.getMonth());
        Assertions.assertEquals(-100, romaicM100.getYear());

        final RomaicDate romaicM200 = RomaicDate.ofEpochDay(LocalDate.of(-200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(5, romaicM200.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaicM200.getMonth());
        Assertions.assertEquals(-200, romaicM200.getYear());

        final RomaicDate romaicM300 = RomaicDate.ofEpochDay(LocalDate.of(-300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(6, romaicM300.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaicM300.getMonth());
        Assertions.assertEquals(-300, romaicM300.getYear());

        final RomaicDate romaicM400 = RomaicDate.ofEpochDay(LocalDate.of(-400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(6, romaicM400.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaicM400.getMonth());
        Assertions.assertEquals(-400, romaicM400.getYear());

        final RomaicDate romaicM500 = RomaicDate.ofEpochDay(LocalDate.of(-500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(7, romaicM500.getDayOfMonth());
        Assertions.assertEquals(RomaicMonth.JANUARY, romaicM500.getMonth());
        Assertions.assertEquals(-500, romaicM500.getYear());
    }

    @Test
    public void testToEpochDay() {
        Assertions.assertEquals(0, RomaicDate.of(1969 + 5509, 4, 19).toEpochDay()); //1-1-1970 grégorien
        Assertions.assertEquals(-RomaicDate.DAYS_0000_ROMAIC_TO_1970_GREGORIAN, RomaicDate.of(0000, 1, 1).toEpochDay());
        Assertions.assertEquals(-RomaicDate.DAYS_0000_ROMAIC_TO_1970_GREGORIAN - 365,
                RomaicDate.of(-1, 1, 1).toEpochDay());
    }

    @Test
    public void testDayOfYear() {
        Assertions.assertEquals(1, RomaicDate.of(2016, 1, 1).getDayOfYear());
        Assertions.assertEquals(62, RomaicDate.of(2016, 3, 1).getDayOfYear());
        Assertions.assertEquals(1, RomaicDate.of(2015, 1, 1).getDayOfYear());
        Assertions.assertEquals(62, RomaicDate.of(2015, 3, 1).getDayOfYear());
    }

    @Test
    public void testDayOfWeek() {
        Assertions.assertEquals(DayOfWeek.THURSDAY,
                RomaicDate.of(1969 + 5509, 4, 19).getDayOfWeek()); //1-1-1970 grégorien
        Assertions.assertEquals(LocalDate.now().getDayOfWeek(), RomaicDate.from(LocalDate.now()).getDayOfWeek());
    }

    @Test
    public void testLengthOfMonth() {
        Assertions.assertEquals(30, RomaicDate.of(200, 1, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 2, 1).lengthOfMonth());
        Assertions.assertEquals(30, RomaicDate.of(2016, 3, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 4, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 5, 1).lengthOfMonth());

        Assertions.assertEquals(29, RomaicDate.of(2016, 6, 1).lengthOfMonth());
        Assertions.assertEquals(28, RomaicDate.of(2015, 6, 1).lengthOfMonth());
        Assertions.assertEquals(29, RomaicDate.of(2000, 6, 1).lengthOfMonth());
        Assertions.assertEquals(29, RomaicDate.of(1900, 6, 1).lengthOfMonth());
        Assertions.assertEquals(29, RomaicDate.of(1700, 6, 1).lengthOfMonth());
        Assertions.assertEquals(29, RomaicDate.of(1600, 6, 1).lengthOfMonth());
        Assertions.assertEquals(29, RomaicDate.of(1500, 6, 1).lengthOfMonth());

        Assertions.assertEquals(31, RomaicDate.of(2016, 7, 1).lengthOfMonth());
        Assertions.assertEquals(30, RomaicDate.of(2016, 8, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 9, 1).lengthOfMonth());
        Assertions.assertEquals(30, RomaicDate.of(2016, 10, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 11, 1).lengthOfMonth());
        Assertions.assertEquals(31, RomaicDate.of(2016, 12, 1).lengthOfMonth());
    }

    @Test
    public void testLengthOfYear() {
        Assertions.assertEquals(366, RomaicDate.of(2000, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, RomaicDate.of(2000, 1, 1).lengthOfYear());
        Assertions.assertEquals(366, RomaicDate.of(1900, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, RomaicDate.of(1900, 1, 1).lengthOfYear());
        Assertions.assertEquals(366, RomaicDate.of(2016, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, RomaicDate.of(2016, 1, 1).lengthOfYear());
        Assertions.assertEquals(365, RomaicDate.of(2015, 12, 31).lengthOfYear());
        Assertions.assertEquals(365, RomaicDate.of(2015, 1, 1).lengthOfYear());
    }
}
