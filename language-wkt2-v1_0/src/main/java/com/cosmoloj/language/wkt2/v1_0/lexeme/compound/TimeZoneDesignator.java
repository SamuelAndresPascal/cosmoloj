package com.cosmoloj.language.wkt2.v1_0.lexeme.compound;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.common.impl.semantic.CharSequenceLexeme;
import java.time.ZoneOffset;
import java.util.function.Predicate;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'une date.</span>
 *
 * @author Samuel Andrés
 */
public class TimeZoneDesignator extends CharSequenceLexeme implements ParsableLexeme<ZoneOffset> {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof TimeZoneDesignator;

    public TimeZoneDesignator(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    @Override
    public ZoneOffset parse(final String codePoints) {

        if (codePoints.length() == 1 && codePoints.codePointAt(0) == 'Z') {
            return ZoneOffset.UTC;
        } else if (codePoints.length() == 3) {
            return ZoneOffset.ofHours(Integer.parseInt(codePoints));
        } else if (codePoints.length() == 6) {
            if (codePoints.codePointAt(0) == '-') {
                return ZoneOffset.ofHoursMinutes(
                        Integer.parseInt(codePoints.substring(0, 3)),
                        -Integer.parseInt(codePoints.substring(4, 6)));
            }
            return ZoneOffset.ofHoursMinutes(
                    Integer.parseInt(codePoints.substring(0, 3)),
                    Integer.parseInt(codePoints.substring(4, 6)));
        } else {
            throw new IllegalStateException();
        }
    }
}
