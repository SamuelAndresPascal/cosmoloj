package com.cosmoloj.language.common.impl.semantic;

/**
 *
 * @author Samuel Andrés
 */
public enum StdTerminal {

    NONE('\u001a'),
    LINE_FEED('\n'), // \u000a
    CARRIAGE_RETURN('\r'); // \u000d

    private final int codePoint;

    StdTerminal(final int codePoint) {
        this.codePoint = codePoint;
    }

    public int getCodePoint() {
        return codePoint;
    }
}
