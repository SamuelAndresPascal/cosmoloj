package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.List;
import java.util.function.Predicate;

/**
 * <div class="fr">Constructeur dans lequel le prédicat appliqué au jeton courant est déterminé par un simple index.
 * </div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 */
public interface PredicateListTokenBuilder<I extends Token> extends PredicateTokenBuilder<I> {

    @Override
    default Predicate<? super I> predicate() {
        return predicates().get(tokens().size());
    }

    List<Predicate<? super I>> predicates();
}
