package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.CharSequenceLexemeBuilder;
import java.util.function.IntPredicate;

/**
 *
 * @author Samuel Andrés
 */
public class UnsignedIntegerBuilder extends CharSequenceLexemeBuilder {

    private final IntPredicate isDigit;

    public UnsignedIntegerBuilder(final IntPredicate isDigit) {
        super(UnsignedInteger.class);
        this.isDigit = isDigit;
    }

    @Override
    public boolean check(final int codePoint) {
        return isDigit.test(codePoint);
    }

    @Override
    public UnsignedInteger build(final int first, final int last, final int index) {
        return new UnsignedInteger(codePoints(), first, last, index);
    }

    @Override
    protected void resetState() {
    }
}
