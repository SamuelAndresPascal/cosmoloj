package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.builder.LexemeBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.Scanner;
import com.cosmoloj.language.api.parsing.UnpredictiveLexer;
import com.cosmoloj.language.api.semantic.Token;
import java.util.List;
import java.util.function.IntFunction;

/**
 * <div class="fr">Lexer non prédictif implémenté à partir d'un lexer prédictif. La capacité du lexer à déterminer
 * le type de lexème à lire est contrôlé par une méthode permettant de déterminer le type de lexème à lire.</div>
 *
 * @author Samuel Andrés
 */
public abstract class AbstractPredictiveMappingUnpredictiveLexer extends AbstractPredictiveLexer
        implements UnpredictiveLexer {

    private final IntFunction<Object> unpredictedLexemeTable;

    public AbstractPredictiveMappingUnpredictiveLexer(final Scanner scanner, final List<Token> indexes,
            final IntFunction<Object> unpredictedLexemeTable,
            final Iterable<LexemeBuilder> builders) {
        super(scanner, indexes, builders);
        this.unpredictedLexemeTable = unpredictedLexemeTable;
    }

    @Override
    public void lex() throws LanguageException {
        lex(this.unpredictedLexemeTable.apply(codePoint()));
    }
}
