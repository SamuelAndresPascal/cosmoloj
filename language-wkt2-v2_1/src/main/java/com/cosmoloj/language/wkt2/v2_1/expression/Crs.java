package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Crs extends ScopeExtentIdentifierRemark.Default {

    private final QuotedLatinText name;

    protected Crs(final int start, final int end, final int index, final QuotedLatinText name,
            final List<Usage> usages, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, usages, identifiers, remark);
        this.name = name;
    }

    public QuotedLatinText getName() {
        return this.name;
    }
}
