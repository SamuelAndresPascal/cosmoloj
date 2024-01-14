package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;

/**
 *
 * @author Samuel Andrés
 */
public class TemporalExtent extends Extent {

    private final Lexeme start;
    private final Lexeme end;

    public TemporalExtent(final int start, final int end, final int index,
            final Lexeme startTime, final Lexeme endTime) {
        super(start, end, index);
        this.start = startTime;
        this.end = endTime;
    }

    public Lexeme getExtentStart() {
        return start;
    }

    public Lexeme getExtentEnd() {
        return end;
    }
}
