package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.function.Predicate;

/**
 * <div class="fr">Constructeur dans lequel le prédicat appliqué au jeton courant est déterminé par un simple index.
 * </div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 */
public interface PredicateIndexTokenBuilder<I extends Token> extends PredicateTokenBuilder<I> {

    @Override
    default Predicate<? super I> predicate() {
        return predicate(tokens().size());
    }

    Predicate<? super I> predicate(int currentIndex);
}
