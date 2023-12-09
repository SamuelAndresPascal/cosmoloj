package com.cosmoloj.language.common.impl.cache;

import com.cosmoloj.language.api.semantic.Token;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public class TokenCacheByCopy<T extends Token> implements TokenCache<T> {

    /*
    Lexèmes précédant le lexème lu. L'index 0 est le lexème immédiantement précédent. L'index 1 est le lexème précédent
    le lexème d'index 0.
    */
    private final T[] precedings;

    public TokenCacheByCopy(final T[] tab) {
        precedings = tab;
    }

    @Override
    public void add(final T candidate) {
        System.arraycopy(precedings, 1, precedings, 0, precedings.length - 1);
        precedings[precedings.length - 1] = candidate;
    }

    @Override
    public T get(final int order) {
        return precedings[precedings.length - 1 - order];
    }
}
