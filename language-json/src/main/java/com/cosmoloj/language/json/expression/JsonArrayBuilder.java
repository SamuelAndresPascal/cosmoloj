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
            case 1 -> SpecialSymbol.RIGHT_OBJECT_DELIMITER.or(JsonValue.class::isInstance);
            default -> even()
                ? SpecialSymbol.COMMA.or(SpecialSymbol.RIGHT_ARRAY_DELIMITER)
                : JsonValue.class::isInstance;
        };
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {

        if (index > 1 && before == 1) {
            if (odd() && waiting(JsonValue.class)) {
                return SpecialSymbol.COMMA;
            } else if (even() && waiting(SpecialSymbol.COMMA)) {
                return JsonValue.class::isInstance;
            } else {
                return t -> false;
            }
        }
        return t -> true;
    }

    @Override
    public JsonArray build() {
        return new JsonArray(first(), last(), index(), tokens(JsonValue.class::isInstance));
    }
}
