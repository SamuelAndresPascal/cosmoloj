package com.cosmoloj.language.common;

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
}
