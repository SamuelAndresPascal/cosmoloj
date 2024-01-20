package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum Direction implements SemanticEnum<Direction> {

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

    public static EnumLexemeBuilder<Direction> builder() {
        return EnumLexemeBuilder.ignoreCase(Direction.class, Direction.values());
    }
}
