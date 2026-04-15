package com.cosmoloj.util.conversion;

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
}
