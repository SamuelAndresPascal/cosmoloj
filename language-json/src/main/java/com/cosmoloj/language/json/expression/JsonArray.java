package com.cosmoloj.language.json.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public class JsonArray<T extends JsonValue> extends AbstractExpression implements JsonValue {

    private final List<T> list;

    public JsonArray(final int first, final int last, final int index, final List<T> list) {
        super(first, last, index);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }
}
