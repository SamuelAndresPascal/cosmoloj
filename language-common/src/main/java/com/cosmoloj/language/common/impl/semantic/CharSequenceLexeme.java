package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <span class="fr">Lexème formé d'une chaîne de caractères.</span>
 *
 * @author Samuel Andrés
 *
 */
public abstract class CharSequenceLexeme implements Lexeme {

    private final String codePoints;

    private final int first;
    private final int last;
    private final int order;

    public CharSequenceLexeme(final String codePoints, final int first, final int last, final int order) {
        this.codePoints = codePoints;
        this.first = first;
        this.last = last;
        this.order = order;
    }

    public CharSequenceLexeme(final Lexeme toMap) {
        this.codePoints = toMap.getCodePoints();
        this.first = toMap.first();
        this.last = toMap.last();
        this.order = toMap.order();
    }

    @Override
    public String getCodePoints() {
        return codePoints;
    }

    @Override
    public int first() {
        return first;
    }

    @Override
    public int last() {
        return last;
    }

    @Override
    public int order() {
        return order;
    }

    @Override
    public String toString() {
        return '{' + "codePoints=" + codePoints + '}';
    }
}
