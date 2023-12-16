package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class CompoundCrs extends Crs {

    private final HorizontalCrs horizontal;
    private final Crs second;
    private final SimpleCrsShell.TemporalCrs temporal;

    public CompoundCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final HorizontalCrs horizontal, final Crs second,
            final SimpleCrsShell.TemporalCrs temporal, final Scope scope, final List<Extent> extents,
            final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, scope, extents, identifiers, remark);
        this.horizontal = horizontal;
        this.second = second;
        this.temporal = temporal;
    }

    public HorizontalCrs getHorizontal() {
        return this.horizontal;
    }

    public Crs getSecondCrs() {
        return this.second;
    }

    public SimpleCrsShell.TemporalCrs getThirdCrs() {
        return temporal;
    }
}
