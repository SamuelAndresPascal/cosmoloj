package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.function.Predicate;

/**
 * <div class="fr">La méthode validation du jeton se base sur la vérification d'un prédicat appliqué au jeton courant.
 * </div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 */
public interface PredicateTokenBuilder<I extends Token> extends StateCheckTokenBuilder<I> {

    @Override
    default boolean check() {
        return predicate().test(current());
    }

    Predicate<? super I> predicate();
}
