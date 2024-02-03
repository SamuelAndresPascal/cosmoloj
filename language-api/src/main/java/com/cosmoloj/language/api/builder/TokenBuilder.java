package com.cosmoloj.language.api.builder;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;

/**
 * <div class="fr">Construit une sémantique en agrégeant des éléments.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 * @param <O> <span class="fr">type de jeton produit</span>
 */
public interface TokenBuilder<I extends Token, O extends Token> {

    /**
     * <div class="fr">Vérifie si le {@link TokenBuilder} peut accepter le {@link Token}.
     * La méthode {@link TokenBuilder#add(com.cosmoloj.language.util.Token) } ne devrait pas être appelée sans
     * que cette methode ne l'ait été au préalable et n'ait retourné {@code true}.</div>
     *
     * @param token <span class="fr">le {@link Token} à tester</span>
     * @return <span class="fr">{@code true} si le {@link Token} peut être intégré au {@link TokenBuilder} et
     * {@code false} dans le cas contraire</span>
     */
    boolean check(I token);

    /**
     * <div class="fr">Teste puis accepte le {@link Token} si le test est réussi et lance une exception dans le cas
     * contraire.</div>
     *
     * @param token <span class="fr">le {@link Token} à tester et à accepter</span>
     * @throws LanguageException <span class="fr">si le {@link Token} ne passe pas le test d'acceptation</span>
     */
    void checkAndAdd(I token) throws LanguageException;

    default TokenBuilder<I, O> list(final I... tokens) throws LanguageException {
        for (final I token : tokens) {
            checkAndAdd(token);
        }
        return this;
    }

    /**
     * <div class="fr">Construction du jeton.</div>
     *
     * @return <span class="fr">jeton produit</span>
     */
    O build();
}
