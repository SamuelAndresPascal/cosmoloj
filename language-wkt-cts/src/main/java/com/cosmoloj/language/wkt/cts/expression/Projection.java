package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Projection extends com.cosmoloj.language.wkt.sf.expression.Projection {

    private final Authority authority;

    public Projection(final int start, final int end, final int index, final QuotedName name,
            final Authority authority) {
        super(start, end, index, name);
        this.authority = authority;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
