package com.cosmoloj.util.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Samuel Andrés
 */
public enum NumberPriority {
    BYTE(Byte.class),
    SHORT(Short.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BIG_INTEGER(BigInteger.class),
    BIG_DECIMAL(BigDecimal.class),
    ADOMIC_INT(AtomicInteger.class),
    ATOMIC_LONG(AtomicLong.class);

    private static final Map<Class, NumberPriority> MAP = Stream.of(NumberPriority.values())
            .collect(Collectors.toMap(v -> v.type, Function.identity()));

    public static int getPriority(final Class<? extends Number> type) {
        final var comp = MAP.get(type);
        if (comp == null) {
            throw new UnsupportedOperationException();
        } else {
            return comp.ordinal();
        }
    }

    private final Class<? extends Number> type;

    NumberPriority(final Class<? extends Number> type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public static int compare(final Number c1, final Number c2) {
        final Number nn1 = asOrSame(c1, c2.getClass());
        final Number nn2 = asOrSame(c2, c1.getClass());
        if (nn1 instanceof Comparable && nn2 instanceof Comparable) {
            return ((Comparable) nn1).compareTo((Comparable) nn2);
        } else if (nn1 instanceof AtomicInteger && nn2 instanceof AtomicInteger) {
            return Integer.compare(((AtomicInteger) nn1).intValue(), ((AtomicInteger) nn2).intValue());
        } else if (nn1 instanceof AtomicLong && nn2 instanceof AtomicLong) {
            return Long.compare(((AtomicLong) nn1).longValue(), ((AtomicLong) nn2).longValue());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Number asOrSame(final Number candidate, final Class<? extends Number> c) {

        if (getPriority(c) > getPriority(candidate.getClass())) {
            return as(candidate, c);
        }
        return candidate;
    }

    public static <T extends Number> T as(final Number candidate, final Class<T> c) {

        final Class<? extends Number> candidateClass = candidate.getClass();

        if (c.isAssignableFrom(candidateClass)) {
            return (T) candidate;
        }

        if (getPriority(c) < getPriority(candidateClass)) {
            throw new IllegalArgumentException();
        }

        // Character sans objet ici
        if (Character.class.equals(candidateClass)) {
            throw new UnsupportedOperationException();
        } else if (Number.class.isAssignableFrom(candidateClass)) {
            if (Byte.class.equals(c)) {
                return (T) (Byte) candidate.byteValue();
            } else if (Short.class.equals(c)) {
                return (T) (Short) candidate.shortValue();
            } else if (Integer.class.equals(c)) {
                return (T) (Integer) candidate.intValue();
            } else if (Long.class.equals(c)) {
                return (T) (Long) candidate.longValue();
            } else if (Float.class.equals(c)) {
                return (T) (Float) candidate.floatValue();
            } else if (Double.class.equals(c)) {
                return (T) (Double) candidate.doubleValue();
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
