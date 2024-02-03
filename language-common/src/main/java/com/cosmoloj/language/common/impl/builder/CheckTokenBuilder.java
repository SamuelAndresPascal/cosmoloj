package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;

/**
 * <div class="fr">Constructeur d'expressions à partir de jetons. La méthode de vérification met le jeton courant en
 * cache de manière à fournire une api manipulant uniquement des prédicats implicitement appliqués sur le jeton
 * courant sans qu'il soit nécessaire de le mentionner en paramètre.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 * @param <O> <span class="fr">type de jeton produit</span>
 */
public abstract class CheckTokenBuilder<I extends Token, O extends Token> extends TokenSequenceTokenBuilder<I, O> {

    // jeton en cours de vérification
    private I waitingForValidation;

    /**
     * <div class="fr">Affecte le jeton à vérifier au jeton courant, puis appelle une méthode de vérification à
     * implémenter en fonction du jeton courant et de l'état du constructeur (jetons passés).</div>
     *
     * @param in
     * @return
     */
    @Override
    public final boolean check(final I in) {
        this.waitingForValidation = in;
        final boolean result = acceptWaiting();
        this.waitingForValidation = null;
        return result;
    }

    /**
     *
     * @return <span class="fr">jeton courant en cours de vérification</span>
     */
    @Override
    public I waitingToken() {
        return waitingForValidation;
    }
}
