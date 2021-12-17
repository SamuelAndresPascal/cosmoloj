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
public class PetrinianDateTest {

    @Test
    public void of0() {
        final LocalDate local = LocalDate.of(2016, Month.JANUARY, 16);
        final PetrinianDate petrinian = PetrinianDate.ofEpochDay(local.toEpochDay());
        Assertions.assertEquals(3, petrinian.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian.getMonth());
        Assertions.assertEquals(2016, petrinian.getYear());
    }

    @Test
    public void regressionFalseBissextile() {
        final PetrinianDate date1 = PetrinianDate.of(2022, Month.FEBRUARY, 28);

        final PetrinianDate date2 = date1.plusDays(1);
        Assertions.assertEquals(1, date2.getDayOfMonth());
        Assertions.assertEquals(3, date2.getMonthValue());
        Assertions.assertEquals(Month.MARCH, date2.getMonth());
        Assertions.assertEquals(2022, date2.getYear());

        final PetrinianDate date3 = date1.plusDays(2);
        Assertions.assertEquals(2, date3.getDayOfMonth());
        Assertions.assertEquals(3, date3.getMonthValue());
        Assertions.assertEquals(Month.MARCH, date3.getMonth());
        Assertions.assertEquals(2022, date3.getYear());
    }

    @Test
    public void ofEpochDay() {

        final PetrinianDate petrinian1 = PetrinianDate.ofEpochDay(LocalDate.of(2016, Month.JANUARY, 16).toEpochDay());
        Assertions.assertEquals(3, petrinian1.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian1.getMonth());
        Assertions.assertEquals(2016, petrinian1.getYear());

        final PetrinianDate petrinian2200 = PetrinianDate.ofEpochDay(LocalDate.of(2200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(18, petrinian2200.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian2200.getMonth());
        Assertions.assertEquals(2199, petrinian2200.getYear());

        final PetrinianDate petrinian2100 = PetrinianDate.ofEpochDay(LocalDate.of(2100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(19, petrinian2100.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian2100.getMonth());
        Assertions.assertEquals(2099, petrinian2100.getYear());

        final PetrinianDate petrinian2000 = PetrinianDate.ofEpochDay(LocalDate.of(2000, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(19, petrinian2000.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian2000.getMonth());
        Assertions.assertEquals(1999, petrinian2000.getYear());

        final PetrinianDate petrinian1900 = PetrinianDate.ofEpochDay(LocalDate.of(1900, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(20, petrinian1900.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1900.getMonth());
        Assertions.assertEquals(1899, petrinian1900.getYear());

        final PetrinianDate petrinian1800 = PetrinianDate.ofEpochDay(LocalDate.of(1800, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(21, petrinian1800.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1800.getMonth());
        Assertions.assertEquals(1799, petrinian1800.getYear());

        final PetrinianDate petrinian1700 = PetrinianDate.ofEpochDay(LocalDate.of(1700, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(22, petrinian1700.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1700.getMonth());
        Assertions.assertEquals(1699, petrinian1700.getYear());

        final PetrinianDate petrinian1600 = PetrinianDate.ofEpochDay(LocalDate.of(1600, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(22, petrinian1600.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1600.getMonth());
        Assertions.assertEquals(1599, petrinian1600.getYear());

        final PetrinianDate petrinian1500 = PetrinianDate.ofEpochDay(LocalDate.of(1500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(23, petrinian1500.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1500.getMonth());
        Assertions.assertEquals(1499, petrinian1500.getYear());

        final PetrinianDate petrinian1400 = PetrinianDate.ofEpochDay(LocalDate.of(1400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(24, petrinian1400.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1400.getMonth());
        Assertions.assertEquals(1399, petrinian1400.getYear());

        final PetrinianDate petrinian1300 = PetrinianDate.ofEpochDay(LocalDate.of(1300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(25, petrinian1300.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1300.getMonth());
        Assertions.assertEquals(1299, petrinian1300.getYear());

        final PetrinianDate petrinian1200 = PetrinianDate.ofEpochDay(LocalDate.of(1200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(25, petrinian1200.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1200.getMonth());
        Assertions.assertEquals(1199, petrinian1200.getYear());

        final PetrinianDate petrinian1100 = PetrinianDate.ofEpochDay(LocalDate.of(1100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(26, petrinian1100.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1100.getMonth());
        Assertions.assertEquals(1099, petrinian1100.getYear());

        final PetrinianDate petrinian1000 = PetrinianDate.ofEpochDay(LocalDate.of(1000, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(27, petrinian1000.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian1000.getMonth());
        Assertions.assertEquals(999, petrinian1000.getYear());

        final PetrinianDate petrinian900 = PetrinianDate.ofEpochDay(LocalDate.of(900, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(28, petrinian900.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian900.getMonth());
        Assertions.assertEquals(899, petrinian900.getYear());

        final PetrinianDate petrinian800 = PetrinianDate.ofEpochDay(LocalDate.of(800, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(28, petrinian800.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian800.getMonth());
        Assertions.assertEquals(799, petrinian800.getYear());

        final PetrinianDate petrinian700 = PetrinianDate.ofEpochDay(LocalDate.of(700, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(29, petrinian700.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian700.getMonth());
        Assertions.assertEquals(699, petrinian700.getYear());

        final PetrinianDate petrinian600 = PetrinianDate.ofEpochDay(LocalDate.of(600, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(30, petrinian600.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian600.getMonth());
        Assertions.assertEquals(599, petrinian600.getYear());

        final PetrinianDate petrinian500 = PetrinianDate.ofEpochDay(LocalDate.of(500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(31, petrinian500.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian500.getMonth());
        Assertions.assertEquals(499, petrinian500.getYear());

        final PetrinianDate petrinian400 = PetrinianDate.ofEpochDay(LocalDate.of(400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(31, petrinian400.getDayOfMonth());
        Assertions.assertEquals(Month.DECEMBER, petrinian400.getMonth());
        Assertions.assertEquals(399, petrinian400.getYear());

        final PetrinianDate petrinian300 = PetrinianDate.ofEpochDay(LocalDate.of(300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(1, petrinian300.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian300.getMonth());
        Assertions.assertEquals(300, petrinian300.getYear());

        final PetrinianDate petrinian200 = PetrinianDate.ofEpochDay(LocalDate.of(200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(2, petrinian200.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian200.getMonth());
        Assertions.assertEquals(200, petrinian200.getYear());

        final PetrinianDate petrinian100 = PetrinianDate.ofEpochDay(LocalDate.of(100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(3, petrinian100.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian100.getMonth());
        Assertions.assertEquals(100, petrinian100.getYear());

        final PetrinianDate petrinian0 = PetrinianDate.ofEpochDay(LocalDate.of(0, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(3, petrinian0.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinian0.getMonth());
        Assertions.assertEquals(0, petrinian0.getYear());

        final PetrinianDate petrinianM100 = PetrinianDate.ofEpochDay(LocalDate.of(-100, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(4, petrinianM100.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinianM100.getMonth());
        Assertions.assertEquals(-100, petrinianM100.getYear());

        final PetrinianDate petrinianM200 = PetrinianDate.ofEpochDay(LocalDate.of(-200, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(5, petrinianM200.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinianM200.getMonth());
        Assertions.assertEquals(-200, petrinianM200.getYear());

        final PetrinianDate petrinianM300 = PetrinianDate.ofEpochDay(LocalDate.of(-300, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(6, petrinianM300.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinianM300.getMonth());
        Assertions.assertEquals(-300, petrinianM300.getYear());

        final PetrinianDate petrinianM400 = PetrinianDate.ofEpochDay(LocalDate.of(-400, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(6, petrinianM400.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinianM400.getMonth());
        Assertions.assertEquals(-400, petrinianM400.getYear());

        final PetrinianDate petrinianM500 = PetrinianDate.ofEpochDay(LocalDate.of(-500, Month.JANUARY, 1).toEpochDay());
        Assertions.assertEquals(7, petrinianM500.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, petrinianM500.getMonth());
        Assertions.assertEquals(-500, petrinianM500.getYear());
    }

    @Test
    public void toEpochDay() {
        Assertions.assertEquals(0, PetrinianDate.of(1969, 12, 19).toEpochDay()); //1-1-1970 grégorien
        Assertions.assertEquals(-PetrinianDate.DAYS_0000_PETRINIAN_TO_1970_GREGORIAN,
                PetrinianDate.of(0000, 1, 1).toEpochDay());
        Assertions.assertEquals(-PetrinianDate.DAYS_0000_PETRINIAN_TO_1970_GREGORIAN - 365,
                PetrinianDate.of(-1, 1, 1).toEpochDay());
    }

    @Test
    public void dayOfYear() {
        Assertions.assertEquals(1, PetrinianDate.of(2016, 1, 1).getDayOfYear());
        Assertions.assertEquals(61, PetrinianDate.of(2016, 3, 1).getDayOfYear());
        Assertions.assertEquals(1, PetrinianDate.of(2015, 1, 1).getDayOfYear());
        Assertions.assertEquals(60, PetrinianDate.of(2015, 3, 1).getDayOfYear());
    }

    @Test
    public void dayOfWeek() {
        Assertions.assertEquals(DayOfWeek.THURSDAY, PetrinianDate.of(1969, 12, 19).getDayOfWeek()); //1-1-1970 grégorien
        Assertions.assertEquals(LocalDate.now().getDayOfWeek(), PetrinianDate.from(LocalDate.now()).getDayOfWeek());
    }

    @Test
    public void lengthOfMonth() {
        Assertions.assertEquals(31, PetrinianDate.of(2016, 1, 1).lengthOfMonth());

        Assertions.assertEquals(29, PetrinianDate.of(2016, 2, 1).lengthOfMonth());
        Assertions.assertEquals(28, PetrinianDate.of(2015, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, PetrinianDate.of(2000, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, PetrinianDate.of(1900, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, PetrinianDate.of(1700, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, PetrinianDate.of(1600, 2, 1).lengthOfMonth());
        Assertions.assertEquals(29, PetrinianDate.of(1500, 2, 1).lengthOfMonth());

        Assertions.assertEquals(31, PetrinianDate.of(2016, 3, 1).lengthOfMonth());
        Assertions.assertEquals(30, PetrinianDate.of(2016, 4, 1).lengthOfMonth());
        Assertions.assertEquals(31, PetrinianDate.of(2016, 5, 1).lengthOfMonth());
        Assertions.assertEquals(30, PetrinianDate.of(2016, 6, 1).lengthOfMonth());
        Assertions.assertEquals(31, PetrinianDate.of(2016, 7, 1).lengthOfMonth());
        Assertions.assertEquals(31, PetrinianDate.of(2016, 8, 1).lengthOfMonth());
        Assertions.assertEquals(30, PetrinianDate.of(2016, 9, 1).lengthOfMonth());
        Assertions.assertEquals(31, PetrinianDate.of(2016, 10, 1).lengthOfMonth());
        Assertions.assertEquals(30, PetrinianDate.of(2016, 11, 1).lengthOfMonth());
        Assertions.assertEquals(31, PetrinianDate.of(2016, 12, 1).lengthOfMonth());
    }

    @Test
    public void lengthOfYear() {
        Assertions.assertEquals(366, PetrinianDate.of(2000, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, PetrinianDate.of(2000, 1, 1).lengthOfYear());
        Assertions.assertEquals(366, PetrinianDate.of(1900, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, PetrinianDate.of(1900, 1, 1).lengthOfYear());
        Assertions.assertEquals(366, PetrinianDate.of(2016, 12, 31).lengthOfYear());
        Assertions.assertEquals(366, PetrinianDate.of(2016, 1, 1).lengthOfYear());
        Assertions.assertEquals(365, PetrinianDate.of(2015, 12, 31).lengthOfYear());
        Assertions.assertEquals(365, PetrinianDate.of(2015, 1, 1).lengthOfYear());
    }
}
