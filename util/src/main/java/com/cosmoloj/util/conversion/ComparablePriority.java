package com.cosmoloj.util.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Samuel Andrés
 */
public enum ComparablePriority {
    BYTE(Byte.class),
    CHARACTER(Character.class),
    SHORT(Short.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BIG_INTEGER(BigInteger.class),
    BIG_DECIMAL(BigDecimal.class),
    STRING(String.class);

    private static final Map<Class, ComparablePriority> MAP = new HashMap<>();
    static {
        for (final ComparablePriority c : ComparablePriority.values()) {
            MAP.put(c.c, c);
        }
    }

    public static int getPriority(final Class c) {
        final ComparablePriority comp = MAP.get(c);
        if (comp == null) {
            throw new UnsupportedOperationException();
        } else {
            return comp.ordinal();
        }
    }

    private final Class c;

    ComparablePriority(final Class<? extends Comparable> c) {
        this.c = c;
    }

    public Class getType() {
        return c;
    }

    public static int compare(final Comparable c1, final Comparable c2) {
        return asOrSame(c1, c2.getClass()).compareTo(asOrSame(c2, c1.getClass()));
    }

    public static Comparable asOrSame(final Comparable candidate, final Class<? extends Comparable> c) {

        if (getPriority(c) > getPriority(candidate.getClass())) {
            return as(candidate, c);
        }
        return candidate;
    }

    public static <T extends Comparable> T as(final Comparable candidate, final Class<T> c) {

        final Class<? extends Comparable> candidateClass = candidate.getClass();

        if (c.isAssignableFrom(candidateClass)) {
            return (T) candidate;
        }

        if (getPriority(c) < getPriority(candidateClass)) {
            throw new IllegalArgumentException();
        }

        if (Character.class.equals(candidateClass)) {
            throw new UnsupportedOperationException();
        } else if (Number.class.isAssignableFrom(candidateClass)) {
            if (Byte.class.equals(c)) {
                return (T) (Byte) ((Number) candidate).byteValue();
            } else if (Short.class.equals(c)) {
                return (T) (Short) ((Number) candidate).shortValue();
            } else if (Integer.class.equals(c)) {
                return (T) (Integer) ((Number) candidate).intValue();
            } else if (Long.class.equals(c)) {
                return (T) (Long) ((Number) candidate).longValue();
            } else if (Float.class.equals(c)) {
                return (T) (Float) ((Number) candidate).floatValue();
            } else if (Double.class.equals(c)) {
                return (T) (Double) ((Number) candidate).doubleValue();
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(final String[] argv) {
        System.out.println(Long.MAX_VALUE);
        System.out.println((float) Long.MAX_VALUE);
        final float f = (float) Long.MAX_VALUE;
        System.out.println((long) f);
        final float f1 = f - 1;
        System.out.println(f1);
        System.out.println((long) f1);
        final float f2 = f - 2036854775807L;
        System.out.println(f2);
        System.out.println((long) f2);

        System.out.println((float) 1.2f + 3L);
        System.out.println(1.2f);
        System.out.println(1.2d);


        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE - 1);
        System.out.println(Integer.MAX_VALUE - 1.f);
        System.out.println((long) (Integer.MAX_VALUE - 1.f));
        System.out.println((long) (Integer.MAX_VALUE - 1.));

        final BigInteger bi = new BigInteger("2313465745137437542531321373412113743734313734351343543131");
        final BigInteger bi2 = new BigInteger("1");

        System.out.println(bi.add(bi2));

        final BigDecimal bd = new BigDecimal("1e15684");

        System.out.println(bd.add(new BigDecimal(bi2)));
    }
}
