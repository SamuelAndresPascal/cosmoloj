package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class LocalDatum extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof LocalDatum;

    private final QuotedName name;
    private final SignedNumericLiteral datumType;
    private final Authority authority;

    public LocalDatum(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral datumType, final Authority authority) {
        super(start, end, index);
        this.name = name;
        this.datumType = datumType;
        this.authority = authority;
    }

    public QuotedName getName() {
        return this.name;
    }

    public SignedNumericLiteral getDatumType() {
        return this.datumType;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
