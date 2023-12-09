package com.cosmoloj.language.common.impl.builder;

/**
 *
 * @author Samuel Andrés
 */
public abstract class StopCharSequenceLexemeBuilder extends CharSequenceLexemeBuilder {

    private final int stopCodePoint;

    public StopCharSequenceLexemeBuilder(final Object lexemeType, final int stopCodePoint) {
        super(lexemeType);
        this.stopCodePoint = stopCodePoint;
    }

    @Override
    public final boolean check(final int codePoint) {
        return codePoint != stopCodePoint;
    }
}
