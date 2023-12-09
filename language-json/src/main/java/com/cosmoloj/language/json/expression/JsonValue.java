package com.cosmoloj.language.json.expression;

import com.cosmoloj.language.api.semantic.Token;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public interface JsonValue extends Token {

    Predicate<Object> JSON_VALUE = JsonValue.class::isInstance;
}
