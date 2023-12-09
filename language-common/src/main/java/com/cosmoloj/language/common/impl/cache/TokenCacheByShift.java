package com.cosmoloj.language.common.impl.cache;

import com.cosmoloj.language.api.semantic.Token;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public class TokenCacheByShift<T extends Token> implements TokenCache<T> {

    private final T[] precedings;

    public TokenCacheByShift(final T[] tab) {
        precedings = tab;
    }

    @Override
    public void add(final T candidate) {
        for (int i = 0, n = precedings.length - 1; i < n; i++) {
            precedings[i] = precedings[i + 1];
        }
        precedings[precedings.length - 1] = candidate;
    }

    @Override
    public T get(final int order) {
        return precedings[precedings.length - 1 - order];
    }
}
