package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <div class="fr">Un lexème constitué à partir d'une chaine de caractères interprété comme la valeur d'une
 * énumération.</div>
 *
 * @author Samuel Andrés
 * @param <S> <span class="fr">l'énumération type de la sémantique du lexème</span>
 */
public abstract class EnumLexeme<S extends Enum<S>> extends CharSequenceLexeme implements ParsableLexeme {

    public EnumLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
        if (getSemantics() == null) {
            throw new IllegalArgumentException();
        }
    }

    public EnumLexeme(final Lexeme toMap) {
        super(toMap);
        if (getSemantics() == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public abstract S parse(String codePoints);

    @Override
    public final S getSemantics() {
        return parse(getCodePoints());
    }
}
