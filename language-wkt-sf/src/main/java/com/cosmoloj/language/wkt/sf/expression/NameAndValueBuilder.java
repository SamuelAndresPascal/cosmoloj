package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <O>
 */
public abstract class NameAndValueBuilder<O extends Expression> extends CheckTokenBuilder<Token, O>
        implements PredicateListTokenBuilder<Token> {

    private final WktName referenceLabel;

    NameAndValueBuilder(final WktName referenceLabel) {
        this.referenceLabel = referenceLabel;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(this.referenceLabel,
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    public static NameAndValueBuilder<Unit> unit() {
        return new NameAndValueBuilder<Unit>(WktName.UNIT) {

            @Override
            public Unit build() {
                return new Unit(first(), last(), index(), token(2), token(4));
            }
        };
    }

    public static NameAndValueBuilder<PrimeMeridian> primeMeridian() {
        return new NameAndValueBuilder<PrimeMeridian>(WktName.PRIMEM) {

            @Override
            public PrimeMeridian build() {
                return new PrimeMeridian(first(), last(), index(), token(2), token(4));
            }
        };
    }

    public static NameAndValueBuilder<Parameter> parameter() {
        return new NameAndValueBuilder<Parameter>(WktName.PARAMETER) {

            @Override
            public Parameter build() {
                return new Parameter(first(), last(), index(), token(2), token(4));
            }
        };
    }
}
