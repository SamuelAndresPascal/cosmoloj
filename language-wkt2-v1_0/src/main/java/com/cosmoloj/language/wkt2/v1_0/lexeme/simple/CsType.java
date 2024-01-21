package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum CsType implements SemanticEnum<CsType> {

    AFFINE("affine"),
    CARTESIAN("Cartesian"),
    CYLINDRICAL("cylindrical"),
    ELLIPSOIDAL("ellipsoidal"),
    LINEAR("linear"),
    PARAMETRIC("parametric"),
    POLAR("polar"),
    SPHERICAL("spherical"),
    TEMPORAL("temporal"),
    VERTICAL("vertical");

    private final String codePoints;

    CsType(final String codePoints) {
        this.codePoints = codePoints;
    }

    @Override
    public String getCodePoints() {
        return this.codePoints;
    }

    public static EnumLexemeBuilder<CsType> builder() {
        return EnumCase.IGNORE.builder(CsType.class, CsType.values());
    }
}
