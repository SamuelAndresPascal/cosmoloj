package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author Samuel Andrés
 * @param <E> <span class="fr">énumération des suites de caractères (mots réservés…)</span>
 */
public abstract class EnumLexemeBuilder<E extends Enum<E> & SemanticEnum<E>> extends CharSequenceLexemeBuilder {

    private final E[] values;
    private final Case casePolicy;
    private final Locale locale;

    public enum Case {
        SENTITIVE, IGNORE, LOWER
    }

    protected EnumLexemeBuilder(final Object lexemeType, final E[] values) {
        this(lexemeType, values, Case.SENTITIVE, Locale.ROOT);
    }

    protected EnumLexemeBuilder(final Object lexemeType, final E[] values, final Case casePolicy, final Locale loc) {
        super(lexemeType);
        this.values = values;
        this.casePolicy = casePolicy;
        this.locale = loc;
    }

    /**
     * <div class="fr">Méthode de vérification d'acceptation du point de code en respectant la casse.</div>
     *
     * @param codePoint <span class="fr">point de code</span>
     * @return
     */
    @Override
    public boolean check(final int codePoint) {

        switch (casePolicy) {
            case IGNORE -> {
                final String codePointsUc = codePoints().toUpperCase(this.locale);
                for (final E value : this.values) {
                    if (value.getCodePoints().toUpperCase(this.locale).startsWith(codePointsUc)
                            && value.length() > codePointsUc.length()
                            && LanguageUtil.equalCharacterIgnoreCase(
                                    value.codePointAt(codePointsUc.length()), codePoint)) {
                        return true;
                    }
                }
            }
            case LOWER -> {
                final String codePoints = codePoints();
                for (final E value : this.values) {
                    if (value.getCodePoints().toLowerCase(locale).startsWith(codePoints)
                            && value.length() > codePoints.length()
                            && Character.toLowerCase(value.codePointAt(codePoints.length())) == codePoint) {
                        return true;
                    }
                }
            }
            default -> { // case sensitive
                final String codePoints = codePoints();
                for (final E value : this.values) {
                    /*
                    On vérifie successivement, pour chaque valeur de l'énumération :
                    1- que sa représentation en points de code commence de la même manière que la chaîne de points de
                    code en cours
                    2- que sa longueur est encore supérieure à la chaîne de points de code en cours de construction
                    3- que le caractère suivant correspond au nouveau point de code soumis à vérification
                    Dès qu'on trouve une valeur de l'énumération qui satisfait à ces conditions, le point de code est
                    accepté.
                    */
                    if (value.getCodePoints().startsWith(codePoints)
                            && value.length() > codePoints.length()
                            && value.codePointAt(codePoints.length()) == codePoint) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int[] expectations() {
        final Set<Integer> candidates = new HashSet<>();
        final String codePoints = codePoints();
        switch (casePolicy) {
            case SENTITIVE -> {
                for (final E value : values) {
                    if (value.getCodePoints().equals(codePoints)) {
                        return null;
                    } else if (value.getCodePoints().startsWith(codePoints)
                            && value.length() > codePoints.length()) {
                        candidates.add(value.getCodePoints().codePointAt(codePoints.length()));
                    }
                }
            }
            case IGNORE -> {
                for (final E value : values) {
                    if (value.getCodePoints().equalsIgnoreCase(codePoints)) {
                        return null;
                    } else if (value.getCodePoints().toUpperCase(locale).startsWith(codePoints.toUpperCase(locale))
                            && value.length() > codePoints.length()) {
                        final int c = value.getCodePoints().codePointAt(codePoints.length());
                        candidates.add(c);
                        if (Character.isLowerCase(c)) {
                            candidates.add(Character.toUpperCase(c));
                        } else if (Character.isUpperCase(c)) {
                            candidates.add(Character.toLowerCase(c));
                        }
                    }
                }
            }
            case LOWER -> {
                for (final E value : values) {
                    if (value.getCodePoints().toLowerCase(locale).equals(codePoints)) {
                        return null;
                    } else if (value.getCodePoints().toLowerCase(locale).startsWith(codePoints)
                            && value.length() > codePoints.length()) {
                        candidates.add(value.getCodePoints().toLowerCase(locale).codePointAt(codePoints.length()));
                    }
                }
            }
            default -> {
            }
        }

        if (!candidates.isEmpty()) {
            return candidates.stream().mapToInt(c -> c).toArray();
        }

        return null;
    }

    @Override
    protected final void resetState() {
    }
}
