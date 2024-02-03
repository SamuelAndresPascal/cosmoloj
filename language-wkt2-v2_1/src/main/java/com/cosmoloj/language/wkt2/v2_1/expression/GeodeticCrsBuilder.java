package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <S>
 */
public abstract class GeodeticCrsBuilder<S extends ScopeExtentIdentifierRemark>
        extends CheckTokenBuilder<Token, S> implements PredicateIndexTokenBuilder<Token> {

    public static GeodeticCrsBuilder<GeodeticCrs> geodeticCrs() {
        return new GeodeticCrsBuilder<>() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> pb(WktKeyword.GEODCRS, WktKeyword.GEODETICCRS, WktKeyword.GEOCCS, WktKeyword.GEOGCS);
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.class::isInstance;
                    case 3 -> SpecialSymbol.COMMA;
                    case 4 -> GeodeticDatum.class::isInstance;
                    case 5 -> SpecialSymbol.COMMA;
                    case 6 -> CoordinateSystem.class::isInstance;
                    default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                        : pb(Usage.class, Identifier.class, Remark.class);
                };
            }

            @Override
            public GeodeticCrs build() {

                return new GeodeticCrs(first(), last(), index(),
                        token(2),
                        token(4),
                        token(6),
                        tokens(Usage.class),
                        tokens(Identifier.class),
                        firstToken(Remark.class));
            }
        };
    }

    public static GeodeticCrsBuilder<GeodeticCrs.Geographic2DCrs> geographic2DCrs() {
        return new GeodeticCrsBuilder<>() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> pb(WktKeyword.GEODCRS, WktKeyword.GEODETICCRS, WktKeyword.GEOCCS, WktKeyword.GEOGCS);
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.class::isInstance;
                    case 3 -> SpecialSymbol.COMMA;
                    case 4 -> GeodeticDatum.class::isInstance;
                    case 5 -> SpecialSymbol.COMMA;
                    case 6 -> CoordinateSystem.Ellipsoidal2DCoordinateSystem.class::isInstance;
                    default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                        : pb(Usage.class, Identifier.class, Remark.class);
                };
            }

            @Override
            public GeodeticCrs.Geographic2DCrs build() {

                return new GeodeticCrs.Geographic2DCrs(first(), last(), index(),
                        token(2),
                        token(4),
                        token(6),
                        tokens(Usage.class),
                        tokens(Identifier.class),
                        firstToken(Remark.class));
            }
        };
    }
}
