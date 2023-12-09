package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.UnpredictiveLexer;
import java.util.function.Function;
import com.cosmoloj.language.api.semantic.Lexeme;


/**
 *
 * @author Samuel Andrés
 * @param <L>
 */
public abstract class AbstractPredictiveMappingUnpredictiveParser<L extends AbstractPredictiveLexer & UnpredictiveLexer>
        extends AbstractPredictiveParser<L> {

    public AbstractPredictiveMappingUnpredictiveParser(final L lexer) {
        super(lexer);
    }

    public <T extends Lexeme> T lex() throws LanguageException {
        getLexer().lex();
        return (T) getLexer().lexeme();
    }

    public <T extends Lexeme> T flushAndLex() throws LanguageException {
        getLexer().flush();
        getLexer().lex();
        return (T) getLexer().lexeme();
    }

    public <T extends Lexeme> T flushAndLex(final Function<Lexeme, T> map)
            throws LanguageException {
        getLexer().flush();
        getLexer().lex();

        final Lexeme lex = map.apply(getLexer().lexeme());

        if (lex == getLexer().lexeme()) {
            return (T) getLexer().lexeme();
        } else {
            getIndexes().remove(getLexer().lexeme());
            getIndexes().add(lex);
            return (T) lex;
        }
    }
}
