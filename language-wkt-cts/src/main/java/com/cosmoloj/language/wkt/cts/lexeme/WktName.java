package com.cosmoloj.language.wkt.cts.lexeme;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;

/**
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.CTS_REVISION_V1_0)
@SectionReference(type = SectionReferenceType.SECTION, id = {"7", "8"})
public enum WktName implements SemanticEnum<WktName> {

    AUTHORITY,
    AXIS,
    TOWGS84,
    PROJCS,
    GEOGCS,
    GEOCCS,
    LOCAL_CS,
    VERT_CS,
    FITTED_CS,
    COMPD_CS,
    DATUM,
    LOCAL_DATUM,
    VERT_DATUM,
    PROJECTION,
    PARAMETER,
    @SectionReference(type = SectionReferenceType.SECTION, id = {"7.3.17"})
    SPHEROID,
    @SectionReference(type = SectionReferenceType.SECTION, id = {"7.3.17"})
    ELLIPSOID, // comptatibilité avec des exemples donnés dans la norme WKT-SF
    PRIMEM,
    UNIT,
    PARAM_MT,
    INVERSE_MT,
    PASSTHROUGH_MT,
    CONCAT_MT;

    public static EnumLexemeBuilder<WktName> builder() {
        return EnumCase.IGNORE.builder(WktName.class, WktName.values());
    }
}
