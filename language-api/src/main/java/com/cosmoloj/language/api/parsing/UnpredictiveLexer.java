package com.cosmoloj.language.api.parsing;

import com.cosmoloj.language.api.exception.LanguageException;

/**
 * <div class="fr">Un lexer non prédictif est capable de lire un lexème sans qu'on lui en indique le type.
 * L'implémentation d'un lexer peut être partiellement prédictive et partiellement imprédictive ; dans ce cas il n'est
 * pas garanti que la méthode non prédictive soit capable, dans tous les cas, de lire tous les lexèmes et l'emploi de la
 * méthode prédictive peut se révéler indispensable en certaines circonstances.</div>
 *
 * @author Samuel Andrés
 */
public interface UnpredictiveLexer extends Lexer {

    /**
     * <div class="fr">Construit et indexe un lexème en fonction de l'état actuel du lexer qui dépend de
     * l'implémentation.</div>
     *
     * @throws LanguageException
     */
    void lex() throws LanguageException;
}
