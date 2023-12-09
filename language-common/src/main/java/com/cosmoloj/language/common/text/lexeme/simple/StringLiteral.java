package com.cosmoloj.language.common.text.lexeme.simple;

import com.cosmoloj.language.common.impl.semantic.StringLexeme;

/**
 * <pre>
 * &lt;character string literal&gt; ::= &lt;quote&gt; [ &lt;character representation&gt;... ] &lt;quote&gt;
 * </pre>
 *
 * @author Samuel Andrés
 */
public class StringLiteral extends StringLexeme {

    private final int escapeChar;
    private final int delimiterChar;
    private final int unicodeEscChar;
    private final int[] escapedChars;

    public StringLiteral(final String codePoints, final int first, final int last, final int index,
            final int delimiter, final int escape, final int unicodeEscChar, final int... escapedChars) {
        super(codePoints, first, last, index);
        this.escapeChar = escape;
        this.delimiterChar = delimiter;
        this.unicodeEscChar = unicodeEscChar;
        this.escapedChars = escapedChars;
    }

    public StringLiteral(final String codePoints, final int first, final int last, final int index,
            final int delimiter, final int escape, final int... escapedChars) {
        this(codePoints, first, last, index, delimiter, escape, 'u', escapedChars);
    }

    public StringLiteral(final String codePoints, final int first, final int last, final int index,
            final int delimiter) {
        this(codePoints, first, last, index, delimiter, delimiter);
    }

    @Override
    public String parse(final String codePoints) {

        final int delimCount = Character.charCount(delimiterChar);

        // on supprime les caractères de délimitation de début et de fin
        final String input = codePoints.substring(delimCount, codePoints.length() - delimCount);

        // mémoire d'échappement de caractère
        boolean escape = false;

        // mémoire de position dans un code unicode
        // (de 4 à 1 ; 0 signifie qu'on n'est pas en train de lire un caractère unicode)
        int unicodeIndex = 0;
        // mémoire du contenu des caractères d'un code unicode
        int[] unicodeChar = new int[4];

        final int length = input.length();
        final StringBuilder sb = new StringBuilder();

        int offset = 0;
        while (offset < length) {
            final int codepoint = input.codePointAt(offset);

            if (unicodeIndex > 0) {
                // gestion des caractères unicode
                unicodeChar[4 - unicodeIndex--] = codepoint;
                if (unicodeIndex == 0) {
                    final StringBuilder sb1 = new StringBuilder("0x");
                    for (int i = 0; i < unicodeChar.length; i++) {
                        sb1.appendCodePoint(unicodeChar[i]);
                    }
                    sb.append(Character.toString(Integer.decode(sb1.toString())));
                }
            } else if (escape) {
                // gestion des caractères échappés
                escape = false;
                if (delimiterChar == codepoint || escapeChar == codepoint) {
                    sb.appendCodePoint(codepoint);
                } else if (unicodeEscChar == codepoint) {
                    // un 'u' échappé : les quatre caractères suivants définissent un code unicode
                    unicodeIndex = 4;
                } else {
                    // cas général des caractères échappés
                    sb.appendCodePoint(this.escapedChars[codepoint]);
                }
            } else if (escapeChar == codepoint) {
                // gestion du caractère d'échappement
                escape = true;
            } else {
                // cas général
                sb.appendCodePoint(codepoint);
            }

           offset += Character.charCount(codepoint);
        }

        return sb.toString();
    }

    public static int[] buildEscapeMap(final int[][] map) {

        int max = 0;
        for (int i = 0; i < map.length; i++) {
            final int length = map[i].length;
            if (length != 2) {
                throw new IllegalArgumentException();
            }

            max = Math.max(max, map[i][0]);
        }

        final int[] result = new int[max + 1];

        for (int i = 0; i < map.length; i++) {
            result[map[i][0]] = map[i][1];
        }

        return result;
    }
}
