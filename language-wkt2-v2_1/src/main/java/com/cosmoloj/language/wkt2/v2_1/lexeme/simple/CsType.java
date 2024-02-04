package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum CsType implements SemanticEnum<CsType> {

    AFFINE("affine", Type.SPATIAL),
    CARTESIAN("Cartesian", Type.SPATIAL),
    CYLINDRICAL("cylindrical", Type.SPATIAL),
    ELLIPSOIDAL("ellipsoidal", Type.SPATIAL),
    LINEAR("linear", Type.SPATIAL),
    PARAMETRIC("parametric", Type.SPATIAL),
    POLAR("polar", Type.SPATIAL),
    SPHERICAL("spherical", Type.SPATIAL),
    VERTICAL("vertical", Type.SPATIAL),
    @Deprecated TEMPORAL("temporal", Type.TEMPORAL),
    TEMPORAL_COUNT("temporalCount", Type.TEMPORAL),
    TEMPORAL_MEASURE("temporalMeasure", Type.TEMPORAL),
    ORDINAL("ordinal", Type.ORDINAL),
    TEMPORAL_DATE_TIME("temporalDateTime", Type.ORDINAL);

    public enum Type {
        SPATIAL, TEMPORAL, ORDINAL
    }

    private final String codePoints;
    private final Type type;

    CsType(final String codePoints, final Type type) {
        this.codePoints = codePoints;
        this.type = type;
    }

    @Override
    public String getCodePoints() {
        return this.codePoints;
    }

    public Type getType() {
        return this.type;
    }

    public static EnumLexemeBuilder<CsType> builder() {
        return EnumCase.IGNORE.builder(CsType.class, CsType.values());
    }
}
