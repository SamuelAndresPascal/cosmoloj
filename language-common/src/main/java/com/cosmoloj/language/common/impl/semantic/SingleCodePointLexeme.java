package com.cosmoloj.language.common.impl.semantic;

import com.cosmoloj.language.api.semantic.Lexeme;

/**
 *
 * @author Samuel Andrés
 */
public abstract class SingleCodePointLexeme implements Lexeme {

    private final int codePoint;
    private final int first;
    private final int last;
    private final int index;

    public SingleCodePointLexeme(final int codePoint, final int first, final int last, final int index) {
        this.first = first;
        this.last = last;
        this.index = index;
        this.codePoint = codePoint;
    }

    @Override
    public int first() {
        return first;
    }

    @Override
    public int last() {
        return last;
    }

    @Override
    public int order() {
        return index;
    }

    @Override
    public Integer getSemantics() {
        return codePoint;
    }

    @Override
    public String getCodePoints() {
        return new StringBuilder().appendCodePoint(codePoint).toString();
    }

    @Override
    public String toString() {
        return "SingleCodePointLexeme{" + "codePoint=" + codePoint + ", first=" + first + ", last=" + last
                + ", index=" + index + '}';
    }
}
