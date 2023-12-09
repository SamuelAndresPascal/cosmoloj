package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.builder.LexemeBuilder;

/**
 *
 * @author Samuel Andrés
 */
public abstract class CharSequenceLexemeBuilder implements LexemeBuilder {

    private final Object lexemeType;
    private final StringBuilder builder;

    public CharSequenceLexemeBuilder(final Object lexemeType) {
        this.lexemeType = lexemeType;
        builder = new StringBuilder();
    }

    @Override
    public final Object lexemeType() {
        return lexemeType;
    }

    @Override
    public final void reset() {
        builder.setLength(0);
        resetState();
    }

    protected abstract void resetState();

    public String codePoints() {
        return builder.toString();
    }

    @Override
    public final void add(final int codePoint) {
        builder.appendCodePoint(codePoint);
    }

    public boolean testToken(final int position, final int terminal) {
        return terminal == builder.codePointAt(position);
    }

    public boolean testToken(final int position, final int... terminals) {
        for (final int terminal : terminals) {
            if (testToken(position, terminal)) {
                return true;
            }
        }
        return false;
    }

    public int codePoint(final int i) {
        return builder.codePointAt(i);
    }

    public int size() {
        return builder.length();
    }

    public boolean isEmpty() {
        return builder.length() == 0;
    }
}
