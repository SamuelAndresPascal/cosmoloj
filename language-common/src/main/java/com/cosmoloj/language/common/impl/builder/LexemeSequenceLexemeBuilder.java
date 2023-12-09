package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <div class="fr">Constructeur de lexèmes complexes à partir de lexèmes plus simples. Ce constructeur ajoute les
 * lexèmes en concaténant leurs points de codes.</div>
 *
 * @author Samuel Andrés
 * @param <O> <span class="fr">type de jeton produit</span>
 */
public abstract class LexemeSequenceLexemeBuilder<O extends Lexeme> extends CheckTokenBuilder<Lexeme, O> {

    /**
     * <div class="fr">Récupération des points de code du lexème, donnée par la concaténation des points de code des
     * lexèmes qui le constituent.</div>
     * @return
     */
    public String codePoints() {
        final StringBuilder sb = new StringBuilder();
        for (final Lexeme lexeme : tokens()) {
            sb.append(lexeme.getCodePoints());
        }
        return sb.toString();
    }
}
