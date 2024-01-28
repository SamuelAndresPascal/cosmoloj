package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.ParserException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.exception.ParserExceptionBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.ArrayList;
import java.util.List;

/**
 * <div class="fr">Constructeur d'expressions à partir de jetons.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 * @param <O> <span class="fr">type de jeton produit</span>
 */
public abstract class TokenSequenceTokenBuilder<I extends Token, O extends Token> implements TokenBuilder<I, O>,
        StateCheckTokenBuilder<I> {

    private final List<I> tokens = new ArrayList<>();

    /**
     * <div class="fr">Retourne un constructueur de prédicats construit comma le disjonction de vérification d'instance
     * des classes indiquées en paramètres.</div>
     *
     * @param c
     * @param others
     * @return
     */
    protected static PredicateBuilder predicateBuilder(final Class<?> c, final Class<?>... others) {
        return others.length == 0 ? PredicateBuilder.of(c) : PredicateBuilder.of(c).or(others);
    }

    /**
     * <div class="fr">Retourne un constructueur de prédicats construit comma le disjonction de vérification des
     * prédicats d'instances d'énumérations indiquées en paramètres.</div>
     *
     * @param e
     * @param others
     * @return
     */
    protected static PredicateBuilder predicateBuilder(final SemanticEnum<?> e, final SemanticEnum<?>... others) {
        return others.length == 0 ? PredicateBuilder.of(e) : PredicateBuilder.of(e).or(others);
    }

    /**
     * <div class="en">Shortcut to {@link #predicateBuilder(java.lang.Class, java.lang.Class...) }.</div>
     * @param c
     * @param others
     * @return
     */
    protected static PredicateBuilder pb(final Class<?> c, final Class<?>... others) {
        return predicateBuilder(c, others);
    }

    /**
     * <div class="en">Shortcut to {@link #predicateBuilder(com.cosmoloj.language.common.impl.semantic.SemanticEnum,
     * com.cosmoloj.language.common.impl.semantic.SemanticEnum...) }.</div>
     *
     * @param e
     * @param others
     * @return
     */
    protected static PredicateBuilder pb(final SemanticEnum<?> e, final SemanticEnum<?>... others) {
        return predicateBuilder(e, others);
    }

    @Override
    public void add(final I token) {
        tokens.add(token);
    }

    @Override
    public void checkAndAdd(final I token) throws ParserException {
        if (check(token)) {
            add(token);
        } else {
            final Class<? extends I>[] expectations = null; //expectations();
            if (expectations != null) {
               throw new ParserExceptionBuilder(token, index())
                       .types(expectations)
                       .exception();
            }
        }
    }

    @Override
    public List<I> tokens() {
        return tokens;
    }
}
