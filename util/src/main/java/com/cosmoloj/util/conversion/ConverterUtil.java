package com.cosmoloj.util.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Samuel Andrés
 */
public class ConverterUtil {

    public static Class<? extends Comparable> comparablePriority(final Iterable<Class<? extends Comparable>> classes) {
        Class<? extends Comparable> result = null;
        for (final Class<? extends Comparable> c : classes) {
            if (result == null || ComparablePriority.getPriority(c) > ComparablePriority.getPriority(result)) {
                result = c;
            }
        }
        return result;
    }

    public Integer getInteger(final Short s) {
        return s.intValue();
    }

    public Long getLong(final Short s) {
        return (long) s;
    }

    public Float getFloat(final Short s) {
        return (float) s;
    }

    public Double getDouble(final Short s) {
        return (double) s;
    }

    public Long getLong(final Integer i) {
        return (long) i;
    }

    public Float getFloat(final Integer i) {
        return (float) i;
    }

    public Double getDouble(final Integer i) {
        return (double) i;
    }

    public Float getFloat(final Long l) {
        return (float) l;
    }

    public Double getDouble(final Long f) {
        return (double) f;
    }

    public Double getDouble(final Float f) {
        return (double) f;
    }


    public static Class<Comparable> prior(final Class... c) {
        int p = ComparablePriority.getPriority(c[0]);
        for (final Class k : c) {
            if (ComparablePriority.getPriority(k) > p) {
                p = ComparablePriority.getPriority(k);
            }
        }
        return ComparablePriority.values()[p].getType();
    }



    public static <T> T coco(final Number first, final Number second) {
        if (first instanceof BigDecimal || second instanceof BigDecimal) {
            throw new UnsupportedOperationException();
        } else if (first instanceof BigInteger || second instanceof BigInteger) {
            throw new UnsupportedOperationException();
        } else if (first instanceof Double || second instanceof Double) {
            return (T) new Double(first.doubleValue() + second.doubleValue());
        } else if (first instanceof Float || second instanceof Float) {
            return (T) new Float(first.floatValue() + second.floatValue());
        } else if (first instanceof Long || second instanceof Long) {
            return (T) new Long(first.longValue() + second.longValue());
        } else if (first instanceof Integer || second instanceof Integer) {
            return (T) new Integer(first.intValue() + second.intValue());
        } else if (first instanceof Short || second instanceof Short) {
            return (T) new Short((short) (first.shortValue() + second.shortValue()));
        } else if (first instanceof Byte || second instanceof Byte) {
            return (T) new Byte((byte) (first.byteValue() + second.byteValue()));
        } else if (first instanceof AtomicLong || second instanceof AtomicLong) {
            throw new UnsupportedOperationException();
        } else if (first instanceof AtomicInteger || second instanceof BigInteger) {
            throw new UnsupportedOperationException();
        }
        return null;
    }
}
