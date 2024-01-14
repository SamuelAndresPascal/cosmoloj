package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;

/**
 * <div class="fr">Sémantique de lexème ayant la forme d'une énumération.</div>
 *
 * @author Samuel Andrés
 * @param <T> <span class="fr">type de l'énumération</span>
 * @see EnumLexemeBuilder
 */
public interface SemanticEnum<T extends Enum<T>> {

    String name();

    default String getCodePoints() {
        return name();
    }

    default int length() {
        return name().length();
    }

    default int codePointAt(final int index) {
        return name().codePointAt(index);
    }
}