package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.common.impl.semantic.CharSequenceLexeme;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.function.Predicate;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'une date.</span>
 *
 * @author Samuel Andrés
 */
public class GregorianDate extends CharSequenceLexeme implements ParsableLexeme<Temporal> {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof GregorianDate;

    public enum Type {
        YEAR, YEAR_MONTH, DATE_ORDINAL, DATE;
    }

    public GregorianDate(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    public static Type getType(final String codePoints) {
        final int first = codePoints.indexOf('-');
        final int last = codePoints.lastIndexOf('-');

        if (first < 0) {
            return Type.YEAR;
        } else if (first == last) {
            if (last == codePoints.length() - 3) {
                return Type.YEAR_MONTH;
            } else if (last == codePoints.length() - 4) {
                return Type.DATE_ORDINAL;
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            return Type.DATE;
        }
    }

    @Override
    public Temporal parse(final String codePoints) {
        return codePointsToDate(codePoints);
    }

    public static Temporal codePointsToDate(final String codePoints) {

        return switch (getType(codePoints)) {
            case YEAR -> Year.parse(codePoints);
            case YEAR_MONTH -> YearMonth.parse(codePoints);
            case DATE_ORDINAL -> LocalDate.parse(codePoints, DateTimeFormatter.ISO_ORDINAL_DATE);
            case DATE -> LocalDate.parse(codePoints);
            default -> throw new IllegalStateException();
        };
    }
}
