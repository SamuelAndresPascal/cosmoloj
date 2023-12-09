package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class PrimeMeridian extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof PrimeMeridian;

    private final QuotedName name;
    private final SignedNumericLiteral longitude;

    public PrimeMeridian(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral longitude) {
        super(start, end, index);
        this.name = name;
        this.longitude = longitude;
    }

    public QuotedName getName() {
        return this.name;
    }

    public SignedNumericLiteral getLongitude() {
        return this.longitude;
    }
}
