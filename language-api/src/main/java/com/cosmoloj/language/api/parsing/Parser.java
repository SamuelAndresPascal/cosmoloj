package com.cosmoloj.language.api.parsing;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <L>
 */
public interface Parser<L extends Lexer> {

    L getLexer();

    Token parse() throws LanguageException;

    default void flush() throws LanguageException {
        getLexer().flush();
    }

    default int flushTo() throws LanguageException {
        return getLexer().flushTo();
    }

    default List<Token> getIndexes() {
        return getLexer().getIndexes();
    }

    default int codePoint() {
        return getLexer().codePoint();
    }

    default <T extends Token> T index(final T token) {
        getIndexes().add(token);
        return token;
    }

    default <T extends Token> T build(final TokenBuilder<?, T> builder) {
        return index(builder.build());
    }
}
