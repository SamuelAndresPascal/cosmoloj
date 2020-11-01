package com.cosmoloj.time;

import com.cosmoloj.time.JulianDayDate;
import com.cosmoloj.time.PetrinianDate;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class JulianDayDateTest {

    @Test
    public void testOf0() {
        final LocalDate local1 = LocalDate.of(2016, Month.JANUARY, 16);
        final JulianDayDate julianDay1 = JulianDayDate.ofEpochDay(local1.toEpochDay());
        Assertions.assertEquals(2_457_403l, julianDay1.getDay());

        final LocalDate local2 = LocalDate.of(2000, Month.JANUARY, 1);
        final JulianDayDate julianDay2 = JulianDayDate.ofEpochDay(local2.toEpochDay());
        Assertions.assertEquals(2_451_544l, julianDay2.getDay());

        final JulianDayDate julianDay3 = JulianDayDate.of(0);
        final LocalDate local3 = LocalDate.ofEpochDay(julianDay3.toEpochDay());
        Assertions.assertEquals(-4713, local3.getYear());
        Assertions.assertEquals(Month.NOVEMBER, local3.getMonth());
        Assertions.assertEquals(25, local3.getDayOfMonth());

        final JulianDayDate julianDay4 = JulianDayDate.of(0);
        final PetrinianDate local4 = PetrinianDate.ofEpochDay(julianDay4.toEpochDay());
        Assertions.assertEquals(-4712, local4.getYear());
        Assertions.assertEquals(Month.JANUARY, local4.getMonth());
        Assertions.assertEquals(2, local4.getDayOfMonth());

        final LocalDate local5 = LocalDate.of(0, Month.JANUARY, 1);
        final JulianDayDate julianDay5 = JulianDayDate.ofEpochDay(local5.toEpochDay());
        Assertions.assertEquals(1721059, julianDay5.getDay());

        final PetrinianDate local6 = PetrinianDate.of(0, Month.JANUARY, 1);
        final JulianDayDate julianDay6 = JulianDayDate.ofEpochDay(local6.toEpochDay());
        Assertions.assertEquals(1721057, julianDay6.getDay());

//        final PetrinianDate julianDay4 = PetrinianDate.of(0);
//        final JulianDayDate local4 = JulianDayDate.ofEpochDay(julianDay4.toEpochDay());
//        Assertions.assertEquals(-4712, local4.getYear());
//        Assertions.assertEquals(Month.JANUARY, local4.getMonth());
//        Assertions.assertEquals(2, local4.getDayOfMonth());
    }
}
