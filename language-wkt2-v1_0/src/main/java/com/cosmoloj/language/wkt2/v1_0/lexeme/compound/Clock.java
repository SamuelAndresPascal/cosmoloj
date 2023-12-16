package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.common.impl.semantic.CharSequenceLexeme;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.function.Predicate;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'une date.</span>
 *
 * @author Samuel Andrés
 */
public class Clock extends CharSequenceLexeme implements ParsableLexeme<OffsetTime> {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Clock;

    public static final DateTimeFormatter LOCAL_TIME = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                .toFormatter();

    public static final DateTimeFormatter OFFSET_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(LOCAL_TIME)
                .appendOffsetId()
                .toFormatter();

    public Clock(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    @Override
    public OffsetTime parse(final String codePoints) {
        return OffsetTime.parse(codePoints.substring(1), OFFSET_TIME);
    }
}
