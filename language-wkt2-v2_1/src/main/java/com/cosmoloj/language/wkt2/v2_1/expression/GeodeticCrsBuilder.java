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
 * @param <SEIR>
 */
public abstract class GeodeticCrsBuilder<SEIR extends ScopeExtentIdentifierRemark>
        extends CheckTokenBuilder<Token, SEIR> implements PredicateIndexTokenBuilder<Token> {

    public static GeodeticCrsBuilder<GeodeticCrs> geodeticCrs() {
        return new GeodeticCrsBuilder<>() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> WktKeyword.GEODCRS.or(WktKeyword.GEODETICCRS).or(WktKeyword.GEOCCS).or(WktKeyword.GEOGCS);
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
                    case 3 -> SpecialSymbol.comma;
                    case 4 -> GeodeticDatum.INSTANCE_OF;
                    case 5 -> SpecialSymbol.comma;
                    case 6 -> CoordinateSystem.class::isInstance;
                    default -> {
                        if (odd()) {
                            yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                        } else {
                            yield Scope.INSTANCE_OF
                                    .or(Extent.INSTANCE_OF)
                                    .or(Identifier.INSTANCE_OF)
                                    .or(Remark.INSTANCE_OF);
                        }
                    }
                };
            }

            @Override
            public GeodeticCrs build() {

                return new GeodeticCrs(first(), last(), index(), token(2), token(4), token(6),
                        firstToken(Scope.INSTANCE_OF),
                        tokens(Extent.INSTANCE_OF),
                        tokens(Identifier.INSTANCE_OF),
                        firstToken(Remark.INSTANCE_OF));
            }
        };
    }

    public static GeodeticCrsBuilder<GeodeticCrs.Geographic2DCrs> geographic2DCrs() {
        return new GeodeticCrsBuilder<>() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> WktKeyword.GEODCRS.or(WktKeyword.GEODETICCRS).or(WktKeyword.GEOCCS).or(WktKeyword.GEOGCS);
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
                    case 3 -> SpecialSymbol.comma;
                    case 4 -> GeodeticDatum.INSTANCE_OF;
                    case 5 -> SpecialSymbol.comma;
                    case 6 -> CoordinateSystem.Ellipsoidal2DCoordinateSystem.class::isInstance;
                    default -> {
                        if (odd()) {
                            yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                        } else {
                            yield Scope.INSTANCE_OF
                                    .or(Extent.INSTANCE_OF)
                                    .or(Identifier.INSTANCE_OF)
                                    .or(Remark.INSTANCE_OF);
                        }
                    }
                };
            }

            @Override
            public GeodeticCrs.Geographic2DCrs build() {

                return new GeodeticCrs.Geographic2DCrs(first(), last(), index(), token(2), token(4), token(6),
                        firstToken(Scope.INSTANCE_OF),
                        tokens(Extent.INSTANCE_OF),
                        tokens(Identifier.INSTANCE_OF),
                        firstToken(Remark.INSTANCE_OF));
            }
        };
    }
}