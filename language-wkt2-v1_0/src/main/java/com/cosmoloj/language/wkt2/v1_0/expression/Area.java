package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 */
public class Area extends Extent {

    private final QuotedLatinText name;

    public Area(final int start, final int end, final int index, final QuotedLatinText name) {
        super(start, end, index);
        this.name = name;
    }

    public QuotedLatinText getName() {
        return this.name;
    }
}
