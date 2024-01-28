package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class PrimeMeridian extends com.cosmoloj.language.wkt.sf.expression.PrimeMeridian {

    private final Authority authority;

    public PrimeMeridian(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral longitude, final Authority authority) {
        super(start, end, index, name, longitude);
        this.authority = authority;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
