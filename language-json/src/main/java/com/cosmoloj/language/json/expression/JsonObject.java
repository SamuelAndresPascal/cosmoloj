package com.cosmoloj.language.json.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Samuel Andrés
 */
public class JsonObject extends AbstractExpression implements JsonValue {

    private final List<Map.Entry<QuotedString, JsonValue>> entries;

    public JsonObject(final int first, final int last, final int index,
            final List<Map.Entry<QuotedString, JsonValue>> entries) {
        super(first, last, index);
        this.entries = entries;
    }

    public List<Map.Entry<QuotedString, JsonValue>> getEntries() {
        return entries;
    }

    public Stream<JsonValue> values(final String key) {
        return entries.stream()
                .filter(e -> key.equals(e.getKey().getSemantics()))
                .map(e -> e.getValue());
    }

    public JsonValue firstOrNull(final String key) {
        return values(key)
                .findFirst()
                .orElse(null);
    }
}
