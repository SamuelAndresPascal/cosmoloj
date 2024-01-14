package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.text.lexeme.simple.StringLiteral;
import com.cosmoloj.language.common.text.lexeme.simple.StringLiteralBuilder;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisNameAbrev extends StringLiteral {

    private static final int[] DOUBLE_QUOTE_EXP = new int[]{'"'};

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof AxisNameAbrev;

    public AxisNameAbrev(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index, '"');
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getAbbreviation() {
        throw new UnsupportedOperationException();
    }

    public static StringLiteralBuilder builder() {
        return new StringLiteralBuilder(AxisNameAbrev.class, '"') {

            @Override
            public int[] expectations() {
                if (isEmpty() || !testToken(size() - 1, '"')) {
                    return Arrays.copyOf(DOUBLE_QUOTE_EXP, DOUBLE_QUOTE_EXP.length);
                }

                return null;
            }

            @Override
            public AxisNameAbrev build(final int first, final int last, final int index) {
                return new AxisNameAbrev(codePoints(), first, last, index);
            }
        };
    }
}
