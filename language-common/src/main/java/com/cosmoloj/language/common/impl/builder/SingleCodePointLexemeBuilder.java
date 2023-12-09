package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.builder.LexemeBuilder;
import com.cosmoloj.language.api.semantic.Lexeme;
import java.util.Arrays;

/**
 * <div class="fr">Construit un jeton composé d'un unique point de code.</div>
 *
 * @author Samuel Andrés
 * @param <L>
 */
public abstract class SingleCodePointLexemeBuilder<L extends Lexeme> implements LexemeBuilder {

    private final int[] characterReferences;
    private final Class<L> lexemeType;

    // L'initialisation du point de code à {@link Integer#MIN_VALUE} indique que le point de code est disponible.
    private int codePoint = Integer.MIN_VALUE;

    public SingleCodePointLexemeBuilder(final int characterReference, final Class<L> lexemeType) {
        if (!Character.isValidCodePoint(characterReference)) {
            throw new IllegalArgumentException();
        }
        this.lexemeType = lexemeType;
        this.characterReferences = new int[]{characterReference};
    }

    public SingleCodePointLexemeBuilder(final int[] characterReferences, final Class<L> lexemeType) {
        this.lexemeType = lexemeType;
        this.characterReferences = characterReferences;
        Arrays.sort(this.characterReferences);
    }

    @Override
    public final Object lexemeType() {
        return lexemeType;
    }

    public int getCodePoint() {
        return this.codePoint;
    }

    @Override
    public final boolean check(final int character) {
        // on accepte si et seulement si le point de code est disponible
        return this.codePoint == Integer.MIN_VALUE && Arrays.binarySearch(this.characterReferences, character) >= 0;
    }

    @Override
    public final void add(final int character) {
        this.codePoint = character;
    }

    @Override
    public final int[] expectations() {
        if (this.codePoint == Integer.MIN_VALUE) {
            return Arrays.copyOf(characterReferences, characterReferences.length);
        }
        return null;
    }

    @Override
    public final void reset() {
        // la réinitialisation consiste à rendre le point de code disponible
        this.codePoint = Integer.MIN_VALUE;
    }
}
