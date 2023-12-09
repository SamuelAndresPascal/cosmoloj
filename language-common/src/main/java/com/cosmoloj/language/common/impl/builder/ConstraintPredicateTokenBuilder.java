package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.function.Predicate;

/**
 * <div class="fr">Constructeur d'expressions à partir de jetons. La méthode de vérification met le jeton courant en
 * cache de manière à fournire une api manipulant uniquement des prédicats implicitement appliqués sur le jeton
 * courant sans qu'il soit nécessaire de le mentionner en paramètre.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 */
public interface ConstraintPredicateTokenBuilder<I extends Token> extends PredicateTokenBuilder<I> {

    @Override
    default boolean check() {
        if (predicate().test(current())) {
            for (int i = 0; i < tokens().size(); i++) {
                if (!constraint(i).test(tokens().get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * <div class="fr">Contrainte à définir sur le jeton dont l'index dans l'historique est indiqué en paramètre.
     * Cette méthode est appelée à chaque requête d'insertion d'un nouveau jeton, sur tous les jetons précédemment
     * insérés, en fournissant son index.</div>
     *
     * @param historyIndex <span class="fr">index du jeton à vérifier dans l'historique</span>
     * @return <span class="fr">contrat que jeton de l'historique indiqué en paramètre est tenu de respecter</span>
     */
    Predicate<? super I> constraint(int historyIndex);
}
