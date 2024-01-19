package com.cosmoloj.language.api.builder;

import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <div class="fr">Construit un jeton en agrégeant des points de codes. Ce type de construteur de jetons est
 * forcément recyclable car du fait qu'il agrège des éléments primitifs, il n'est pas susceptible de s'utiliser
 * récursivement.</div>
 *
 * @author Samuel Andrés
 */
public interface LexemeBuilder {

    int UNKNOWN_INDEX = Integer.MIN_VALUE;

    /**
     * <div class="fr">Vérifie si le constructeur peut accepter le point de code.
     * La méthode {@link LexemeBuilder#add(int) } ne devrait pas être appelée sans que la methode
     * {@link LexemeBuilder#check(int) } ne l'ait été au préalable et n'ait retourné {@code true}.</div>
     *
     * @param codePoint <span class="fr">point de code à vérifier</span>
     * @return <span class="fr">{@code true} si le point de code peut être intégré au codePoint et {@code false}
     * dans le cas contraire</span>
     */
    boolean check(int codePoint);

    /**
     * <div class="fr">Agrège le point de code.
     * La méthode {@link LexemeBuilder#add(int) } ne devrait pas être appelée sans que la methode
     * {@link LexemeBuilder#check(int) } ne l'ait été au préalable et n'ait retourné {@code true}.</div>
     *
     * @param codePoint <span class="fr">point de code à agréger</span>
     */
    void add(int codePoint);

    default int[] expectations() {
        return null;
    }

    /**
     * <div class="fr">Construction de la sémantique.</div>
     *
     * @param start <span class="fr">début du lexème</span>
     * @param end <span class="fr">fin du lexème</span>
     * @param index <span class="fr">ordre d'interprétation du lexème</span>
     * @return <span class="fr">sémantique construite</span>
     */
    Lexeme build(int start, int end,  int index);


    /**
     * <div class="fr">Permet d'identifier un type de lexème au constructeur courant utilisé dans une analyse
     * syntaxique déterministe où le type de lexème est connu à l'avance.
     * </div>
     */
    Object lexId();

    /**
     * <div class="fr">Permet de rendre le constructeur de jetons réutilisable pour la construction de nouveaux jetons.
     * </div>
     */
    void reset();
}
