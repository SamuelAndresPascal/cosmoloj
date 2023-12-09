package com.cosmoloj.language.api.parsing;

import com.cosmoloj.language.api.semantic.Token;
import java.util.List;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <div class="fr">Permet de construire des lexèmes élémentaires constitués de points de codes.</div>
 *
 * @author Samuel Andrés
 */
public interface Lexer {

    List<Token> getIndexes();

    /**
     * <div class="fr">Dernier lexème lu.</div>
     *
     * @return
     */
    Lexeme lexeme();

    /**
     * <div class="fr">Parcourt les caractères à ignorer jusqu'au potentiel lexème suivant.</div>
     */
    void flush();

    /**
     * <div class="fr">Valeur du point de code courant. Les valeurs négatives peuvent être utilisées pour décrire des
     * états particulier, en particulier des codes d'erreur.</div>
     *
     * @return <span class="fr">la valeur du point de code courant</span>
     */
    int codePoint();

    default int flushTo() {
        flush();
        return codePoint();
    }

    /**
     * <div class="fr">Vrai lorsqu'il n'y a plus de point de code à lire, faux sinon.</div>
     *
     * @return <span class="fr">vrai lorsqu'il n'y a plus de point de code à lire, faux sinon</span>
     */
    boolean end();
}
