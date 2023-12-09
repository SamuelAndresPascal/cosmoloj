package com.cosmoloj.language.json.mapping;

import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonObject;
import com.cosmoloj.language.json.expression.JsonValue;
import com.cosmoloj.language.json.lexeme.compound.JsonSignedNumericLiteral;
import com.cosmoloj.language.json.lexeme.simple.Keyword;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 *
 * @author Samuel Andrés
 */
public final class JsonMapping {

    private JsonMapping() {
    }

    public static final Function<JsonValue, Boolean> KEYWORD = e -> {
        if (e instanceof Keyword.Lexeme l) {
            return switch (l.getSemantics()) {
                case TRUE -> Boolean.TRUE;
                case FALSE -> Boolean.FALSE;
                default -> null;
            };
        } else if (e instanceof JsonSignedNumericLiteral nl && !nl.isDecimal()) {
            return switch (nl.getSemantics().intValue()) {
                case 1 -> Boolean.TRUE;
                case 0 -> Boolean.FALSE;
                default -> null;
            };
        }
        throw new IllegalStateException("unexpected value for json boolean mapping: " + e);
    };

    public static final Function<JsonValue, String> QUOTED_STRING = e -> {
        if (e instanceof QuotedString qs) {
            return qs.getSemantics();
        } else if (Keyword.NULL.test(e)) {
            return null;
        }
        throw new IllegalStateException("unexpected value for json quoted string mapping: " + e);
    };

    public static final Function<JsonValue, Number> SIGNED_NUMERIC_LITERAL = e -> {
        if (e instanceof JsonSignedNumericLiteral snl) {
            return snl.getSemantics();
        } else if (Keyword.NULL.test(e)) {
            return null;
        } else if (e instanceof QuotedString qs && "NaN".equals(qs.getSemantics())) {
            return Double.NaN;
        }
        throw new IllegalStateException("unexpected value for json number mapping: " + e);
    };

    public static final Function<JsonValue, JsonObject> AS_JSON_OBJECT = e -> {
        if (e instanceof JsonObject o) {
            return o;
        } else if (Keyword.NULL.test(e)) {
            return null;
        }
        throw new IllegalStateException("unexpected value for json object mapping: " + e);
    };

    public static final Function<JsonValue, JsonArray> AS_JSON_ARRAY = e -> {
        if (e instanceof JsonArray a) {
            return a;
        } else if (Keyword.NULL.test(e)) {
            return null;
        }
        throw new IllegalStateException("unexpected value for json array mapping: " + e);
    };

    public static <T> T field(final JsonObject object, final String field, final Function<JsonValue, T> mapper) {
        return object == null ? null : object.values(field).findFirst().map(mapper).orElse(null);
    }

    public static Boolean bool(final JsonObject object, final String field) {
        return field(object, field, KEYWORD);
    }

    public static String string(final JsonObject object, final String field) {
        return field(object, field, QUOTED_STRING);
    }

    public static Integer integer(final JsonObject object, final String field) {
        return number(object, field, Integer.class::cast);
    }

    public static Number number(final JsonObject object, final String field) {
        return field(object, field, SIGNED_NUMERIC_LITERAL);
    }

    public static <T extends Number> T number(final JsonObject object, final String field,
            final Function<Number, T> mapper) {
        return field(object, field, SIGNED_NUMERIC_LITERAL.andThen(mapper));
    }

    public static <T> T[] array(final JsonObject object, final String field, final Function<JsonObject, T> mapper,
            final IntFunction<T[]> arrayMapper) {

        final JsonArray f = field(object, field, AS_JSON_ARRAY);

        if (f == null) {
            return null;
        }

        return (T[]) f.getList()
                .stream()
                .map(AS_JSON_OBJECT.andThen(mapper))
                .toArray(arrayMapper);
    }

    public static String[] stringArray(final JsonObject object, final String field) {

        final JsonArray f = field(object, field, AS_JSON_ARRAY);

        if (f == null) {
            return null;
        }

        return (String[]) f.getList()
                .stream()
                .map(s -> ((QuotedString) s).getSemantics())
                .toArray(String[]::new);
    }

}
