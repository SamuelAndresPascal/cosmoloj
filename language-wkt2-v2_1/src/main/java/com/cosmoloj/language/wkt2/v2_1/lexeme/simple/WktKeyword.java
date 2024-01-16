package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.WKT_CRS_V1_0)
public enum WktKeyword implements SemanticEnum<WktKeyword>, Predicate<Object> {

    ABRIDGEDTRANSFORMATION,
    ANCHOR,
    ANCHOREPOCH,
    ANGLEUNIT,
    AREA,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.2.2")
    AUTHORITY, // compatibilité CTS
    AXIS,
    AXISMAXVALUE,
    AXISMINVALUE,
    BASEENGCRS,
    BASEGEODCRS,
    BASEGEOGCRS,
    BASEPARAMCRS,
    BASEPROJCRS,
    BASETIMECRS,
    BASEVERTCRS,
    BBOX,
    BEARING,
    BOUNDCRS,
    CALENDAR,
    CITATION,
    COMPOUNDCRS,
    CONCATENATEDOPERATION,
    COMPD_CS, // compatibilité WKT-CTS
    CONVERSION,
    COORDEPOCH,
    COORDINATEMETADATA,
    COORDINATEOPERATION,
    CS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3")
    DATUM,
    DEFININGTRANSFORMATION,
    DERIVEDPROJCRS,
    DERIVINGCONVERSION,
    DYNAMIC,
    EDATUM,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.1")
    ELLIPSOID,
    ENGCRS,
    ENGINEERINGCRS,
    ENGINEERINGDATUM,
    ENSEMBLE,
    ENSEMBLEACCURACY,
    EPOCH,
    FRAMEEPOCH,
    GEODCRS,
    GEODETICCRS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité CS ellipsoidal 3D
    GEOGCS, // compatibilité WKT-CTS
    GEOCCS, // compatibilité WKT-CTS
    GEODETICDATUM,
    GEOGCRS,
    GEOGRAPHICCRS,
    GEOIDMODEL,
    ID,
    IDATUM,
    IMAGECRS,
    IMAGEDATUM,
    INTERPOLATIONCRS,
    LENGTHUNIT,
    LOCAL_CS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3") // normalement non rétrocompatible
    LOCAL_DATUM,
    MEMBER,
    MERIDIAN,
    METHOD,
    MODEL,
    OPERATIONACCURACY,
    ORDER,
    PARAMETER,
    PARAMETERFILE,
    PARAMETRICCRS,
    PARAMETRICDATUM,
    PARAMETRICUNIT,
    PDATUM,
    POINTMOTIONOPERATION,
    PRIMEM,
    PRIMEMERIDIAN,
    PROJCRS,
    PROJECTEDCRS,
    PROJCS, // compatibilité WKT-CTS
    PROJECTION,
    RANGEMEANING,
    REMARK,
    SCALEUNIT,
    SCOPE,
    SOURCECRS,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.2")
    SPHEROID,
    STEP,
    TARGETCRS,
    TDATUM,
    TRF,
    TEMPORALQUANTITY,
    TIMECRS,
    TIMEDATUM,
    TIMEEXTENT,
    TIMEORIGIN,
    TIMEUNIT,
    TRIAXIAL,
    UNIT,
    URI,
    USAGE,
    VDATUM,
    VELOCITYGRID,
    VERSION,
    @SectionReference(type = SectionReferenceType.SECTION, id = "C.3.3") // normalement non rétrocompatible
    VERT_DATUM,
    VERTCRS,
    VERT_CS, // compatibilité WKT-CTS
    VERTICALCRS,
    VERTICALDATUM,
    VERTICALEXTENT,
    VERF;


    // autres mots-clefs à gérer pour la compatibilité WKT-CTS
//    FITTED_CS;
//    PARAM_MT, // pas de rétrocompatibilité
//    INVERSE_MT, // pas de rétrocompatibilité
//    PASSTHROUGH_MT, // pas de rétrocompatibilité
//    CONCAT_MT // pas de rétrocompatibilité
//    TOWGS84,

    @Override
    public boolean test(final Object token) {
        return token instanceof WktKeyword.Lexeme && this.equals(((WktKeyword.Lexeme) token).getSemantics());
    }

    public static WktKeyword toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<WktKeyword> {

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public WktKeyword parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<WktKeyword> builder() {
        return new EnumLexemeBuilder<WktKeyword>(WktKeyword.class, WktKeyword.values()) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }

    public static boolean isUnit(final WktKeyword t) {
            return WktKeyword.UNIT.equals(t)
                    || WktKeyword.ANGLEUNIT.equals(t)
                    || WktKeyword.LENGTHUNIT.equals(t)
                    || WktKeyword.SCALEUNIT.equals(t)
                    || WktKeyword.PARAMETRICUNIT.equals(t)
                    || WktKeyword.TIMEUNIT.equals(t);
    }

    public static boolean isExtent(final WktKeyword t) {
            return WktKeyword.AREA.equals(t)
                    || WktKeyword.BBOX.equals(t)
                    || WktKeyword.VERTICALEXTENT.equals(t)
                    || WktKeyword.TIMEEXTENT.equals(t);
    }
}
