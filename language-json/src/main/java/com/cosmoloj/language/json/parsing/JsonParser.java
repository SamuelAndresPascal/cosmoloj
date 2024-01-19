package com.cosmoloj.language.json.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveParser;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.lexeme.compound.ApproximateNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedIntegerBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteralBuilder;
import com.cosmoloj.language.common.number.parsing.NumberParser;
import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonArrayBuilder;
import com.cosmoloj.language.json.expression.JsonObject;
import com.cosmoloj.language.json.expression.JsonObjectBuilder;
import com.cosmoloj.language.json.expression.JsonValue;
import com.cosmoloj.language.json.lexeme.compound.JsonApproximateNumericLiteral;
import com.cosmoloj.language.json.lexeme.compound.JsonExactNumericLiteral;
import com.cosmoloj.language.json.lexeme.compound.JsonSignedInteger;
import com.cosmoloj.language.json.lexeme.compound.JsonSignedNumericLiteral;
import com.cosmoloj.language.json.lexeme.simple.Keyword;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import com.cosmoloj.language.json.lexeme.simple.SpecialSymbol;
import java.util.ArrayList;
import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;

/**
 *
 * @author Samuel Andrés
 */
public class JsonParser extends AbstractPredictiveMappingUnpredictiveParser<JsonLexer> {

    private final NumberParser<JsonLexer> numberParser;

    public JsonParser(final JsonLexer lexer) {
        super(lexer);
        this.numberParser = new NumberParser<>(lexer, '.', 'E', 'e') {

            @Override
            public SignedIntegerBuilder getSignedIntegerBuilder() {
                return JsonSignedInteger.builder();
            }

            @Override
            public ExactNumericLiteralBuilder getExactNumericLiteralBuilder() {
                return JsonExactNumericLiteral.builder();
            }

            @Override
            public ApproximateNumericLiteralBuilder getApproximateNumericLiteralBuilder() {
                return JsonApproximateNumericLiteral.builder();
            }

            @Override
            public SignedNumericLiteralBuilder getSignedNumericLiteralBuilder() {
                return JsonSignedNumericLiteral.builder();
            }
        };
    }

    public JsonObject object() throws LanguageException {
        return object(flushAndLex(SpecialSymbol.LEFT_OBJECT_DELIMITER));
    }

    public JsonObject object(final EnumLexeme<SpecialSymbol> leftDelimiter) throws LanguageException {

        final JsonObjectBuilder builder = new JsonObjectBuilder();

        builder.list(leftDelimiter);

        final Lexeme lexeme = flushAndLex();

        if (lexeme instanceof EnumLexeme) {
            builder.list(lexeme);
        } else if (lexeme instanceof QuotedString) {
            builder.list(
                    lexeme,
                    flushAndLex(SpecialSymbol.COLON),
                    value(flushAndLex()));

            EnumLexeme<SpecialSymbol> symbol = flushAndLexEnum(SpecialSymbol.class);
            while (SpecialSymbol.COMMA.test(symbol)) {
                builder.list(
                        symbol,
                        flushAndLex(QuotedString.class),
                        flushAndLex(SpecialSymbol.COLON),
                        value(flushAndLex()));
                symbol = flushAndLexEnum(SpecialSymbol.class);
            }
            builder.list(symbol);
        }
        return build(builder);
    }

    public JsonValue value(final Lexeme lexeme) throws LanguageException {
        if (lexeme instanceof QuotedString) {
            return (QuotedString) lexeme;
        } else if (lexeme instanceof EnumLexeme enumLex) {
            final Object sem = lexeme.getSemantics();
            if (sem instanceof SpecialSymbol symbol) {
                if (SpecialSymbol.LEFT_OBJECT_DELIMITER.equals(symbol)) {
                    return object(enumLex);
                } else if (SpecialSymbol.LEFT_ARRAY_DELIMITER.equals(symbol)) {
                    return array(enumLex);
                } else {
                    throw new IllegalStateException("expected left object/array delimiter");
                }
            } else if (sem instanceof Keyword) {
                return (JsonValue) enumLex;
            } else if (lexeme instanceof Sign.Lexeme) {
                return (JsonSignedNumericLiteral) numberParser.signedNumericLiteral(lexeme);
            } else {
                throw new IllegalStateException("expected left object/array delimiter or keyword");
            }
        } else {
            return (JsonSignedNumericLiteral) numberParser.signedNumericLiteral(lexeme);
        }
    }

    public JsonArray array() throws LanguageException {
        return array(flushAndLex(SpecialSymbol.LEFT_ARRAY_DELIMITER));
    }

    public JsonArray array(final EnumLexeme<SpecialSymbol> leftDelimiter) throws LanguageException {

        final JsonArrayBuilder builder = new JsonArrayBuilder();

        builder.list(leftDelimiter);

        final Lexeme lexeme = flushAndLex();

        if (SpecialSymbol.RIGHT_ARRAY_DELIMITER.test(lexeme)) {
            builder.list(lexeme);
        } else {
            builder.list(value(lexeme));

            EnumLexeme<SpecialSymbol> symbol = flushAndLexEnum(SpecialSymbol.class);
            while (SpecialSymbol.COMMA.test(symbol)) {
                builder.list(symbol,
                        value(flushAndLex()));
                symbol = flushAndLexEnum(SpecialSymbol.class);
            }
            builder.list(symbol);
        }
        return build(builder);
    }

    @Override
    public JsonValue parse() throws LanguageException {

        final EnumLexeme<SpecialSymbol> first = flushAndLexEnum(SpecialSymbol.class);

        return switch (first.getSemantics()) {
            case LEFT_OBJECT_DELIMITER -> object(first);
            case LEFT_ARRAY_DELIMITER -> array(first);
            default -> throw unexpected(first)
                    .semantics(SpecialSymbol.LEFT_OBJECT_DELIMITER, SpecialSymbol.LEFT_ARRAY_DELIMITER)
                    .exception();
        };
    }

    public static JsonParser of(final String body) {

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(body), new ArrayList<>());
        lexer.initialize();

        return new JsonParser(lexer);
    }

    public static JsonValue parse(final String json) throws LanguageException {

        return of(json).parse();
    }
}
