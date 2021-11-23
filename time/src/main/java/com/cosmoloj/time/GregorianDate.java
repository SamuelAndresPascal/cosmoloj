package com.cosmoloj.time;

import com.cosmoloj.time.chrono.GregorianChronology;
import com.cosmoloj.time.format.FormatUtil;
import com.cosmoloj.time.temporal.TemporalQueries;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

public final class GregorianDate extends WeekDate<Month, DayOfWeek>
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    public static final GregorianDate MIN = GregorianDate.of(Year.MIN_VALUE, 1, 1);
    public static final GregorianDate MAX = GregorianDate.of(Year.MAX_VALUE, 12, 31);

    /**
     * Nombre de jours entre le début de l'ère chrétienne (1er janvier 0) et le
     * début de l'ère Java (1er janvier 1970).
     *
     * De 0 à 2000 = 5 cycles grégoriens de 4 siècles.
     * De 2000 à 1970, on retire 30 années de 365 jours et encore 7 jours
     * supplémentaires correspondants aux jours des 7 années bissextiles entre
     * 1970 et 2000.
     *
     * Vaut 719728 jours.
     */
    static final long DAYS_0000_TO_1970 = (GregorianUtil.DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    private GregorianDate(final int year, final int month, final int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public Month getMonth() {
        return Month.of(getMonthValue());
    }

    @Override
    public boolean isSupported(final TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    @Override
    public long until(final Temporal endExclusive, final TemporalUnit unit) {
        return TemporalUtil.between(this, GregorianDate.from(endExclusive), unit);
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
    }

    @Override
    public ValueRange range(final TemporalField field) {
        if (field instanceof ChronoField chronoField) {
            if (chronoField.isDateBased()) {
                return switch (chronoField) {
                    case DAY_OF_MONTH -> ValueRange.of(1, lengthOfMonth());
                    case DAY_OF_YEAR -> ValueRange.of(1, lengthOfYear());
                    case ALIGNED_WEEK_OF_MONTH ->
                        ValueRange.of(1, Month.FEBRUARY.equals(getMonth()) && !isLeapYear() ? 4 : 5);
                    case YEAR_OF_ERA ->
                        getYear() <= 0 ? ValueRange.of(1, Year.MAX_VALUE + 1) : ValueRange.of(1, Year.MAX_VALUE);
                    default -> field.range();
                };
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.rangeRefinedBy(this);
    }

    @Override
    public int get(final TemporalField field) {
        if (field instanceof ChronoField) {
            return get0(field);
        }
        return ChronoLocalDate.super.get(field);
    }

    @Override
    public long getLong(final TemporalField field) {
        if (field instanceof ChronoField) {
            if (field == ChronoField.EPOCH_DAY) {
                return toEpochDay();
            }
            if (field == ChronoField.PROLEPTIC_MONTH) {
                return JulianUtil.getProlepticMonth(getYear(), getMonthValue());
            }
            return get0(field);
        }
        return field.getFrom(this);
    }

    private int get0(final TemporalField field) {
        return switch ((ChronoField) field) {
            case DAY_OF_WEEK -> getDayOfWeek().getValue();
            case ALIGNED_DAY_OF_WEEK_IN_MONTH -> ((getDayOfMonth() - 1) % 7) + 1;
            case ALIGNED_DAY_OF_WEEK_IN_YEAR -> ((getDayOfYear() - 1) % 7) + 1;
            case DAY_OF_MONTH -> getDayOfMonth();
            case DAY_OF_YEAR -> getDayOfYear();
            case EPOCH_DAY -> throw new UnsupportedTemporalTypeException(
                    "Invalid field 'EpochDay' for get() method, use getLong() instead");
            case ALIGNED_WEEK_OF_MONTH -> ((getDayOfMonth() - 1) / 7) + 1;
            case ALIGNED_WEEK_OF_YEAR -> ((getDayOfYear() - 1) / 7) + 1;
            case MONTH_OF_YEAR -> getMonthValue();
            case PROLEPTIC_MONTH -> throw new UnsupportedTemporalTypeException(
                    "Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case YEAR_OF_ERA -> (getYear() >= 1 ? getYear() : 1 - getYear());
            case YEAR -> getYear();
            case ERA -> (getYear() >= 1 ? 1 : 0);
            default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        };
    }

    @Override
    public GregorianChronology getChronology() {
        return GregorianChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        return Month.of(getMonthValue()).length(isLeapYear());
    }

    @Override
    public GregorianPeriod until(final ChronoLocalDate endDateExclusive) {
//        final GregorianDate end = GregorianDate.from(endDateExclusive);
//        long totalMonths = JulianUtils.getProlepticMonth(end.getYear(), end.getMonthValue())
//        - JulianUtils.getProlepticMonth(this.getYear(), this.getMonthValue());  // safe
//        int days = end.getDayOfMonth() - this.day;
//        if (totalMonths > 0 && days < 0) {
//            totalMonths--;
//            final GregorianDate calcDate = this.plusMonths(totalMonths);
//            days = (final int) (end.toEpochDay() - calcDate.toEpochDay());  // safe
//        } else if (totalMonths < 0 && days > 0) {
//            totalMonths++;
//            days -= end.lengthOfMonth();
//        }
//        long years = totalMonths / 12;  // safe
//        int months = (final int) (totalMonths % 12);  // safe
//        return Period.of(Math.toIntExact(years), months, days);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(final ChronoLocalDate other) {
        if (other instanceof GregorianDate) {
            return TemporalUtil.compare(this, (GregorianDate) other);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(final ChronoLocalDate other) {
        if (other instanceof GregorianDate) {
            return TemporalUtil.compare(this, (GregorianDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(final ChronoLocalDate other) {
        if (other instanceof GregorianDate) {
            return TemporalUtil.compare(this, (GregorianDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(final ChronoLocalDate other) {
        if (other instanceof GregorianDate) {
            return TemporalUtil.compare(this, (GregorianDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GregorianDate gregorian) {
            return TemporalUtil.compare(this, gregorian) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public long toEpochDay() {
        final long y = getYear();

        // Nombre de jours dans les années écoulées depuis l'année 0000 en comptant 365 jours par an.
        long total = 365 * y;

        // Si on est après l'an 0000
        if (y >= 0) {
            // On ajoute un jour par année bissextile (1 année sur 4) en tenant compte de l'année en cours (d'où le +3)
            // …sauf les années séculaires (1 année sur 100) en tenant compte de l'année en cours (d'où le +100)
            // …mais en maintenant une année séculaire bissextile tous les 4 siècles
            // (1 année sur 400) en tenant compte de l'année en cours (d'où le +400)
            total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400;
        } else {
            total -= y / -4 - y / -100 + y / -400;
        }

        // On ajoute le nombre de jours écoulés dans l'année jusqu'au mois en cours.
        total += getDayOfYear() - 1; // Pourquoi (-1) ?

        // On se ramène à l'époque Java
        return total - DAYS_0000_TO_1970;
    }

    @Override
    public DayOfWeek getDayOfWeek() {
        // Le 1-1-1970 grégorien était un jeudi.
        int dow0 = (int) Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
    }

    @Override
    public boolean isLeapYear() {
        return GregorianChronology.INSTANCE.isLeapYear(getYear());
    }

    @Override
    public int getDayOfYear() {
        return getMonth().firstDayOfYear(isLeapYear()) + getDayOfMonth() - 1;
    }

    /**
     * <div class="fr">Autre altorithme pour calculer le jour de l'année.</div>
     *
     * @return
     */
    public int getDayOfYear2() {
        // On ajoute le nombre de jours écoulés dans l'année jusqu'au mois en cours.
        int total = (367 * getMonthValue() - 362) / 12;

        // On ajoute le nombre de jours écoulés dans le mois courant.
        total += getDayOfMonth();

        if (getMonthValue() > 2) {
            total--;
            if (!isLeapYear()) {
                total--;
            }
        }

        return total;
    }

    @Override
    public GregorianDate with(final TemporalAdjuster adjuster) {
        if (adjuster instanceof GregorianDate) {
            return (GregorianDate) adjuster;
        }
        return (GregorianDate) adjuster.adjustInto(this);
    }

    @Override
    public GregorianDate with(final TemporalField field, final long newValue) {
        if (field instanceof ChronoField f) {
            f.checkValidValue(newValue);
            return switch (f) {
                case DAY_OF_WEEK -> plusDays(newValue - getDayOfWeek().getValue());
                case ALIGNED_DAY_OF_WEEK_IN_MONTH ->
                    plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case ALIGNED_DAY_OF_WEEK_IN_YEAR ->
                    plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case DAY_OF_MONTH -> withDayOfMonth((int) newValue);
                case DAY_OF_YEAR -> withDayOfYear((int) newValue);
                case EPOCH_DAY -> GregorianDate.ofEpochDay(newValue);
                case ALIGNED_WEEK_OF_MONTH -> plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
                case ALIGNED_WEEK_OF_YEAR -> plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
                case MONTH_OF_YEAR -> withMonth((int) newValue);
                case PROLEPTIC_MONTH -> plusMonths(newValue - JulianUtil.getProlepticMonth(getYear(), getMonthValue()));
                case YEAR_OF_ERA -> withYear((int) (getYear() >= 1 ? newValue : 1 - newValue));
                case YEAR -> withYear((int) newValue);
                case ERA -> (getLong(ChronoField.ERA) == newValue ? this : withYear(1 - getYear()));
                default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            };
        }
        return field.adjustInto(this, newValue);
    }

    public GregorianDate withYear(final int year) {
        if (this.getYear() == year) {
            return this;
        }
        ChronoField.YEAR.checkValidValue(year);
        return resolvePreviousValid(year, getMonthValue(), getDayOfMonth());
    }

    public GregorianDate withMonth(final int month) {
        if (this.getMonthValue() == month) {
            return this;
        }
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        return resolvePreviousValid(getYear(), month, getDayOfMonth());
    }

    public GregorianDate withDayOfMonth(final int dayOfMonth) {
        if (this.getDayOfMonth() == dayOfMonth) {
            return this;
        }
        return of(getYear(), getMonthValue(), dayOfMonth);
    }

    public GregorianDate withDayOfYear(final int dayOfYear) {
        if (this.getDayOfYear() == dayOfYear) {
            return this;
        }
        return ofYearDay(getYear(), dayOfYear);
    }

    private static GregorianDate resolvePreviousValid(final int year, final int month, final int day) {
        final int d = switch (month) {
            case 2 -> Math.min(day, GregorianChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
            case 4, 6, 9, 11 -> Math.min(day, 30);
            default -> day;
        };
        return new GregorianDate(year, month, d);
    }

    @Override
    public GregorianDate plus(final TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period period) {
            return plusMonths(period.toTotalMonths()).plusDays(period.getDays());
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (GregorianDate) amountToAdd.addTo(this);
    }

    @Override
    public GregorianDate plus(final long amountToAdd, final TemporalUnit unit) {
        if (unit instanceof ChronoUnit chrono) {
            return switch (chrono) {
                case DAYS -> plusDays(amountToAdd);
                case WEEKS -> plusWeeks(amountToAdd);
                case MONTHS -> plusMonths(amountToAdd);
                case YEARS -> plusYears(amountToAdd);
                case DECADES -> plusYears(Math.multiplyExact(amountToAdd, 10));
                case CENTURIES -> plusYears(Math.multiplyExact(amountToAdd, 100));
                case MILLENNIA -> plusYears(Math.multiplyExact(amountToAdd, 1000));
                case ERAS -> with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), amountToAdd));
                default -> throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            };
        }
        return unit.addTo(this, amountToAdd);
    }

    public GregorianDate plusYears(final long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = ChronoField.YEAR.checkValidIntValue(getYear() + yearsToAdd);  // safe overflow
        return resolvePreviousValid(newYear, getMonthValue(), getDayOfMonth());
    }

    public GregorianDate plusMonths(final long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = getYear() * 12L + (getMonthValue() - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = (int) Math.floorMod(calcMonths, 12) + 1;
        return resolvePreviousValid(newYear, newMonth, getDayOfMonth());
    }

    public GregorianDate plusWeeks(final long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    public GregorianDate plusDays(final long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return GregorianDate.ofEpochDay(mjDay);
    }

    @Override
    public GregorianDate minus(final TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period period) {
            return minusMonths(period.toTotalMonths()).minusDays(period.getDays());
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (GregorianDate) amountToSubtract.subtractFrom(this);
    }

    @Override
    public GregorianDate minus(final long amountToSubtract, final TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE
                ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    public GregorianDate minusYears(final long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE
                ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    public GregorianDate minusMonths(final long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE
                ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    public GregorianDate minusWeeks(final long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE
                ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeksToSubtract));
    }

    public GregorianDate minusDays(final long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    @Override
    public <R> R query(final TemporalQuery<R> query) {
        if (query == TemporalQueries.gregorianDate()) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public String toString() {
        return FormatUtil.toString(getYear(), getMonthValue(), getDayOfMonth());
    }

    public static GregorianDate of(final int year, final int month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month, dayOfMonth);
    }

    public static GregorianDate of(final int year, final Month month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        Objects.requireNonNull(month, "month");
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month.getValue(), dayOfMonth);
    }

    public static GregorianDate ofYearDay(final int year, final int dayOfYear) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = GregorianChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && !leap) {
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");
        }
        Month moy = Month.of((dayOfYear - 1) / 31 + 1);
        int monthEnd = moy.firstDayOfYear(leap) + moy.length(leap) - 1;
        if (dayOfYear > monthEnd) {
            moy = moy.plus(1);
        }
        final int dom = dayOfYear - moy.firstDayOfYear(leap) + 1;
        return new GregorianDate(year, moy.getValue(), dom);
    }

    public static GregorianDate ofEpochDay(final long epochDay) {
        // Nombre de jours écoulés depuis l'époque 01/01/0000 grégorien
        final long zeroDay = epochDay + DAYS_0000_TO_1970;

        /*
        Numéro de jour dans l'année commençant le 1er mars.
        Ce calcul nécessite de décaler le décompte des jours début mars.
        On enlève donc 60 jours correspondant aux 31 jours de janvier et 29
        jours de février 0000 qui est bisextile.
        */
        final YearDayDate marchDoy = GregorianMarchUtil.toDayOfYear(zeroDay - 60);

        //Retour au calendrier débutant en janvier.
        final YearMonthDayDate date = JulianMarchUtil.translate(marchDoy, JulianMarchMonth.JANUARY);

        // check year now we are certain it is correct
        int year = ChronoField.YEAR.checkValidIntValue(date.getYear());
        return new GregorianDate(year, date.getMonthValue(), date.getDayOfMonth());
    }

    private static GregorianDate create(final int year, int month, int dayOfMonth) {
        if (dayOfMonth > 28) {
            final int dom = switch (month) {
                case 2 -> GregorianChronology.INSTANCE.isLeapYear(year) ? 29 : 28;
                case 4, 6, 9, 11 -> 30;
                default -> 31;
            };

            if (dayOfMonth > dom) {
                if (dayOfMonth == 29) {
                    throw new DateTimeException("Invalid date 'February 29' as '" + year + "' is not a leap year");
                } else {
                    throw new DateTimeException("Invalid date '" + Month.of(month).name() + " " + dayOfMonth + "'");
                }
            }
        }
        return new GregorianDate(year, month, dayOfMonth);
    }

    public static GregorianDate from(final TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        final GregorianDate date = temporal.query(TemporalQueries.gregorianDate());
        if (date == null) {
            throw new DateTimeException(
                    String.format("Unable to obtain GregorianDate from TemporalAccessor: %s  of type %s",
                            temporal, temporal.getClass().getName()));
        }
        return date;
    }

    public static GregorianDate now(final Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return GregorianDate.ofEpochDay(TemporalUtil.instantEpoch(clock));
    }

    public static GregorianDate now(final ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static GregorianDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static GregorianDate parse(final CharSequence text) {
        throw new UnsupportedOperationException();
//        return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static GregorianDate parse(final CharSequence text, final DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, GregorianDate::from);
    }
}
