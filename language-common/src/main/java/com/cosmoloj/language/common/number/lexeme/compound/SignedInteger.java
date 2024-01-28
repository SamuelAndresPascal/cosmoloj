package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.semantic.IntegerLexeme;

/**
 *
 * @author Samuel Andrés
 */
public class SignedInteger extends IntegerLexeme {

    public SignedInteger(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }
}
