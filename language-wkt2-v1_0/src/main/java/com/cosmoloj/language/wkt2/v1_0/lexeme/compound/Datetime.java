package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.common.impl.semantic.CharSequenceLexeme;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.function.Predicate;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'une date.</span>
 *
 * @author Samuel Andrés
 */
public class Datetime extends CharSequenceLexeme implements ParsableLexeme<Temporal> {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Datetime;

    public static final DateTimeFormatter ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .optionalStart()
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .optionalEnd()
                .optionalStart()
                .appendValue(ChronoField.DAY_OF_YEAR, 3)
                .optionalEnd()
                .toFormatter();

    public static final DateTimeFormatter LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral('T')
                .append(Clock.LOCAL_TIME)
                .toFormatter();

    public static final DateTimeFormatter OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(LOCAL_DATE_TIME)
                .parseLenient()
                .appendOffsetId()
                .parseStrict()
                .toFormatter();

    private final boolean hasTime;

    public Datetime(final String codePoints, final int start, final int end, final int index, final boolean hasTime) {
        super(codePoints, start, end, index);
        this.hasTime = hasTime;
    }

    @Override
    public Temporal parse(final String codePoints) {
        if (!this.hasTime) {
            return GregorianDate.codePointsToDate(codePoints);
        } else {
            return OffsetDateTime.parse(codePoints, OFFSET_DATE_TIME);
        }
    }
}
