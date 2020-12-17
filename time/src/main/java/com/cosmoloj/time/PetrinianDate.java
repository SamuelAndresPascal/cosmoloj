package com.cosmoloj.time;

import com.cosmoloj.time.chrono.PetrinianChronology;
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
public final class PetrinianDate extends WeekDate<Month, DayOfWeek>
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    public static final PetrinianDate MIN = PetrinianDate.of(Year.MIN_VALUE, 1, 1);
    public static final PetrinianDate MAX = PetrinianDate.of(Year.MAX_VALUE, 12, 31);

    /**
     * Nombre de jours entre le début de l'ère chrétienne (1er janvier 0 pétrinien)
     * et le début de l'ère Java (1er janvier 1970 grégorien).
     *
     *
     * Du 1/1/0 (pétrinien) au 1/1/2000 (pétrinien) : 20 siècles
     * Du 1/1/2000 (pétrinien) au 1/1/1970 (pétrinien) : on retire 30 années de
     * 365 jours et encore 7 jours supplémentaires correspondants aux jours des
     * 7 années bissextiles entre 1970 et 2000.
     * Du 1/1/1970 (pétrinien) au 1/1/1970 (grégorien) : on retire 13 jours de
     * décalage entre les deux calendriers à cette date (*).
     *
     * Vaut 719730 jours.
     *
     * Note : Il y a 719730 jours entre le 1/1/1 pétrinien et le 1/1/1970
     * grégorien alors qu'il y a 719728 jours entre le 1/1/1 grégorien et le
     * 1/1/1970 grégorien car le 1/1/1 pétrinien et le 1/1/1 pétrinien sont
     * en décalage théorique de deux jours à cette date (*).
     *
     * (*) Les deux calendriers sont synchronisés au 1er janvier 200
     * (grégorien/pétrinien) et pendant tout le 3e siècle. Le calendrier
     * grégorien compense le décalage astronomique du calendrier pétrinien
     * environ depuis le Concile de Nicée qui a établi la pascalie au 4e siècle
     * (325).
     */
    static final long DAYS_0000_PETRINIAN_TO_1970_GREGORIAN
            = (JulianUtil.DAYS_PER_CENTURY * 20L) - (30L * 365L + 7L) - 13L;

    private PetrinianDate(int year, int month, int dayOfMonth) {
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
        return TemporalUtil.between(this, PetrinianDate.from(endExclusive), unit);
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
                        return ValueRange.of(1, getMonth() == Month.FEBRUARY && !isLeapYear() ? 4 : 5);
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
    public PetrinianChronology getChronology() {
        return PetrinianChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        return Month.of(getMonthValue()).length(isLeapYear());
    }

    @Override
    public ChronoPeriod until(final ChronoLocalDate endDateExclusive) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(final ChronoLocalDate other) {
        if (other instanceof PetrinianDate) {
            return TemporalUtil.compare(this, (PetrinianDate) other);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(final ChronoLocalDate other) {
        if (other instanceof PetrinianDate) {
            return TemporalUtil.compare(this, (PetrinianDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(final ChronoLocalDate other) {
        if (other instanceof PetrinianDate) {
            return TemporalUtil.compare(this, (PetrinianDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(final ChronoLocalDate other) {
        if (other instanceof PetrinianDate) {
            return TemporalUtil.compare(this, (PetrinianDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PetrinianDate) {
            return TemporalUtil.compare(this, (PetrinianDate) obj) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
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
        return total - DAYS_0000_PETRINIAN_TO_1970_GREGORIAN;
    }

    @Override
    public DayOfWeek getDayOfWeek() {
        // Le 1-1-1970 grégorien était un jeudi.
        int dow0 = (int) Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
    }

    @Override
    public boolean isLeapYear() {
        return PetrinianChronology.INSTANCE.isLeapYear(getYear());
    }

    @Override
    public int getDayOfYear() {
        return getMonth().firstDayOfYear(isLeapYear()) + getDayOfMonth() - 1;
    }

    @Override
    public PetrinianDate with(final TemporalAdjuster adjuster) {
        if (adjuster instanceof PetrinianDate) {
            return (PetrinianDate) adjuster;
        }
        return (PetrinianDate) adjuster.adjustInto(this);
    }

    @Override
    public PetrinianDate with(final TemporalField field, final long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            f.checkValidValue(newValue);
            switch (f) {
                case DAY_OF_WEEK:
                    return plusDays(newValue - getDayOfWeek().getValue());
                case ALIGNED_DAY_OF_WEEK_IN_MONTH:
                    return plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case ALIGNED_DAY_OF_WEEK_IN_YEAR:
                    return plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case DAY_OF_MONTH:
                    return withDayOfMonth((int) newValue);
                case DAY_OF_YEAR:
                    return withDayOfYear((int) newValue);
                case EPOCH_DAY:
                    return PetrinianDate.ofEpochDay(newValue);
                case ALIGNED_WEEK_OF_MONTH:
                    return plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
                case ALIGNED_WEEK_OF_YEAR:
                    return plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
                case MONTH_OF_YEAR:
                    return withMonth((int) newValue);
                case PROLEPTIC_MONTH:
                    return plusMonths(newValue - JulianUtil.getProlepticMonth(getYear(), getMonthValue()));
                case YEAR_OF_ERA:
                    return withYear((int) (getYear() >= 1 ? newValue : 1 - newValue));
                case YEAR:
                    return withYear((int) newValue);
                case ERA:
                    return (getLong(ChronoField.ERA) == newValue ? this : withYear(1 - getYear()));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
        }
        return field.adjustInto(this, newValue);
    }

    public PetrinianDate withYear(final int year) {
        if (this.getYear() == year) {
            return this;
        }
        ChronoField.YEAR.checkValidValue(year);
        return resolvePreviousValid(year, getMonthValue(), getDayOfMonth());
    }

    public PetrinianDate withMonth(final int month) {
        if (this.getMonthValue() == month) {
            return this;
        }
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        return resolvePreviousValid(getYear(), month, getDayOfMonth());
    }

    public PetrinianDate withDayOfMonth(int dayOfMonth) {
        if (this.getDayOfMonth() == dayOfMonth) {
            return this;
        }
        return of(getYear(), getMonthValue(), dayOfMonth);
    }

    public PetrinianDate withDayOfYear(final int dayOfYear) {
        if (this.getDayOfYear() == dayOfYear) {
            return this;
        }
        return ofYearDay(getYear(), dayOfYear);
    }

    private static PetrinianDate resolvePreviousValid(final int year, final int month, int day) {
        switch (month) {
            case 2:
                day = Math.min(day, PetrinianChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = Math.min(day, 30);
                break;
            default:
        }
        return new PetrinianDate(year, month, day);
    }

    @Override
    public PetrinianDate plus(final TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period) {
            Period periodToAdd = (Period) amountToAdd;
            return plusMonths(periodToAdd.toTotalMonths()).plusDays(periodToAdd.getDays());
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (PetrinianDate) amountToAdd.addTo(this);
    }

    @Override
    public PetrinianDate plus(final long amountToAdd, final TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case DAYS:
                    return plusDays(amountToAdd);
                case WEEKS:
                    return plusWeeks(amountToAdd);
                case MONTHS:
                    return plusMonths(amountToAdd);
                case YEARS:
                    return plusYears(amountToAdd);
                case DECADES:
                    return plusYears(Math.multiplyExact(amountToAdd, 10));
                case CENTURIES:
                    return plusYears(Math.multiplyExact(amountToAdd, 100));
                case MILLENNIA:
                    return plusYears(Math.multiplyExact(amountToAdd, 1000));
                case ERAS:
                    return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), amountToAdd));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            }
        }
        return unit.addTo(this, amountToAdd);
    }

    public PetrinianDate plusYears(final long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = ChronoField.YEAR.checkValidIntValue(getYear() + yearsToAdd);  // safe overflow
        return resolvePreviousValid(newYear, getMonthValue(), getDayOfMonth());
    }

    public PetrinianDate plusMonths(final long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = getYear() * 12L + (getMonthValue() - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = (int) Math.floorMod(calcMonths, 12) + 1;
        return resolvePreviousValid(newYear, newMonth, getDayOfMonth());
    }

    public PetrinianDate plusWeeks(final long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    public PetrinianDate plusDays(final long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return PetrinianDate.ofEpochDay(mjDay);
    }

    @Override
    public PetrinianDate minus(final TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period) {
            Period periodToSubtract = (Period) amountToSubtract;
            return minusMonths(periodToSubtract.toTotalMonths()).minusDays(periodToSubtract.getDays());
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (PetrinianDate) amountToSubtract.subtractFrom(this);
    }

    @Override
    public PetrinianDate minus(final long amountToSubtract, final TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE
                ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    public PetrinianDate minusYears(final long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE
                ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    public PetrinianDate minusMonths(final long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE
                ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    public PetrinianDate minusWeeks(final long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE
                ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeksToSubtract));
    }

    public PetrinianDate minusDays(final long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    @Override
    public <R> R query(final TemporalQuery<R> query) {
        if (query == TemporalQueries.petrinianDate()) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public String toString() {
        return FormatUtil.toString(getYear(), getMonthValue(), getDayOfMonth());
    }

    public static PetrinianDate of(final int year, final int month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month, dayOfMonth);
    }

    public static PetrinianDate of(final int year, final Month month, final int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        Objects.requireNonNull(month, "month");
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month.getValue(), dayOfMonth);
    }

    public static PetrinianDate ofYearDay(final int year, final int dayOfYear) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = PetrinianChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && !leap) {
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");
        }
        Month moy = Month.of((dayOfYear - 1) / 31 + 1);
        int monthEnd = moy.firstDayOfYear(leap) + moy.length(leap) - 1;
        if (dayOfYear > monthEnd) {
            moy = moy.plus(1);
        }
        final int dom = dayOfYear - moy.firstDayOfYear(leap) + 1;
        return new PetrinianDate(year, moy.getValue(), dom);
    }

    public static PetrinianDate ofEpochDay(final long epochDay) {
        // Conversion en jours depuis le 1/1/0 pétrinien
        long zeroDay = epochDay + DAYS_0000_PETRINIAN_TO_1970_GREGORIAN;

        /*
        Numéro de jour dans l'année commençant le 1er mars.
        Ce calcul nécessite de décaler le décompte des jours début mars.
        On enlève donc 60 jours correspondant aux 31 jours de janvier et 29
        jours de février 0000 qui est bisextile.
        */
        final YearDayDate marchDoy = JulianMarchUtil.toDayOfYear(zeroDay - 60);

        // Retour à l'année commençant en janvier.
        final YearMonthDayDate date = JulianMarchUtil.translate(marchDoy, JulianMarchMonth.JANUARY);

        // check year now we are certain it is correct
        int year = ChronoField.YEAR.checkValidIntValue(date.getYear());
        return new PetrinianDate(year, date.getMonthValue(), date.getDayOfMonth());
    }

    private static PetrinianDate create(final int year, final int month, final int dayOfMonth) {
        if (dayOfMonth > 28) {
            int dom = 31;
            switch (month) {
                case 2:
                    dom = (PetrinianChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dom = 30;
                    break;
                default:
            }
            if (dayOfMonth > dom) {
                if (dayOfMonth == 29) {
                    throw new DateTimeException("Invalid date 'February 29' as '" + year + "' is not a leap year");
                } else {
                    throw new DateTimeException("Invalid date '" + Month.of(month).name() + " " + dayOfMonth + "'");
                }
            }
        }
        return new PetrinianDate(year, month, dayOfMonth);
    }

    public static PetrinianDate from(final TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        PetrinianDate date = temporal.query(TemporalQueries.petrinianDate());
        if (date == null) {
            throw new DateTimeException(
                    String.format("Unable to obtain PetrinianDate from TemporalAccessor: %s of type %s",
                            temporal, temporal.getClass().getName()));
        }
        return date;
    }

    public static PetrinianDate now(final Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return PetrinianDate.ofEpochDay(TemporalUtil.instantEpoch(clock));
    }

    public static PetrinianDate now(final ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static PetrinianDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static PetrinianDate parse(final CharSequence text) {
        throw new UnsupportedOperationException();
//        return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static PetrinianDate parse(final CharSequence text, final DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, PetrinianDate::from);
    }
}
