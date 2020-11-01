package com.cosmoloj.time.format;

import com.cosmoloj.time.GregorianDate;
import com.cosmoloj.time.PetrinianDate;
import com.cosmoloj.time.RomaicDate;
import com.cosmoloj.time.RomaicMonth;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 *
 * @author Samuel Andrés
 */
public final class FormatUtil {

    private FormatUtil() {
    }

    public static String toString(final int yearValue, final int monthValue, final int dayValue) {
        final int absYear = Math.abs(yearValue);
        final StringBuilder buf = new StringBuilder(10);
        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append(yearValue - 10000).deleteCharAt(1);
            } else {
                buf.append(yearValue + 10000).deleteCharAt(0);
            }
        } else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }
        return buf.append(monthValue < 10 ? "-0" : "-")
            .append(monthValue)
            .append(dayValue < 10 ? "-0" : "-")
            .append(dayValue)
            .toString();
    }

    public static String format(final LocalDate localDate, final Locale locale) {
        return localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, locale)
                + " " + localDate.getYear();
    }

    public static String format(final GregorianDate localDate, final Locale locale) {
        return localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, locale)
                + " " + localDate.getYear();
    }

    public static String format(final PetrinianDate localDate, final Locale locale) {
        return localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, locale)
                + " " + localDate.getYear();
    }

    public static String format(final RomaicDate localDate, final Locale locale) {
        final int toSubstitute = localDate.getMonth().getValue();
        final RomaicMonth substitutionMonth = (toSubstitute < 5)
                ? RomaicMonth.of(toSubstitute + 8) : RomaicMonth.of(toSubstitute - 4);
        return localDate.getDayOfMonth() + " " + substitutionMonth.getDisplayName(TextStyle.FULL, locale)
                + " " + localDate.getYear();
    }
}
