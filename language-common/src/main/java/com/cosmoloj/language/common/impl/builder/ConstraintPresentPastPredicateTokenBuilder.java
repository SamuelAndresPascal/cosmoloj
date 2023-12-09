package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.ArrayList;
import java.util.Collections;
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
public interface ConstraintPresentPastPredicateTokenBuilder<I extends Token>
        extends ConstraintHistoryPredicateTokenBuilder<I> {

    @Override
    default List<Predicate<? super I>> history(int currentIndex) {
        final Predicate<? super I> present = present(currentIndex);
        final List<Predicate<? super I>> past = past(currentIndex);
        if (past.isEmpty()) {
            return List.of(present);
        } else {
            final List<Predicate<? super I>> result = new ArrayList<>();
            result.add(present);
            result.addAll(past);
            return Collections.unmodifiableList(result);
        }
    }

    Predicate<? super I> present(int currentIndex);

    List<Predicate<? super I>> past(int currentIndex);
}
