package com.cosmoloj.language.api.semantic;

/**
 * <div class="fr">Lexème élémentaire constitué d'une suite de points de code.</div>
 *
 * @author Samuel Andrés
 *
 */
public interface Lexeme extends Token {

    /**
     * <div class="fr">Calcule la sémantique du lexème.</div>
     *
     * @return <span class="fr">sémantique</span>
     */
    Object getSemantics();

    /**
     * <div class="fr">Retourne la liste des points de code qui constituent le lexème sous forme d'une chaîne de
     * caractères.</div>
     *
     * @return <span class="fr">la liste des codePoints constituant le lexème</span>
     */
    String getCodePoints();
}
