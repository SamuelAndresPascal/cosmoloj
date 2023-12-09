package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.builder.LexemeSequenceLexemeBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.DecimalSeparator;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import java.util.List;
import java.util.function.Predicate;
import com.cosmoloj.language.api.semantic.Lexeme;

/**
 * <pre>
 * &lt;exact numeric literal&gt; ::= &lt;unsigned integer&gt; [ &lt;period&gt; [ &lt;unsigned integer&gt; ] ]
 * | &lt;period&gt; &lt;unsigned integer&gt;
 *
 * &lt;seconds value&gt; ::= &lt;seconds integer value&gt; [ &lt;period&gt; [ &lt;seconds fraction&gt; ] ]
 * </pre>
 *
 * @author Samuel Andrés
 */
public class ExactNumericLiteralBuilder extends LexemeSequenceLexemeBuilder<ExactNumericLiteral>
        implements PredicateListTokenBuilder<Lexeme> {

    private boolean decimal = false;

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(UnsignedInteger.UNSIGNED_INTEGER.or(DecimalSeparator.DECIMAL_SEPARATOR),
                DecimalSeparator.DECIMAL_SEPARATOR.or(UnsignedInteger.UNSIGNED_INTEGER),
                UnsignedInteger.UNSIGNED_INTEGER);
    }

    @Override
    public void add(final Lexeme token) {
        if (DecimalSeparator.DECIMAL_SEPARATOR.test(token)) {
            decimal = true;
        }
        super.add(token);
    }

    @Override
    public ExactNumericLiteral build() {
        return new ExactNumericLiteral(codePoints(), first(), last(), index(), this.decimal);
    }

    protected boolean isDecimal() {
        return decimal;
    }
}
