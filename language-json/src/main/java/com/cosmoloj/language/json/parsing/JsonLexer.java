package com.cosmoloj.language.json.parsing;

import com.cosmoloj.language.api.parsing.Scanner;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveLexer;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.lexeme.simple.DecimalSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.ExponentSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.json.lexeme.simple.JsonUnsignedInteger;
import com.cosmoloj.language.json.lexeme.simple.Keyword;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import com.cosmoloj.language.json.lexeme.simple.SpecialSymbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;

/**
 *
 * @author Samuel Andrés
 */
public class JsonLexer extends AbstractPredictiveMappingUnpredictiveLexer {

    private static final IntFunction<Object> UNPREDICTED_TABLE = cp -> {
        return switch (cp) {
            case '"' -> QuotedString.class;
            case ',', ':', '{', '}', '[', ']' -> SpecialSymbol.class;
            case '+', '-' ->  Sign.class;
            default -> {
                if (Character.isAlphabetic(cp)) {
                    yield Keyword.class;
                } else if (Character.isDigit(cp)) {
                    yield UnsignedInteger.class;
                }
                throw new IllegalStateException("unexpected code point");
            }
        };
    };

    public JsonLexer(final Scanner scanner, final List<Token> indexes) {
        super(scanner, indexes, UNPREDICTED_TABLE, Set.of(
                Keyword.builder(),
                QuotedString.builder(),
                JsonUnsignedInteger.builder(),
                Sign.builder(),
                SpecialSymbol.builder(),
                DecimalSeparator.builder('.'),
                ExponentSeparator.builder('E', 'e')));
    }

    public static JsonLexer initialize(final String text) {
        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        return lexer;
    }
}
