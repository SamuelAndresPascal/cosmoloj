package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ParameterAbridged extends AbstractParam {

    public static final Predicate<Object> PARAMETER_ABRIDGED = t -> t instanceof ParameterAbridged;

    private final SignedNumericLiteral value;

    public ParameterAbridged(final int start, final int end, final int index, final QuotedLatinText name,
            final SignedNumericLiteral value, final List<Identifier> identifiers) {
        super(start, end, index, name, identifiers);
        this.value = value;
    }

    public SignedNumericLiteral getValue() {
        return value;
    }
}
