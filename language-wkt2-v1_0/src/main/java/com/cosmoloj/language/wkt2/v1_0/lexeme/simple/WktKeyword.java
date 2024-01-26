package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import com.cosmoloj.util.function.Predicates;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.WKT_CRS_V1_0)
public enum WktKeyword implements SemanticEnum<WktKeyword> {

    ABRIDGEDTRANSFORMATION,
    ANCHOR,
    ANGLEUNIT,
    AREA,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.2.2")
    AUTHORITY, // compatibilité CTS
    AXIS,
    BASEENGCRS,
    BASEGEODCRS,
    BASEPARAMCRS,
    BASEPROJCRS,
    BASETIMECRS,
    BASEVERTCRS,
    BBOX,
    BEARING,
    BOUNDCRS,
    CITATION,
    COMPOUNDCRS,
    COMPD_CS, // compatibilité WKT-CTS
    CONVERSION,
    COORDINATEOPERATION,
    CS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3")
    DATUM,
    DERIVINGCONVERSION,
    EDATUM,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.1")
    ELLIPSOID,
    ENGCRS,
    ENGINEERINGCRS,
    ENGINEERINGDATUM,
    GEODCRS,
    GEODETICCRS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité CS ellipsoidal 3D
    GEOGCS, // compatibilité WKT-CTS
    GEOCCS, // compatibilité WKT-CTS
    GEODETICDATUM,
    ID,
    IDATUM,
    IMAGECRS,
    IMAGEDATUM,
    INTERPOLATIONCRS,
    LENGTHUNIT,
    LOCAL_CS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3") // normalement non rétrocompatible
    LOCAL_DATUM,
    MERIDIAN,
    METHOD,
    OPERATIONACCURACY,
    ORDER,
    PARAMETER,
    PARAMETERFILE,
    PARAMETRICCRS,
    PARAMETRICDATUM,
    PARAMETRICUNIT,
    PDATUM,
    PRIMEM,
    PRIMEMERIDIAN,
    PROJCRS,
    PROJECTEDCRS,
    PROJCS, // compatibilité WKT-CTS
    PROJECTION,
    REMARK,
    SCALEUNIT,
    SCOPE,
    SOURCECRS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.2")
    SPHEROID,
    TARGETCRS,
    TDATUM,
    TIMECRS,
    TIMEDATUM,
    TIMEEXTENT,
    TIMEORIGIN,
    TIMEUNIT,
    UNIT,
    URI,
    VDATUM,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3") // normalement non rétrocompatible
    VERT_DATUM,
    VERTCRS,
    VERT_CS, // compatibilité WKT-CTS
    VERTICALCRS,
    VERTICALDATUM,
    VERTICALEXTENT;


    // autres mots-clefs à gérer pour la compatibilité WKT-CTS
//    FITTED_CS;
//    PARAM_MT, // pas de rétrocompatibilité
//    INVERSE_MT, // pas de rétrocompatibilité
//    PASSTHROUGH_MT, // pas de rétrocompatibilité
//    CONCAT_MT // pas de rétrocompatibilité
//    TOWGS84,

    public static EnumLexemeBuilder<WktKeyword> builder() {
        return EnumCase.IGNORE.builder(WktKeyword.class, WktKeyword.values());
    }

    public static boolean isUnit(final EnumLexeme<WktKeyword> t) {
            return Predicates.of(WktKeyword.UNIT)
                    .or(WktKeyword.ANGLEUNIT)
                    .or(WktKeyword.LENGTHUNIT)
                    .or(WktKeyword.SCALEUNIT)
                    .or(WktKeyword.PARAMETRICUNIT)
                    .or(WktKeyword.TIMEUNIT)
                    .test(t);
    }

    public static boolean isExtent(final EnumLexeme<WktKeyword> t) {
            return Predicates.of(WktKeyword.AREA)
                    .or(WktKeyword.BBOX)
                    .or(WktKeyword.VERTICALEXTENT)
                    .or(WktKeyword.TIMEEXTENT)
                    .test(t);
    }
}
