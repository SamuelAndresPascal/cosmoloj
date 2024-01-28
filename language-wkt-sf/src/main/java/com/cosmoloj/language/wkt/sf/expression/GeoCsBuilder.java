package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <O>
 */
public abstract class GeoCsBuilder<O extends Expression> extends CheckTokenBuilder<Token, O>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    private final Predicate<Object> isGeogcs;

    private final WktName referenceLabel;

    GeoCsBuilder(final WktName referenceLabel) {
        this.referenceLabel = referenceLabel;
        this.isGeogcs = t -> this.referenceLabel.equals(WktName.GEOGCS);
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(this.referenceLabel,
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                SpecialSymbol.COMMA,
                Datum.class::isInstance,
                SpecialSymbol.COMMA,
                PrimeMeridian.class::isInstance,
                SpecialSymbol.COMMA,
                Unit.class::isInstance,
                // un GEOGCS 3D doit prévoir l'unité linéaire verticale
                Predicates.of(RightDelimiter.class::isInstance).or(SpecialSymbol.COMMA.and(this.isGeogcs)),
                // on continue uniquement si on n'a pas fermé l'expression au lexème précédent
                Unit.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return index == 10 ? SpecialSymbol.COMMA : Predicates.yes();
    }

    public static GeoCsBuilder<GeocentricCs> geoc() {
        return new GeoCsBuilder<GeocentricCs>(WktName.GEOCCS) {

            @Override
            public GeocentricCs build() {
                return new GeocentricCs(first(), last(), index(), token(2), token(4), token(6), token(8));
            }
        };
    }

    public static GeoCsBuilder<GeographicCs> geog() {
        return new GeoCsBuilder<GeographicCs>(WktName.GEOGCS) {

            @Override
            public GeographicCs build() {
                return new GeographicCs(first(), last(), index(), token(2), token(4), token(6), token(8), token(10));
            }
        };
    }
}
