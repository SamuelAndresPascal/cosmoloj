package com.cosmoloj.language.api.exception;

import java.util.Collection;

/**
 *
 * @author Samuel Andrés
 * @param <A>
 */
public interface Expectation<A> {

    Collection<A> getAlternatives();

    String alternativeToString(A alternative);

    default String alternativesToString(final String separator) {
        return String.join(separator, getAlternatives()
                .stream()
                .map(this::alternativeToString)
                .toList());
    }
}
