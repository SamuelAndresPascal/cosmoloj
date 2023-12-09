package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;

/**
 * <span class="fr">Un lexème interprétant une chaîne de caractères comme la représentation d'un {@link Integer}.</span>
 *
 * @author Samuel Andrés
 */
public class IntegerLexeme extends CharSequenceLexeme implements ParsableLexeme<Integer> {

    public IntegerLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    @Override
    public Integer parse(final String codePoints) {
        return Integer.parseInt(codePoints);
    }
}
