package com.cosmoloj.language.common.impl.parsing;

/**
 *
 * @author Samuel Andrés
 */
public final class LexerCode {

    private LexerCode() {
    }

    /**
     * <div class="fr">Valeur du point de code courant avant initialisation du lexer et en fin de parcours du scanner.
     * </div>
     */
    public static final int NO_MORE_CODE_POINT = Integer.MIN_VALUE;
    public static final int NOT_YET_STARTED = -1;

    public static String message(final int lexerCode) {
        return switch (lexerCode) {
            case NOT_YET_STARTED -> "initial lexer position : lexer has not yet started";
            case NO_MORE_CODE_POINT -> "end lexer position : no more code point to read";
            default -> "(no message for code " + lexerCode + ")";
        };
    }
}
