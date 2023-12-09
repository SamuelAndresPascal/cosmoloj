package com.cosmoloj.language.api.parsing;

import com.cosmoloj.language.api.exception.LanguageException;

/**
 * <div class="fr">Un lexer prédictif est capable de lire un lexème dont on lui indique le type.L'implémentation d'un
 * lexer peut être partiellement prédictive et partiellement imprédictive ; dans ce cas il n'est pas garanti que la
 * méthode prédictive soit capable, dans tous les cas, de lire tous les lexèmes et l'emploi de la méthode non prédictive
 * peut se révéler indispensable en certaines circonstances.</div>
 *
 * @author Samuel Andrés
 */
public interface PredictiveLexer extends Lexer {

    /**
     * <div class="fr">Construit et indexe un lexème du type indiqué en paramètre à partir du point de code courant.
     * </div>
     *
     * @param type <span class="fr">le type de lexème à construire</span>
     * @throws LanguageException <span class="fr"></span>
     */
    void lex(Object type) throws LanguageException;
}
