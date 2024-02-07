package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.Clock;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.Datetime;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.GregorianDate;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.TimeZoneDesignator;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktParserNoDelimTest {

    @Test
    public void gregorian_date_1() throws LanguageException {

        final var text = "2017-10-13";

        final WktParser parser = WktParser.of(text);

        final GregorianDate date = parser.gregorianDate();

        Assertions.assertEquals(LocalDate.parse("2017-10-13"), date.getSemantics());
    }

    @Test
    public void gregorian_date_2() throws LanguageException {

        final var text = "2017-10";

        final WktParser parser = WktParser.of(text);

        final GregorianDate date = parser.gregorianDate();

        Assertions.assertEquals(YearMonth.parse("2017-10"), date.getSemantics());
    }

    @Test
    public void gregorian_date_3() throws LanguageException {

        final var text = "2017";

        final WktParser parser = WktParser.of(text);

        final GregorianDate date = parser.gregorianDate();

        Assertions.assertEquals(Year.parse("2017"), date.getSemantics());
    }

    @Test
    public void gregorian_date_4() throws LanguageException {

        final var text = "2014-060";

        final WktParser parser = WktParser.of(text);

        final GregorianDate date = parser.gregorianDate();

        final LocalDate semantic = (LocalDate) date.getSemantics();
        Assertions.assertEquals(LocalDate.parse("2014-060", DateTimeFormatter.ISO_ORDINAL_DATE), semantic);
        Assertions.assertEquals(2014, semantic.getYear());
        Assertions.assertEquals(Month.MARCH, semantic.getMonth());
        Assertions.assertEquals(1, semantic.getDayOfMonth());
        Assertions.assertEquals(60, semantic.getDayOfYear());
    }

    @Test
    public void time_designator_1() throws LanguageException {

        final var text = "Z";

        final WktParser parser = WktParser.of(text);

        final TimeZoneDesignator zone = parser.timeZoneDesignator();

        Assertions.assertEquals(ZoneOffset.UTC, zone.getSemantics());
    }

    @Test
    public void time_designator_2() throws LanguageException {

        final var text = "+02";

        final WktParser parser = WktParser.of(text);

        final TimeZoneDesignator zone = parser.timeZoneDesignator();

        Assertions.assertEquals(ZoneOffset.ofHours(2), zone.getSemantics());
    }

    @Test
    public void time_designator_3() throws LanguageException {

        final var text = "-04";

        final WktParser parser = WktParser.of(text);

        final TimeZoneDesignator zone = parser.timeZoneDesignator();

        Assertions.assertEquals(ZoneOffset.ofHours(-4), zone.getSemantics());
    }

    @Test
    public void time_designator_4() throws LanguageException {

        final var text = "+02:56";

        final WktParser parser = WktParser.of(text);

        final TimeZoneDesignator zone = parser.timeZoneDesignator();

        Assertions.assertEquals(ZoneOffset.ofHoursMinutes(2, 56), zone.getSemantics());
    }

    @Test
    public void time_designator_5() throws LanguageException {

        final var text = "-04:38";

        final WktParser parser = WktParser.of(text);

        final TimeZoneDesignator zone = parser.timeZoneDesignator();

        Assertions.assertEquals(ZoneOffset.ofHoursMinutes(-4, -38), zone.getSemantics());
    }

    @Test
    public void clock_1() throws LanguageException {

        final var text = "T16:14-04:38";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:14-04:38"), date.getSemantics());
    }

    @Test
    public void clock_2() throws LanguageException {

        final var text = "T16:14+04:38";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:14+04:38"), date.getSemantics());
    }

    @Test
    public void clock_3() throws LanguageException {

        final var text = "T16:14:03+04:38";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:14:03+04:38"), date.getSemantics());
    }

    @Test
    public void clock_4() throws LanguageException {

        final var text = "T16:14:03Z";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:14:03Z"), date.getSemantics());
    }

    @Test
    public void clock_5() throws LanguageException {

        final var text = "T16+14:03";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:00+14:03"), date.getSemantics());
    }

    @Test
    public void clock_6() throws LanguageException {

        final var text = "T16Z";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:00Z"), date.getSemantics());
    }

    @Test
    public void clock_7() throws LanguageException {

        final var text = "T16:14:03.02+04:38";

        final WktParser parser = WktParser.of(text);

        final Clock date = parser.clock();

        Assertions.assertEquals(OffsetTime.parse("16:14:03.02+04:38"), date.getSemantics());
    }

    @Test
    public void date_time_1() throws LanguageException {

        final var text = "2014";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(Year.parse("2014"), date.getSemantics());
    }

    @Test
    public void date_time_2() throws LanguageException {

        final var text = "2014-01";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(YearMonth.parse("2014-01"), date.getSemantics());
    }

    @Test
    public void date_time_3() throws LanguageException {

        final var text = "2014-03-01";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(LocalDate.parse("2014-03-01"), date.getSemantics());
    }

    @Test
    public void date_time_4() throws LanguageException {

        final var text = "2014-060";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(LocalDate.parse("2014-03-01"), date.getSemantics());
    }

    @Test
    public void date_time_5() throws LanguageException {

        final var text = "2014-05-06T23Z";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-05-06T23:00Z"), date.getSemantics());
    }

    @Test
    public void date_time_6() throws LanguageException {

        final var text = "2014-157T23Z";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-06-06T23:00Z"), date.getSemantics());
    }

    @Test
    public void date_time_7() throws LanguageException {

        final var text = "2014-07-12T16:00Z";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-07-12T16:00Z"), date.getSemantics());
    }

    @Test
    public void date_time_8() throws LanguageException {

        final var text = "2014-07-12T17:00+01";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-07-12T17:00+01"), date.getSemantics());
    }

    @Test
    public void date_time_9() throws LanguageException {

        final var text = "2014-09-18T08:17:56Z";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-09-18T08:17:56Z"), date.getSemantics());
    }

    @Test
    public void date_time_10() throws LanguageException {

        final var text = "2014-11-23T00:34:56.789Z";

        final WktParser parser = WktParser.of(text);

        final Datetime date = parser.dateTime();

        Assertions.assertEquals(OffsetDateTime.parse("2014-11-23T00:34:56.789Z"), date.getSemantics());
    }

    @Test
    public void axis_direction_test_a() throws LanguageException {

        final var text = "north";

        final WktParser parser = WktParser.of(text);

        final var direction = parser.axisDirection();

        Assertions.assertEquals(Direction.north, direction.getType().getSemantics());
        Assertions.assertNull(direction.getComplement());
    }
}
