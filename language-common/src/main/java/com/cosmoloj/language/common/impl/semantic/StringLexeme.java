package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme une chaîne de caractères.</span>
 *
 * @author Samuel Andrés
 *
 */
public abstract class StringLexeme extends CharSequenceLexeme implements ParsableLexeme<String> {

    public StringLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    @Override
    public String parse(final String codePoints) {
        return codePoints;
    }
}
