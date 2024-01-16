package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.Locale;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum Direction implements SemanticEnum<Direction>, Predicate<Object> {

    north,
    northNorthEast,
    northEast,
    eastNorthEast,
    east,
    eastSouthEast,
    southEast,
    southSouthEast,
    south,
    southSouthWest,
    southWest,
    westSouthWest,
    west,
    westNortWest,
    northWest,
    northNorthWest,
    geocentricX,
    geocentricY,
    geocentricZ,
    up,
    down,
    forward,
    aft,
    port,
    starboard,
    clockwise,
    counterClockwise,
    columnPositive,
    columnNegative,
    rowPositive,
    rowNegative,
    displayRight,
    displayLeft,
    displayUp,
    displayDown,
    future,
    past,
    towards,
    awayFrom,
    unspecified;

    @Override
    public boolean test(final Object token) {
        return token instanceof Direction.Lexeme && this.equals(((Direction.Lexeme) token).getSemantics());
    }

    public static Direction toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<Direction> {

        public static final Predicate<Object> INSTANCE_OF = Lexeme.class::isInstance;

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public Direction parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<Direction> builder() {
        return new EnumLexemeBuilder<Direction>(Direction.class, Direction.values(), EnumLexemeBuilder.Case.IGNORE,
                Locale.ROOT) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
