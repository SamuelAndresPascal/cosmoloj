package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Unit extends com.cosmoloj.language.wkt.sf.expression.Unit {

    public static final Predicate<Object> INSTANCE_OF_CTS = t -> t instanceof Unit;

    private final Authority authority;

    public Unit(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral conversionFactor, final Authority authority) {
        super(start, end, index, name, conversionFactor);
        this.authority = authority;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
