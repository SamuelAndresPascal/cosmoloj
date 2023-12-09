package com.cosmoloj.language.api.semantic;

/**
 * <span class="fr">Lexème dont la sémantique est déduite à partir d'une fonction appliquée aux points de code.</span>
 *
 * @author Samuel Andrés
 * @param <S>
 *
 */
public interface ParsableLexeme<S> extends Lexeme {

    S parse(String codePoints);

    @Override
    default S getSemantics() {
        return parse(getCodePoints());
    }
}
