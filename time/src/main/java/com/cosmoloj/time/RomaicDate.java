package com.cosmoloj.time;

import com.cosmoloj.time.chrono.RomaicChronology;
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
import java.time.chrono.ChronoPeriod;
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

/**
 *
 * @author Samuel Andrés
 */
public final class RomaicDate extends WeekDate<RomaicMonth, DayOfWeek>
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    public static final RomaicDate MIN = RomaicDate.of(Year.MIN_VALUE, 1, 1);
    public static final RomaicDate MAX = RomaicDate.of(Year.MAX_VALUE, 12, 31);

    /**
     * Nombre de jours entre le début de l'ère chrétienne (1er janvier 0 pétrinien)
     * et le début de l'ère Java (1er janvier 1970 grégorien).
     *
     * Du 1/1/0 (1er septembre 0000 romaïque) au 1/5/0 (1er janvier 0000
     * romaïque ~ 1er janvier -5509 pétrinien) : on ajoute 122 jours
     * Du 1/1/-5509 pétrinien au 1/1/-5500 pétrinien : on ajoute 8 années de
     * 365 jours et encore 2 jours supplémentaires correspondant aux jours des
     * deux années bissextiles entre -5509 et -5500 (-5508 et -5504).
     * Du 1/1/-5500 au 1/1/0000 pétrinien : 55 siècles
     * Du 1/1/0 (pétrinien) au 1/1/2000 (pétrinien) : 20 siècles
     * Du 1/1/2000 (pétrinien) au 1/1/1970 (pétrinien) : on retire 30 années de
     * 365 jours et encore 7 jours supplémentaires correspondants aux jours des
     * 7 années bissextiles entre 1970 et 2000.
     * Du 1/1/1970 (pétrinien) au 1/1/1970 (grégorien) : on retire 13 jours de
     * décalage entre les deux calendriers à cette date.
     *
     * Vaut ? jours.
     */
    static final long DAYS_0000_ROMAIC_TO_1970_GREGORIAN
            = (JulianUtil.DAYS_PER_CENTURY * 75L) + 122L + (8L * 365L + 2L) - (30L * 365L + 7L) - 13L;

    private RomaicDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public RomaicMonth getMonth() {
        return RomaicMonth.of(getMonthValue());
    }

    @Override
    public boolean isSupported(final TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    @Override
    public long until(final Temporal endExclusive, final TemporalUnit unit) {
        return TemporalUtil.between(this, RomaicDate.from(endExclusive), unit);
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
    }

    @Override
    public ValueRange range(final TemporalField field) {
        if (field instanceof ChronoField) {
            final ChronoField chronoField = (ChronoField) field;
            if (chronoField.isDateBased()) {
                switch (chronoField) {
                    case DAY_OF_MONTH:
                        return ValueRange.of(1, lengthOfMonth());
                    case DAY_OF_YEAR:
                        return ValueRange.of(1, lengthOfYear());
                    case ALIGNED_WEEK_OF_MONTH:
                        return ValueRange.of(1, getMonth() == RomaicMonth.FEBRUARY && !isLeapYear() ? 4 : 5);
                    case YEAR_OF_ERA:
                        return (getYear() <= 0 ? ValueRange.of(1, Year.MAX_VALUE + 1)
                                : ValueRange.of(1, Year.MAX_VALUE));
                    default:
                        return field.range();
                }
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
        switch ((ChronoField) field) {
            case DAY_OF_WEEK:
                return getDayOfWeek().getValue();
            case ALIGNED_DAY_OF_WEEK_IN_MONTH:
                return ((getDayOfMonth() - 1) % 7) + 1;
            case ALIGNED_DAY_OF_WEEK_IN_YEAR:
                return ((getDayOfYear() - 1) % 7) + 1;
            case DAY_OF_MONTH:
                return getDayOfMonth();
            case DAY_OF_YEAR:
                return getDayOfYear();
            case EPOCH_DAY:
                throw new UnsupportedTemporalTypeException(
                        "Invalid field 'EpochDay' for get() method, use getLong() instead");
            case ALIGNED_WEEK_OF_MONTH:
                return ((getDayOfMonth() - 1) / 7) + 1;
            case ALIGNED_WEEK_OF_YEAR:
                return ((getDayOfYear() - 1) / 7) + 1;
            case MONTH_OF_YEAR:
                return getMonthValue();
            case PROLEPTIC_MONTH:
                throw new UnsupportedTemporalTypeException(
                        "Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case YEAR_OF_ERA:
                return (getYear() >= 1 ? getYear() : 1 - getYear());
            case YEAR:
                return getYear();
            case ERA:
                return (getYear() >= 1 ? 1 : 0);
            default:
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
    }

    @Override
    public RomaicChronology getChronology() {
        return RomaicChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        return RomaicMonth.of(getMonthValue()).length(isLeapYear());
    }

    @Override
    public ChronoPeriod until(final ChronoLocalDate endDateExclusive) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(final ChronoLocalDate other) {
        if (other instanceof RomaicDate) {
            return TemporalUtil.compare(this, (RomaicDate) other);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(final ChronoLocalDate other) {
        if (other instanceof RomaicDate) {
            return TemporalUtil.compare(this, (RomaicDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(final ChronoLocalDate other) {
        if (other instanceof RomaicDate) {
            return TemporalUtil.compare(this, (RomaicDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(final ChronoLocalDate other) {
        if (other instanceof RomaicDate) {
            return TemporalUtil.compare(this, (RomaicDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RomaicDate romaic) {
            return TemporalUtil.compare(this, romaic) == 0;
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
            total += (y + 3) / 4;
        } else {
            total -= y / -4;
        }

        // On ajoute le nombre de jours écoulés dans l'année jusqu'au mois en cours.
        total += getDayOfYear() - 1; // Pourquoi (-1) ?

        // On se ramène à l'époque Java
        return total - DAYS_0000_ROMAIC_TO_1970_GREGORIAN;
    }

    @Override
    public DayOfWeek getDayOfWeek() {
        // Le 1-1-1970 grégorien était un jeudi.
        int dow0 = (int) Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
    }

    @Override
    public boolean isLeapYear() {
        return RomaicChronology.INSTANCE.isLeapYear(getYear());
    }

    @Override
    public int getDayOfYear() {
        return getMonth().firstDayOfYear(isLeapYear()) + getDayOfMonth() - 1;
    }

    @Override
    public RomaicDate with(final TemporalAdjuster adjuster) {
        if (adjuster instanceof RomaicDate) {
            return (RomaicDate) adjuster;
        }
        return (RomaicDate) adjuster.adjustInto(this);
    }

    @Override
    public RomaicDate with(final TemporalField field, final long newValue) {
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
                case EPOCH_DAY -> RomaicDate.ofEpochDay(newValue);
                case ALIGNED_WEEK_OF_MONTH -> plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
                case ALIGNED_WEEK_OF_YEAR -> plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
                case MONTH_OF_YEAR -> withMonth((int) newValue);
                case PROLEPTIC_MONTH -> plusMonths(newValue - JulianUtil.getProlepticMonth(getYear(), getMonthValue()));
                case YEAR_OF_ERA -> withYear((int) (getYear() >= 1 ? newValue : 1 - newValue));
                case YEAR -> withYear((int) newValue);
                case ERA -> getLong(ChronoField.ERA) == newValue ? this : withYear(1 - getYear());
                default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            };
        }
        return field.adjustInto(this, newValue);
    }

    public RomaicDate withYear(final int year) {
        if (this.getYear() == year) {
            return this;
        }
        ChronoField.YEAR.checkValidValue(year);
        return resolvePreviousValid(year, getMonthValue(), getDayOfMonth());
    }

    public RomaicDate withMonth(final int month) {
        if (this.getMonthValue() == month) {
            return this;
        }
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        return resolvePreviousValid(getYear(), month, getDayOfMonth());
    }

    public RomaicDate withDayOfMonth(final int dayOfMonth) {
        if (this.getDayOfMonth() == dayOfMonth) {
            return this;
        }
        return of(getYear(), getMonthValue(), dayOfMonth);
    }

    public RomaicDate withDayOfYear(final int dayOfYear) {
        if (this.getDayOfYear() == dayOfYear) {
            return this;
        }
        return ofYearDay(getYear(), dayOfYear);
    }

    private static RomaicDate resolvePreviousValid(final int year, final int month, final int day) {
        final int d = switch (month) {
            case 2 -> Math.min(day, RomaicChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
            case 4, 6, 9, 11 -> Math.min(day, 30);
            default -> day;
        };
        return new RomaicDate(year, month, d);
    }

    @Override
    public RomaicDate plus(final TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period period) {
            return plusMonths(period.toTotalMonths()).plusDays(period.getDays());
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (RomaicDate) amountToAdd.addTo(this);
    }

    @Override
    public RomaicDate plus(final long amountToAdd, final TemporalUnit unit) {
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

    public RomaicDate plusYears(final long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = ChronoField.YEAR.checkValidIntValue(getYear() + yearsToAdd);  // safe overflow
        return resolvePreviousValid(newYear, getMonthValue(), getDayOfMonth());
    }

    public RomaicDate plusMonths(final long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = getYear() * 12L + (getMonthValue() - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = (int) Math.floorMod(calcMonths, 12) + 1;
        return resolvePreviousValid(newYear, newMonth, getDayOfMonth());
    }

    public RomaicDate plusWeeks(final long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    public RomaicDate plusDays(final long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return RomaicDate.ofEpochDay(mjDay);
    }

    @Override
    public RomaicDate minus(final TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period period) {
            return minusMonths(period.toTotalMonths()).minusDays(period.getDays());
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (RomaicDate) amountToSubtract.subtractFrom(this);
    }

    @Override
    public RomaicDate minus(final long amountToSubtract, final TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE
                ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    public RomaicDate minusYears(final long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE
                ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    public RomaicDate minusMonths(final long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE
                ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    public RomaicDate minusWeeks(final long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE
                ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeksToSubtract));
    }

    public RomaicDate minusDays(final long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    @Override
    public <R> R query(final TemporalQuery<R> query) {
        if (query == TemporalQueries.romaicDate()) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public String toString() {
        return FormatUtil.toString(getYear(), getMonthValue(), getDayOfMonth());
    }

    public static RomaicDate of(final int year, final int month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month, dayOfMonth);
    }

    public static RomaicDate of(final int year, final RomaicMonth month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        Objects.requireNonNull(month, "month");
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month.getValue(), dayOfMonth);
    }

    public static RomaicDate ofYearDay(final int year, final int dayOfYear) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = RomaicChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && !leap) {
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");
        }
        RomaicMonth moy = RomaicMonth.of((dayOfYear - 1) / 31 + 1);
        int monthEnd = moy.firstDayOfYear(leap) + moy.length(leap) - 1;
        if (dayOfYear > monthEnd) {
            moy = moy.plus(1);
        }
        int dom = dayOfYear - moy.firstDayOfYear(leap) + 1;
        return new RomaicDate(year, moy.getValue(), dom);
    }

    public static RomaicDate ofEpochDay(final long epochDay) {

        // Conversion en jours depuis le 1/1/0 pétrinien
        final long zeroDay = epochDay + DAYS_0000_ROMAIC_TO_1970_GREGORIAN;

        /*
        Numéro de jour dans l'année commençant le 1er mars.
        Ce calcul nécessite de décaler le décompte des jours début mars.
        On enlève donc 182 jours correspondant : 30 jours de semptembre,
        31 d'octobre, 30 de novembre, 31 de décembre, 31 de janvier et 29 de
        février 0000 qui est bisextile.
        */
        final YearDayDate dy = JulianMarchUtil.toDayOfYear(zeroDay - 182);

        // convert march-based values back to september-based
        final YearMonthDayDate date = JulianMarchUtil.translate(dy, JulianMarchMonth.SEPTEMBER);

        // check year now we are certain it is correct
        int year = ChronoField.YEAR.checkValidIntValue(date.getYear());
        return new RomaicDate(year, date.getMonthValue(), date.getDayOfMonth());
    }

    private static RomaicDate create(final int year, final int month, final int dayOfMonth) {
        if (dayOfMonth > 28) {
            final int dom = switch (month) {
                case 2 -> RomaicChronology.INSTANCE.isLeapYear(year) ? 29 : 28;
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
        return new RomaicDate(year, month, dayOfMonth);
    }

    public static RomaicDate from(final TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        RomaicDate date = temporal.query(TemporalQueries.romaicDate());
        if (date == null) {
            throw new DateTimeException(
                    String.format("Unable to obtain RomaicDate from TemporalAccessor: %s of type %s",
                            temporal, temporal.getClass().getName()));
        }
        return date;
    }

    public static RomaicDate now(final Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return RomaicDate.ofEpochDay(TemporalUtil.instantEpoch(clock));
    }

    public static RomaicDate now(final ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static RomaicDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static RomaicDate parse(final CharSequence text) {
        throw new UnsupportedOperationException();
//        return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static RomaicDate parse(final CharSequence text, final DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, RomaicDate::from);
    }
}
