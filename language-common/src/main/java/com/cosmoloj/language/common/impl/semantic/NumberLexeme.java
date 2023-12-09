package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'un {@link Integer}.</span>
 *
 * @author Samuel Andrés
 *
 */
public abstract class NumberLexeme extends CharSequenceLexeme implements ParsableLexeme<Number> {

    public NumberLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }
}
