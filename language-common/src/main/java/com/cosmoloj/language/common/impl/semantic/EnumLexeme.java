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
public class EnumLexeme<S extends Enum<S> & SemanticEnum<S>> extends CharSequenceLexeme implements ParsableLexeme<S> {

    private final S[] values;
    private final EnumCase ec;

    public EnumLexeme(final String codePoints, final int first, final int last, final int index, final S[] values,
            final EnumCase ec) {
        super(codePoints, first, last, index);
        this.values = values;
        this.ec = ec;
    }

    public EnumLexeme(final Lexeme toMap, final S[] values, final EnumCase ec) {
        super(toMap);
        this.values = values;
        this.ec = ec;
    }

    @Override
    public S parse(final String codePoints) {
        return ec.parse(codePoints, this.values);
    }
}
