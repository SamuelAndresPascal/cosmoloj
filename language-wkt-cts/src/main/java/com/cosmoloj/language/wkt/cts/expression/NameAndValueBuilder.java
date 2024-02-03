package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <O>
 */
public abstract class NameAndValueBuilder<O extends Expression> extends CheckTokenBuilder<Token, O>
        implements PredicateListTokenBuilder<Token>, ConstraintPredicateTokenBuilder<Token> {

    private final WktName referenceLabel;

    protected NameAndValueBuilder(final WktName referenceLabel) {
        this.referenceLabel = referenceLabel;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(this.referenceLabel,
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                Predicates.of(RightDelimiter.class::isInstance).or(SpecialSymbol.COMMA),
                Authority.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Predicate<? super Token> constraint(final int index) {
        return switch (index) {
            case 0 -> switch (size()) {
                case 5 -> waiting(SpecialSymbol.COMMA)
                ? WktName.UNIT.or(WktName.PRIMEM).or(WktName.VERT_DATUM).or(WktName.LOCAL_DATUM)
                : Predicates.yes();
                default -> Predicates.yes();
            };
            default -> Predicates.yes();
        };
    }

    public static NameAndValueBuilder<Unit> unit() {
        return new NameAndValueBuilder<>(WktName.UNIT) {

            @Override
            public Unit build() {
                return new Unit(first(), last(), index(), token(2), token(4), token(6));
            }
        };
    }

    public static NameAndValueBuilder<PrimeMeridian> primeMeridian() {
        return new NameAndValueBuilder<>(WktName.PRIMEM) {

            @Override
            public PrimeMeridian build() {
                return new PrimeMeridian(first(), last(), index(), token(2), token(4), token(6));
            }
        };
    }

    public static NameAndValueBuilder<Parameter> parameter() {
        return new NameAndValueBuilder<>(WktName.PARAMETER) {

            @Override
            public Parameter build() {
                return new Parameter(first(), last(), index(), token(2), token(4));
            }
        };
    }

    public static NameAndValueBuilder<VertDatum> vertDatum() {
        return new NameAndValueBuilder<>(WktName.VERT_DATUM) {

            @Override
            public VertDatum build() {
                return new VertDatum(first(), last(), index(), token(2), token(4), token(6));
            }
        };
    }

    public static NameAndValueBuilder<LocalDatum> localDatum() {
        return new NameAndValueBuilder<>(WktName.LOCAL_DATUM) {

            @Override
            public LocalDatum build() {
                return new LocalDatum(first(), last(), index(), token(2), token(4), token(6));
            }
        };
    }
}
