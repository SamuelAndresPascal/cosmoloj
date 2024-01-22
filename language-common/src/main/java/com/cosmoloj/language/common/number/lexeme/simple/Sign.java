package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum Sign implements SemanticEnum<Sign> {

    PLUS('+'),
    MINUS('-');

    private final String codePoints;

    Sign(final int... codePoints) {
        final StringBuilder sb = new StringBuilder();
        for (final int codePoint : codePoints) {
            sb.appendCodePoint(codePoint);
        }
        this.codePoints = sb.toString();
    }

    @Override
    public String getCodePoints() {
        return codePoints;
    }

    public static EnumCase casePolicy() {
        return EnumCase.SENTITIVE;
    }

    public static boolean exists(final String codePoints) {
        return casePolicy().parse(codePoints, Sign.values()) != null;
    }

    public static EnumLexeme<Sign> map(final Lexeme lex) {
        return casePolicy().map(Sign.values(), lex);
    }

    public static EnumLexemeBuilder<Sign> builder() {
        return casePolicy().builder(Sign.class, Sign.values());
    }
}
