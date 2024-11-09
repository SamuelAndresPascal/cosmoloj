package com.cosmoloj.language.wkt.sf.lexeme;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;

/**
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.SF_ACCESS_PART_1_V1_2_1)
@SectionReference(type = SectionReferenceType.SECTION, id = "9.1")
public enum WktName implements SemanticEnum<WktName> {

    PROJCS,
    GEOGCS,
    GEOCCS,
    DATUM,
    PROJECTION,
    PARAMETER,
    @Page(74)
    SPHEROID,
    @Page(75)
    @SectionReference(type = SectionReferenceType.EXAMPLE, number = 2)
    ELLIPSOID,
    PRIMEM,
    UNIT;

    public static EnumLexemeBuilder<WktName> builder() {
        return EnumCase.IGNORE.builder(WktName.class, WktName.values());
    }
}
