package com.cosmoloj.language.wkt.geom;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.util.bib.Reference;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.SF_ACCESS_PART_1_V1_2_1)
public enum Keyword implements SemanticEnum<Keyword> {
    POINT,
    LINESTRING,
    POLYGON,
    POLYHEDRALSURFACE,
    TRIANGLE,
    TIN,
    MULTIPOINT,
    MULTILINESTRING,
    MULTIPOLYGON,
    GEOMETRYCOLLECTION;


    private final String codePoints;

    Keyword(final String codePoints) {
        this.codePoints = codePoints;
    }

    Keyword() {
        this(null);
    }

    @Override
    public String getCodePoints() {
        return this.codePoints == null ? name() : this.codePoints;
    }



    public static EnumLexemeBuilder<Keyword> builder() {
        return EnumCase.IGNORE.builder(Keyword.class, Keyword.values());
    }
}
