package com.cosmoloj.language.common.text.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.CharSequenceLexemeBuilder;
import java.util.Arrays;

/**
 * <pre>
 * &lt;character string literal&gt; ::= &lt;quote&gt; [ &lt;character representation&gt;...] &lt;quote&gt;
 </pre>
 *
 * @author Samuel Andrés
 */
public abstract class StringLiteralBuilder extends CharSequenceLexemeBuilder {

    // Vaut "vrai" lorsque le caractère précédent était un delimiter.
    private boolean escapeExpected = false;

    private final int escapeChar;
    private final int delimiterChar;

    private final int unicodeEscChar;

    private final int[] escapedChars;
    private boolean end = false;

    public StringLiteralBuilder(final Object type, final int delimiter) {
        this(type, delimiter, delimiter);
    }

    public StringLiteralBuilder(final Object type, final int delimiter, final int escape, final int... escapedChars) {
        this(type, delimiter, escape, 'u', escapedChars);
    }

    public StringLiteralBuilder(final Object type, final int delimiter, final int escape, final int unicodeEscChar,
            final int... escapedChars) {
        super(type);
        this.escapeChar = escape;
        this.delimiterChar = delimiter;
        this.unicodeEscChar = unicodeEscChar;
        this.escapedChars = escapedChars;
        Arrays.sort(escapedChars);
    }

    @Override
    public boolean check(final int codePoint) {

        if (end) {
            return false;
        }

        if (escapeExpected) {
            escapeExpected = false;
            // si on attend un caractère échappé il doit être : soit lui-même, soit le délimiteur, soit l'un spécifié
            return codePoint == escapeChar
                    || codePoint == delimiterChar
                    || codePoint == unicodeEscChar
                    || Arrays.binarySearch(escapedChars, codePoint) >= 0;
        } else if (codePoint == delimiterChar && size() != 0) {
            if (delimiterChar != escapeChar) {
                end = true;
                return true;
            }
        }

        if (escapeChar == codePoint && size() != 0) {
            escapeExpected = true;
        }

        return switch (size()) {
            case 0 -> delimiterChar == codePoint; // en 0 on n'accepte que le délimiteur
            default -> true; // pour les autres index, tout sauf le délimiter
        };
    }

    @Override
    public void resetState() {
        escapeExpected = false;
        end = false;
    }

    @Override
    public int[] expectations() {

        if (end) {
            return null;
        }

        if (isEmpty() || !testToken(size() - 1, this.escapeChar)) {
            return new int[]{this.escapeChar};
        }

        return null;
    }

    public static int[] buildEscapeMap(final int[][] map) {

        for (int i = 0; i < map.length; i++) {
            final int length = map[i].length;
            if (length != 2) {
                throw new IllegalArgumentException();
            }
        }

        final int[] result = new int[map.length];

        for (int i = 0; i < map.length; i++) {
            result[i] = map[i][0];
        }

        return result;
    }
}
