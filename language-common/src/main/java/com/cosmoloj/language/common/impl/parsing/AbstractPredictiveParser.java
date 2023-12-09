package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.PredictiveLexer;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.exception.ParserExceptionBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.language.api.semantic.Lexeme;
import java.util.Objects;

/**
 *
 * @author Samuel Andrés
 * @param <L>
 */
public abstract class AbstractPredictiveParser<L extends AbstractPredictiveLexer & PredictiveLexer>
        extends AbstractParser<L> {

    public AbstractPredictiveParser(final L lexer) {
        super(lexer);
    }

    //@SuppressWarnings("unchecked")
    public <T extends Enum<T> & SemanticEnum<T>, L extends EnumLexeme<T>> L lexEnum(final Class<T> lexemeType)
            throws LanguageException {
        getLexer().lex(lexemeType);
        try {
            return (L) getLexer().lexeme();
        } catch (final ClassCastException ex) {
            throw unexpected(getLexer().lexeme()).semantics().exception();
        }
    }

    public <T extends Enum<T> & SemanticEnum<T>, L extends EnumLexeme<T>> L flushAndLexEnum(
            final Class<T> lexemeType) throws LanguageException {
        getLexer().flush();
        return lexEnum(lexemeType);
    }

    public <T extends Enum<T> & SemanticEnum<T>, L extends EnumLexeme<T>> L lex(final T... semantics)
            throws LanguageException {
        Objects.requireNonNull(semantics, "semantics must not be null");
        getLexer().lex(semantics[0].getClass());
        final L lexeme = (L) getLexer().lexeme();

        for (final T s : semantics) {
            if (s.equals(lexeme.getSemantics())) {
                return lexeme;
            }
        }

        throw unexpected(lexeme).semantics(semantics).exception();
    }

    public <T extends Enum<T> & SemanticEnum<T>, L extends EnumLexeme<T>> L flushAndLex(final T... semantics)
            throws LanguageException {
        getLexer().flush();
        return lex(semantics);
    }

    public <T extends Lexeme> T lex(final Class<T> lexemeType) throws LanguageException {
        getLexer().lex(lexemeType);
        try {
            return (T) getLexer().lexeme();
        } catch (final ClassCastException ex) {
            throw unexpected(getLexer().lexeme()).types(lexemeType).exception();
        }
    }

    public <T extends Lexeme> T flushAndLex(final Class<T> lexemeType) throws LanguageException {
        getLexer().flush();
        return lex(lexemeType);
    }

    protected ParserExceptionBuilder unexpected(final Token token) {
        return this.getLexer().unexpected(token);
    }
}
