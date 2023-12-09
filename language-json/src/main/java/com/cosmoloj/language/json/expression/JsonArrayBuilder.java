package com.cosmoloj.language.json.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
import com.cosmoloj.language.json.lexeme.simple.SpecialSymbol;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class JsonArrayBuilder extends CheckTokenBuilder<Token, JsonArray>
        implements ConstraintBeforeIndexPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> SpecialSymbol.LEFT_ARRAY_DELIMITER;
            case 1 -> SpecialSymbol.RIGHT_OBJECT_DELIMITER.or(JsonValue.JSON_VALUE);
            default -> even() ? SpecialSymbol.COMMA.or(SpecialSymbol.RIGHT_ARRAY_DELIMITER) : JsonValue.JSON_VALUE;
        };
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {

        if (index > 1 && before == 1) {
            if (odd() && current(JsonValue.JSON_VALUE)) {
                return SpecialSymbol.COMMA;
            } else if (even() && current(SpecialSymbol.COMMA)) {
                return JsonValue.JSON_VALUE;
            } else {
                return t -> false;
            }
        }
        return t -> true;
    }

    @Override
    public JsonArray build() {
        return new JsonArray(first(), last(), index(), tokens(JsonValue.JSON_VALUE));
    }
}
