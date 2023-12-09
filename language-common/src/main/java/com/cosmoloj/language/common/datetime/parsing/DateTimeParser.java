package com.cosmoloj.language.common.datetime.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.UnpredictiveLexer;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveLexer;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveParser;
import com.cosmoloj.language.common.number.lexeme.simple.DecimalSeparator;

/**
 *
 * @author Samuel Andrés
 *
 * @param <L>
 */
public class DateTimeParser<L extends AbstractPredictiveLexer & UnpredictiveLexer>
        extends AbstractPredictiveMappingUnpredictiveParser<L> {

    private final int decimalSeparator;

    public DateTimeParser(final L lexer, final int decimalSeparator) {
        super(lexer);
        this.decimalSeparator = decimalSeparator;
    }

    @Override
    public Token parse() throws LanguageException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * <pre>
     * &lt;seconds value&gt; ::= &lt;seconds integer value&gt; [ &lt;period&gt; [ &lt;seconds fraction&gt; ] ]
     * </pre>
     *
     * @return <span class="fr"></span>
     * @throws LanguageException <span class="fr"></span>
     */
    public ExactNumericLiteral seconds_value() throws LanguageException {

        final ExactNumericLiteralBuilder builder = new ExactNumericLiteralBuilder();
        builder.checkAndAdd(flushAndLex(UnsignedInteger.class));

        // Examen du lexème suivant. Est-ce un point ?
        if (this.decimalSeparator == codePoint()) {
            builder.checkAndAdd(lex(DecimalSeparator.class)); // period

            // Examen du lexème suivant. Est-ce un entier ?
            if (Character.isDigit(codePoint())) {
                builder.checkAndAdd(lex(UnsignedInteger.class));
            }
        }

        return build(builder);
    }
}
