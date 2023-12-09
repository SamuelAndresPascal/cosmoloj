package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.List;
import java.util.function.Predicate;

/**
 * <div class="fr">Constructeur d'expressions à partir de jetons. La méthode de vérification met le jeton courant en
 * cache de manière à fournire une api manipulant uniquement des prédicats implicitement appliqués sur le jeton
 * courant sans qu'il soit nécessaire de le mentionner en paramètre.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 */
public interface ConstraintHistoryPredicateTokenBuilder<I extends Token>
        extends ConstraintPredicateTokenBuilder<I> {

    @Override
    default Predicate<? super I> predicate() {
        final var history = history(size());
        if (history.size() > 0) {
            return history(size()).get(0);
        } else {
            return t -> false;
        }
    }

    @Override
    default Predicate<? super I> constraint(final int historyIndex) {
        final List<Predicate<? super I>> history = history(size());
        if (history.size() > 1) {
            return history.get(before(historyIndex));
        } else {
            return t -> true;
        }
    }

    List<Predicate<? super I>> history(int currentIndex);
}
