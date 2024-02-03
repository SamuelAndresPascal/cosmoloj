package com.cosmoloj.language.json.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import com.cosmoloj.language.json.lexeme.simple.SpecialSymbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class JsonObjectBuilder extends CheckTokenBuilder<Token, JsonObject>
        implements ConstraintBeforeIndexPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> SpecialSymbol.LEFT_OBJECT_DELIMITER;
            case 1 -> SpecialSymbol.RIGHT_OBJECT_DELIMITER.or(QuotedString.class::isInstance);
            default -> even()
                ? SpecialSymbol.COLON.or(SpecialSymbol.COMMA).or(SpecialSymbol.RIGHT_OBJECT_DELIMITER)
                : pb(QuotedString.class).or(JsonValue.class);
        };
    }

    @Override
    public JsonObject build() {
        final List<Map.Entry<QuotedString, JsonValue>> entries = new ArrayList<>();

        QuotedString currentKey = null;
        for (final Token t : tokens()) {
            if (t instanceof QuotedString string && currentKey == null) {
                currentKey = string;
            } else if (t instanceof JsonValue json && currentKey != null) {
                entries.add(Map.entry(currentKey, json));
                currentKey = null;
            }
        }

        return new JsonObject(first(), last(), index(), entries);
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        if (index > 1 && before == 1) {
            if (even()) {
                if (waiting(SpecialSymbol.COLON)) {
                    return QuotedString.class::isInstance;
                } else if (waiting(SpecialSymbol.COMMA.or(SpecialSymbol.RIGHT_OBJECT_DELIMITER))) {
                    return JsonValue.class::isInstance;
                } else {
                    return t -> false;
                }
            } else {
                if (waiting(pb(JsonValue.class).and(pb(QuotedString.class).negate()))) {
                    return SpecialSymbol.COLON;
                } else if (waiting(QuotedString.class)) {
                    return SpecialSymbol.COLON.or(SpecialSymbol.COMMA);
                }
            }
        }
        return t -> true;
    }
}
