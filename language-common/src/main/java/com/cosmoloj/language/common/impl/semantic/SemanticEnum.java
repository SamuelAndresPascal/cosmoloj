package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import java.util.function.Predicate;

/**
 * <div class="fr">Sémantique de lexème ayant la forme d'une énumération.</div>
 *
 * @author Samuel Andrés
 * @param <T> <span class="fr">type de l'énumération</span>
 * @see EnumLexemeBuilder
 */
public interface SemanticEnum<T extends Enum<T>> extends Predicate<Object> {

    String name();

    /**
     * Default, use enum name as code points.
     * @return
     */
    default String getCodePoints() {
        return name();
    }

    default int length() {
        return getCodePoints().length();
    }

    default int codePointAt(final int index) {
        return getCodePoints().codePointAt(index);
    }

    default boolean testSemantics(final Object s) {
        return this.equals(s);
    }

    @Override
    default boolean test(final Object lex) {
        return lex instanceof Lexeme l && testSemantics(l.getSemantics());
    }

    static String loadCodePoints(final int... codePoints) {
        final StringBuilder sb = new StringBuilder();
        for (final int codePoint : codePoints) {
            sb.appendCodePoint(codePoint);
        }
        return sb.toString();
    }
}
