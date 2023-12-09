package com.cosmoloj.language.common.impl.exception;

import com.cosmoloj.language.api.exception.Expectation;
import com.cosmoloj.language.api.exception.ParserException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.LexerCode;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class ParserExceptionBuilder {

    private final Object ctx;
    private final ParserException.Context type;
    private final int position;
    private final List<Expectation<?>> expectations = new ArrayList<>();

    public ParserExceptionBuilder(final Token unexpectedToken, final int position) {
        // ici, c'est forcément une erreur du parseur et non du lexer
        this.ctx = unexpectedToken;
        this.type = ParserException.Context.TOKEN;
        this.position = position;
    }

    public ParserExceptionBuilder(final int codePoint, final int position) {
        this.position = position;
        if (Character.isValidCodePoint(codePoint)) {
            type = ParserException.Context.CODE_POINT;
            ctx = String.copyValueOf(Character.toChars(codePoint));
        } else {
            type = ParserException.Context.LEXER_ERROR;
            ctx = LexerCode.message(codePoint);
        }
    }

    public ParserExceptionBuilder terminals(final int... alternatives) {
        this.expectations.add(new TerminalExpectation(alternatives));
        return this;
    }

    @SuppressWarnings("unchecked")
    public ParserExceptionBuilder types(final Class<? extends Token>... alternatives) {
        this.expectations.add(new TokenExpectation(List.of(alternatives)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T> & SemanticEnum<T>> ParserExceptionBuilder semantics(final T... alternatives) {
        this.expectations.add(new WordExpectation<>(List.of(alternatives)));
        return this;
    }

    public <T extends Enum<T> & SemanticEnum<T>> ParserExceptionBuilder semantics(final Collection<T> alternatives) {
        this.expectations.add(new WordExpectation<>(alternatives));
        return this;
    }

    public ParserException exception() {
        return new ParserException(ctx, position, type, expectations);
    }

    private abstract static class AbstractExpectation<A> implements Expectation<A> {

        private final Collection<A> alternatives;

        AbstractExpectation(final Collection<A> alternatives) {
            this.alternatives = alternatives;
        }

        @Override
        public Collection<A> getAlternatives() {
            return alternatives;
        }

        @Override
        public String alternativeToString(final A alternative) {
            return alternative.toString();
        }
    }

    private static class WordExpectation<T extends Enum<T> & SemanticEnum<T>> extends AbstractExpectation<T> {

        WordExpectation(final Collection<T> alternatives) {
            super(alternatives);
        }
    }

    private static class TerminalExpectation extends AbstractExpectation<Integer> {

        TerminalExpectation(final int... alternatives) {
            super(IntStream.of(alternatives).boxed().toList());
        }

        @Override
        public String alternativeToString(final Integer alternative) {
            return new StringBuilder().appendCodePoint(alternative).toString();
        }
    }

    private static class TokenExpectation extends AbstractExpectation<Class<? extends Token>> {

        TokenExpectation(final Collection<Class<? extends Token>> alternatives) {
            super(alternatives);
        }

        @Override
        public String alternativeToString(final Class<? extends Token> alternative) {
            if (EnumLexeme.class.isAssignableFrom(alternative)) {
                    final Class<?> declaringClass = alternative.getDeclaringClass();
                    if (Enum.class.isAssignableFrom(declaringClass)) {
                        return alternative.getDeclaringClass().getSimpleName() + " : "
                                + printEnumConstants(((Class<Enum>) declaringClass).getEnumConstants());
                    } else {
                        return alternative.getSimpleName();
                    }
                } else {
                    return alternative.getSimpleName();
                }
        }

        private static String printEnumConstants(final Enum[] enumConstants) {

            final StringBuilder sb = new StringBuilder().append('[');
            int i = 0;
            final int n = enumConstants.length - 1;
            for (final Enum enumConstant : enumConstants) {
                if (enumConstant instanceof SemanticEnum<?>) {
                    sb.append(((SemanticEnum<?>) enumConstant).getCodePoints());
                } else {
                    sb.append(String.valueOf(enumConstant));
                }

                if (i < n) {
                    sb.append(", ");
                }
                i++;
            }
            return sb.append(']').toString();
        }
    }
}
