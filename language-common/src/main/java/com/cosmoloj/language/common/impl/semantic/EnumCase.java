package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import java.util.Locale;

/**
 *
 * @author Samuel Andrés
 */
public enum EnumCase {
    SENTITIVE {

        /**
         * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs
         * d'énumération en paramètre.</div>
         *
         * @param <T>
         * @param candidate
         * @param values
         * @return
         */
        @Override
        public <E extends Enum<E> & SemanticEnum<E>> E parse(final String candidate, final E[] values) {
            for (final E value : values) {
                if (value.length() == candidate.length()) {
                    for (int i = 0, n = value.length(); i < n; i++) {
                        if (value.codePointAt(i) != candidate.codePointAt(i)) {
                            break;
                        }
                        if (i + 1 == value.length()) {
                            return value;
                        }
                    }
                }
            }
            return null;
        }
    },
    IGNORE {

        /**
         * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs
         * d'énumération en paramètre, indépendamment de la casse.</div>
         *
         * @param <T>
         * @param candidate
         * @param values
         * @return
         */
        @Override
        public <E extends Enum<E> & SemanticEnum<E>> E parse(final String candidate, final E[] values) {
            for (final E value : values) {
                if (value.length() == candidate.length()) {
                    for (int i = 0, n = value.length(); i < n; i++) {
                        if (!equalCharacterIgnoreCase(value.codePointAt(i), candidate.codePointAt(i))) {
                            break;
                        }
                        if (i + 1 == value.length()) {
                            return value;
                        }
                    }
                }
            }
            return null;
        }
    },
    LOWER {

        /**
         * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs
         * d'énumération en paramètre en bas de casse.</div>
         *
         * @param <T>
         * @param candidate
         * @param values
         * @return
         */
        @Override
        public <E extends Enum<E> & SemanticEnum<E>> E parse(final String candidate, final E[] values) {
            for (final E value : values) {
                if (value.length() == candidate.length()) {
                    for (int i = 0, n = value.length(); i < n; i++) {
                        final var lc = Character.isLowerCase(value.codePointAt(i))
                                ? value.codePointAt(i) : Character.toLowerCase(value.codePointAt(i));
                        if (lc != candidate.codePointAt(i)) {
                            break;
                        }
                        if (i + 1 == value.length()) {
                            return value;
                        }
                    }
                }
            }
            return null;
        }
    };

    public abstract <E extends Enum<E> & SemanticEnum<E>> E parse(String candidate, E[] values);


    public <E extends Enum<E> & SemanticEnum<E>> EnumLexeme<E> map(final E[] values, final Lexeme l) {
        return new EnumLexeme<>(l, values, this);
    }

    public <E extends Enum<E> & SemanticEnum<E>> EnumLexeme<E> lex(
            final String codePoints, final int first, final int last, final int index, final E[] values) {
        return new EnumLexeme<>(codePoints, first, last, index, values, this);
    }

    public <E extends Enum<E> & SemanticEnum<E>> EnumLexemeBuilder<E> builder(
            final Object lexId, final E[] values, final Locale loc) {
        return new EnumLexemeBuilder<>(lexId, values, this, loc);
    }

    public <E extends Enum<E> & SemanticEnum<E>> EnumLexemeBuilder<E> builder(final Object lexId, final E[] values) {
        return new EnumLexemeBuilder<>(lexId, values, this);
    }

    /**
     * <div class="fr">Vérifie l'égalité de deux points de code, indépendamment de la casse.</div>
     *
     * @param reference
     * @param candidate
     * @return
     */
    public static boolean equalCharacterIgnoreCase(final int reference, final int candidate) {
        return reference == candidate
                || (Character.isLowerCase(candidate) && reference == Character.toUpperCase(candidate))
                || (Character.isUpperCase(candidate) && reference == Character.toLowerCase(candidate));
    }
}
