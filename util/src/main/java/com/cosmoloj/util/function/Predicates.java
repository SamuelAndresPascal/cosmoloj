package com.cosmoloj.util.function;

import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public final class Predicates {

    private Predicates() {
    }

    public static <P> Predicate<P> yes() {
        return t -> true;
    }

    public static <P> Predicate<P> no() {
        return t -> false;
    }

    public static <P> Predicate<P> of(final Predicate<P> pointer) {
        return pointer;
    }

    public static Predicate<Object> or(final Predicate<Object>[] values) {
        Predicate<Object> result = t -> false;
        for (final Predicate<Object> value : values) {
            result = result.or(value);
        }
        return result;
    }

    public static <T extends Enum<T> & Predicate<Object>> Predicate<Object> or(final Class<T> c) {
        return or(c.getEnumConstants());
    }

    public static Predicate<Object> and(final Predicate<Object>[] values) {
        Predicate<Object> result = t -> true;
        for (final Predicate<Object> value : values) {
            result = result.and(value);
        }
        return result;
    }
}
