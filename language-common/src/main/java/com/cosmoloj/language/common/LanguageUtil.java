package com.cosmoloj.language.common;

import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public final class LanguageUtil {

    private LanguageUtil() {
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

    /**
     * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs d'énumération
     * en paramètre, indépendamment de la casse.</div>
     *
     * @param <T>
     * @param candidate
     * @param values
     * @return
     */
    public static <T extends SemanticEnum<?>> T toEnumIgnoreCase(final String candidate, final T[] values) {
        for (final T value : values) {
            if (value.length() == candidate.length()) {
                for (int i = 0, n = value.length(); i < n; i++) {
                    if (!LanguageUtil.equalCharacterIgnoreCase(value.codePointAt(i), candidate.codePointAt(i))) {
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

    /**
     * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs d'énumération
     * en paramètre en bas de casse.</div>
     *
     * @param <T>
     * @param candidate
     * @param values
     * @return
     */
    public static <T extends SemanticEnum<?>> T toEnumLowerCase(final String candidate, final T[] values) {
        for (final T value : values) {
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

    /**
     * <div class="fr">Transforme une chaîne de caractères en son équivalent parmi le tableau de valeurs d'énumération
     * en paramètre.</div>
     *
     * @param <T>
     * @param candidate
     * @param values
     * @return
     */
    public static <T extends SemanticEnum<?>> T toEnum(final String candidate, final T[] values) {
        for (final T value : values) {
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
}
