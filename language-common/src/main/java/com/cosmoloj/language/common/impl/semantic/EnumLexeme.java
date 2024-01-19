package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.ParsableLexeme;
import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.LanguageUtil;

/**
 * <div class="fr">Un lexème constitué à partir d'une chaine de caractères interprété comme la valeur d'une
 * énumération.</div>
 *
 * @author Samuel Andrés
 * @param <S> <span class="fr">l'énumération type de la sémantique du lexème</span>
 */
public abstract class EnumLexeme<S extends Enum<S>> extends CharSequenceLexeme implements ParsableLexeme<S> {

    protected EnumLexeme(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    protected EnumLexeme(final Lexeme toMap) {
        super(toMap);
    }

    @Override
    public final S getSemantics() {
        return parse(getCodePoints());
    }

    public static final class CaseSensitive<S extends Enum<S> & SemanticEnum<S>> extends EnumLexeme<S> {

        private final S[] values;

        public CaseSensitive(final String chars, final int start, final int end, final int index, final S[] values) {
            super(chars, start, end, index);
            this.values = values;
        }

        public CaseSensitive(final Lexeme toMap, final S[] values) {
            super(toMap);
            this.values = values;
        }

        @Override
        public S parse(final String codePoints) {
            return LanguageUtil.toEnum(codePoints, this.values);
        }
    }
}
