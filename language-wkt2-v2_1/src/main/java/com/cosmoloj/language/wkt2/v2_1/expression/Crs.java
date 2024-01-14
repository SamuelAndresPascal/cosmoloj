package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Crs extends ScopeExtentIdentifierRemark.Default {

    public static final Predicate<Object> CRS = t -> t instanceof Crs;

    private final QuotedLatinText name;

    public Crs(final int start, final int end, final int index, final QuotedLatinText name,
            final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
            final Remark remark) {
        super(start, end, index, scope, extents, identifiers, remark);
        this.name = name;
    }

    public QuotedLatinText getName() {
        return this.name;
    }
}
