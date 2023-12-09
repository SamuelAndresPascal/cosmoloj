package com.cosmoloj.language.wkt.cts.parsing;

import com.cosmoloj.language.api.parsing.Scanner;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveLexer;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.lexeme.simple.DecimalSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.ExponentSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt.cts.lexeme.AxisDirectionName;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Samuel Andrés
 */
public class WktLexer extends AbstractPredictiveMappingUnpredictiveLexer {

    public WktLexer(final Scanner scanner, final List<Token> indexes, final int leftDelimiter,
            final int rightDelimiter) {
        super(scanner, indexes,
                cp -> {
                    return switch (cp) {
                        case '"' -> QuotedName.class;
                        case ',' -> SpecialSymbol.class;
                        case '+', '-' -> Sign.class;
                        default -> {
                            if (leftDelimiter == cp) {
                                yield LeftDelimiter.class;
                            } else if (rightDelimiter == cp) {
                                yield RightDelimiter.class;
                            } else if (Character.isAlphabetic(cp)) {
                                yield WktName.class;
                            } else if (Character.isDigit(cp)) {
                                yield UnsignedInteger.class;
                            }
                            throw new IllegalStateException("unexpected code point");
                        }
                    };
                },
                Set.of(WktName.builder(),
                AxisDirectionName.builder(),
                QuotedName.builder(),
                UnsignedInteger.builder(Character::isDigit),
                Sign.builder(),
                SpecialSymbol.builder(),
                LeftDelimiter.builder(leftDelimiter),
                RightDelimiter.builder(rightDelimiter),
                DecimalSeparator.builder('.'),
                ExponentSeparator.builder('E')));
    }

    public static WktLexer initialize(final String text, final int leftDelimiter, final int rightDelimiter) {

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), leftDelimiter,
                rightDelimiter);
        lexer.initialize();
        return lexer;
    }
}
