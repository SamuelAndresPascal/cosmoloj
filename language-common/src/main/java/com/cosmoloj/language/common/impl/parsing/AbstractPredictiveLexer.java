package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.builder.LexemeBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.PredictiveLexer;
import com.cosmoloj.language.api.parsing.Scanner;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.exception.ParserExceptionBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cosmoloj.language.api.semantic.Lexeme;
import java.util.function.UnaryOperator;


/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractPredictiveLexer implements PredictiveLexer {

    private final List<Token> indexes;
    private final Scanner scanner;

    // mutable fields
    private int codePoint;
    private Lexeme lexeme;
    private int start;
    private int end;

    private final Map<Object, LexemeBuilder> builders = new HashMap<>();

    public AbstractPredictiveLexer(final Scanner scanner, final List<Token> indexes,
            final Iterable<LexemeBuilder> builders) {
        this.scanner = scanner;
        this.codePoint = LexerCode.NOT_YET_STARTED;
        this.indexes = indexes;

        for (final LexemeBuilder builder : builders) {
            if (!this.builders.containsKey(builder.lexemeType())) {
                this.builders.put(builder.lexemeType(), builder);
            } else {
                throw new IllegalStateException(
                        String.format("builder lexeme type %s is already defined", builder.lexemeType()));
            }
        }
    }

    protected void mapLexeme(final UnaryOperator<Lexeme> mapper) {
        getIndexes().remove(lexeme());
        this.lexeme = mapper.apply(lexeme());
        getIndexes().add(lexeme());
    }

    protected void nullLexeme() {
        this.lexeme = null;
    }

    /**
     * <div class="fr">Initialisation de la valeur du point de code courant à la première valeur du scanner.</div>
     */
    public void initialize() {
        nextCodePoint();
    }

    @Override
    public List<Token> getIndexes() {
        return this.indexes;
    }

    @Override
    public int codePoint() {
        return this.codePoint;
    }

    @Override
    public boolean end() {
        return this.codePoint == LexerCode.NO_MORE_CODE_POINT;
    }

    @Override
    public Lexeme lexeme() {
        return this.lexeme;
    }

    @Override
    public void lex(final Object lexemeType) throws LanguageException {

        final LexemeBuilder builder = builders.get(lexemeType);

        /*
        les indices de fin et de départ du lexème sont initialisés à la position du curseur (qui est déjà après le
        premier caractère du lexème)
        */
        this.start = this.scanner.cursor();
        this.end = Integer.MIN_VALUE;

        // on compose le lexème tant que les points de code sont valides et que le constructeur les accepte
        while (Character.isValidCodePoint(codePoint)
                && builder.check(this.codePoint)) {
            builder.add(this.codePoint);
            this.end = this.scanner.cursor();
            nextCodePoint();
        }

        final int[] expectations = builder.expectations();

        if (expectations != null) {
           throw unexpectedCodePoint().terminals(expectations).exception();
        } else if (end < 0) {
            // si aucun tour de boucle n'est réalisé, le lexème construit est vide ce qui n'a pas de sens
           throw unexpectedCodePoint().exception();
        }

        this.start--; // on recale la position de départ avant le premier caractère du lexème
        this.end--; // on recale la position d'arrivée avant le caractère du scanner qui n'a pas été accepté

        // on constuit le lexème
        this.lexeme = builder.build(this.start, this.end, this.indexes.size());

        // on ajoute le lexème à la liste
        this.indexes.add(this.lexeme);
        builder.reset();
    }

    /**
     * <div class="fr">Passe au point de code suivant.</div>
     */
    protected void nextCodePoint() {
        if (this.scanner.hasNext()) {
            this.codePoint = this.scanner.nextInt();
        } else {
            this.codePoint = LexerCode.NO_MORE_CODE_POINT;
        }
    }

    @Override
    public void flush() {
        loop: while (true) {
            switch (codePoint()) {
                case ' ', '\n', '\r', '\t' -> nextCodePoint();
                default -> {
                    break loop;
                }
            }
        }
    }

    protected ParserExceptionBuilder unexpectedCodePoint() {
        return new ParserExceptionBuilder(codePoint(), this.scanner.cursor());
    }

    public ParserExceptionBuilder unexpected(final Token token) {
        return new ParserExceptionBuilder(token, this.scanner.cursor());
    }
}
