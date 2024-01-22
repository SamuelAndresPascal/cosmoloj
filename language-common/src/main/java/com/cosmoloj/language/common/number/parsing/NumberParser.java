package com.cosmoloj.language.common.number.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.UnpredictiveLexer;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveLexer;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveParser;
import com.cosmoloj.language.common.number.lexeme.compound.ApproximateNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.ApproximateNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedInteger;
import com.cosmoloj.language.common.number.lexeme.compound.SignedIntegerBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteralBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.UnsignedNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.simple.DecimalSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.ExponentSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.Sign;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import java.util.Arrays;
import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;

/**
 *
 * @author Samuel Andrés
 *
 * @param <L>
 */
public class NumberParser<L extends AbstractPredictiveLexer & UnpredictiveLexer>
        extends AbstractPredictiveMappingUnpredictiveParser<L> {

    private final int[] exponentSeparators;
    private final int decimalSeparator;

    public NumberParser(final L lexer, final int decimalSeparator, final int... exponentSeparators) {
        super(lexer);
        this.decimalSeparator = decimalSeparator;
        this.exponentSeparators = exponentSeparators;
        Arrays.sort(this.exponentSeparators);
    }

    @Override
    public Token parse() throws LanguageException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SignedIntegerBuilder getSignedIntegerBuilder() {
        return new SignedIntegerBuilder();
    }

    public ExactNumericLiteralBuilder getExactNumericLiteralBuilder() {
        return new ExactNumericLiteralBuilder();
    }

    public ApproximateNumericLiteralBuilder getApproximateNumericLiteralBuilder() {
        return new ApproximateNumericLiteralBuilder();
    }

    public SignedNumericLiteralBuilder getSignedNumericLiteralBuilder() {
        return new SignedNumericLiteralBuilder();
    }

    /**
     * <pre>
     * &lt;signed integer&gt; ::= [ &lt;sign&gt; ] &lt;unsigned integer&gt;
     * </pre>
     *
     * @return <span class="fr"></span>
     * @throws LanguageException <span class="fr"></span>
     */
    public SignedInteger signedInteger() throws LanguageException {

        final Lexeme lex = flushAndLex();

        final SignedIntegerBuilder builder = getSignedIntegerBuilder();
        // Y a-t-il un signe ?
        if (lex instanceof EnumLexeme) {
            switch ((Sign) lex.getSemantics()) {
                case PLUS, MINUS -> builder.list(
                        lex,
                        flushAndLex(UnsignedInteger.class));
                default -> throw unexpected(lex).semantics(Sign.PLUS, Sign.MINUS).exception();
            }
        } else if (lex instanceof UnsignedInteger) {
            builder.list(lex);
        } else {
            throw unexpected(lex).types(Sign.class, UnsignedInteger.class).exception();
        }
        return build(builder);
    }

    /**
     * <pre>
     * &lt;exponent&gt; ::= &lt;signed integer&gt;
     * </pre>
     *
     * @return <span class="fr"></span>
     * @throws LanguageException <span class="fr"></span>
     */
    public SignedInteger exponent() throws LanguageException {
        return signedInteger();
    }

    /**
     * <pre>
     * &lt;exact numeric literal&gt; ::= &lt;unsigned integer&gt; [ &lt;period&gt; [ &lt;unsigned integer&gt; ] ]
     * | &lt;period&gt; &lt;unsigned integer&gt;
     * </pre>
     *
     * @return <span class="fr"></span>
     * @throws LanguageException <span class="fr"></span>
     */
    public ExactNumericLiteral exactNumericLiteral() throws LanguageException {
        return exactNumericLiteral(flushAndLex());
    }

    /**
     * <pre>
     * &lt;exact numeric literal&gt; ::= &lt;unsigned integer&gt; [ &lt;period&gt; [ &lt;unsigned integer&gt; ] ]
     * | &lt;period&gt; &lt;unsigned integer&gt;
     * </pre>
     *
     * @param lexeme
     * @return <span class="fr"></span>
     * @throws LanguageException <span class="fr"></span>
     */
    public ExactNumericLiteral exactNumericLiteral(final Lexeme lexeme) throws LanguageException {

        final ExactNumericLiteralBuilder builder = getExactNumericLiteralBuilder();

        if (lexeme instanceof UnsignedInteger) {
            builder.list(lexeme);

            // Examen du lexème suivant. Est-ce un point ?
            if (this.decimalSeparator == codePoint()) {

                builder.list(lex(DecimalSeparator.class)); // period

                // Examen du lexème suivant. Est-ce un entier ?
                if (Character.isDigit(codePoint())) {
                    builder.list(lex(UnsignedInteger.class));
                }
            }
        } else if (lexeme instanceof DecimalSeparator) {
            builder.list(
                        lexeme,
                        lex(UnsignedInteger.class));
        } else {
            throw unexpected(lexeme).types(UnsignedInteger.class, DecimalSeparator.class).exception();
        }

        return build(builder);
    }

    /**
     * <pre>
     * &lt;mantissa&gt; ::= &lt;exact numeric literal&gt;
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public ExactNumericLiteral mantissa() throws LanguageException {
        return exactNumericLiteral();
    }

    /**
     * <pre>
     * &lt;approximate numeric literal&gt; ::= &lt;mantissa&gt; E &lt;exponent&gt;
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public ApproximateNumericLiteral approximateNumericLiteral() throws LanguageException {
        return approximateNumericLiteral(mantissa());
    }

    /**
     * <pre>
     * &lt;approximate numeric literal&gt; ::= &lt;mantissa&gt; E &lt;exponent&gt;
     * </pre>
     *
     * @param mantissa
     * @return
     * @throws LanguageException
     */
    public ApproximateNumericLiteral approximateNumericLiteral(final ExactNumericLiteral mantissa)
            throws LanguageException {
        return build(getApproximateNumericLiteralBuilder().list(
                mantissa,
                flushAndLex(ExponentSeparator.class),
                exponent()));
    }

    /**
     * <pre>
     * &lt;unsigned numeric literal&gt; ::= &lt;exact numeric literal&gt; | &lt;approximate numeric literal&gt;
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public UnsignedNumericLiteral unsignedNumericLiteral() throws LanguageException {

        final ExactNumericLiteral mantissa = exactNumericLiteral();

        if (Arrays.binarySearch(exponentSeparators, codePoint()) >= 0) {
            return approximateNumericLiteral(mantissa);
        }

        return mantissa;
    }

    /**
     * <pre>
     * &lt;unsigned numeric literal&gt; ::= &lt;exact numeric literal&gt; | &lt;approximate numeric literal&gt;
     * </pre>
     *
     * @param lexeme
     * @return
     * @throws LanguageException
     */
    public UnsignedNumericLiteral unsignedNumericLiteral(final Lexeme lexeme) throws LanguageException {

        final ExactNumericLiteral mantissa = exactNumericLiteral(lexeme);

        if (Arrays.binarySearch(exponentSeparators, codePoint()) >= 0) {
            return approximateNumericLiteral(mantissa);
        }

        return mantissa;
    }

    /**
     * <pre>
     * &lt;signed numeric literal&gt; ::= [&lt;sign&gt; ] &lt;unsigned numeric literal&gt;
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public SignedNumericLiteral signedNumericLiteral() throws LanguageException {
        return signedNumericLiteral(flushAndLex());
    }

    /**
     * <pre>
     * &lt;signed numeric literal&gt; ::= [&lt;sign&gt; ] &lt;unsigned numeric literal&gt;
     * </pre>
     *
     * @param lexeme
     * @return
     * @throws LanguageException
     */
    public SignedNumericLiteral signedNumericLiteral(final Lexeme lexeme) throws LanguageException {

        final SignedNumericLiteralBuilder builder = getSignedNumericLiteralBuilder();

        if (lexeme instanceof EnumLexeme) {
            switch ((Sign) lexeme.getSemantics()) {
                case PLUS, MINUS -> builder.list(lexeme,
                        unsignedNumericLiteral(lex()));
                default -> throw unexpected(lexeme).semantics(Sign.PLUS, Sign.MINUS).exception();
            }
        } else {
             builder.list(unsignedNumericLiteral(lexeme));
        }

        return build(builder);
    }
}
