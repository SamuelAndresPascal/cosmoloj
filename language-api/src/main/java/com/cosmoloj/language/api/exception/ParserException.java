package com.cosmoloj.language.api.exception;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ParserException extends LanguageException {

    private final Object context;
    private final List<Expectation<?>> expectations;
    private final int position;
    private final Context type;

    public enum Context {
        TOKEN, CODE_POINT, LEXER_ERROR
    }

    public ParserException(final Object context, final int position, final Context type,
            final List<Expectation<?>> expectations) {
        this.context = context;
        this.position = position;
        this.type = type;
        this.expectations = Collections.unmodifiableList(expectations);
    }

    public Object getToken() {
        return context;
    }

    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder("unexpected ");
        switch (type) {
            case TOKEN -> sb.append("token ").append(this.context);
            case CODE_POINT -> sb.append("code point ").append(this.context);
            case LEXER_ERROR -> sb.append("lexer error \"").append(this.context).append('"');
            default -> {
            }
        }
        sb.append(" at ").append(this.position).append(", but expected:").append(System.lineSeparator());
        for (final Expectation<?> ex : expectations) {
            sb.append(ex.alternativesToString("|")).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
