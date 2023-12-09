package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;

/**
 * <span class="fr">Un lexème interprétant une liste de jetons comme la représentation d'un {@link Double}.</span>
 *
 * @author Samuel Andrés
 *
 */
public class DoubleLexeme extends CharSequenceLexeme implements ParsableLexeme {

    public DoubleLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    @Override
    public Object parse(final String codePoints) {
        return Double.parseDouble(codePoints);
    }
}
