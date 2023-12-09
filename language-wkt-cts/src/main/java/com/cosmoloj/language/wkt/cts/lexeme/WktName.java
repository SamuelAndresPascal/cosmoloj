package com.cosmoloj.language.wkt.cts.lexeme;

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
@Reference(Cosmoloj.CTS_REVISION_V1_0)
@SectionReference(type = SectionReferenceType.SECTION, id = {"7", "8"})
public enum WktName implements SemanticEnum<WktName>, Predicate<Object> {

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

    @Override
    public boolean test(final Object token) {
        return token instanceof WktName.Lexeme && this.equals(((WktName.Lexeme) token).getSemantics());
    }

    public static WktName toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<WktName> {

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public WktName parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<WktName> builder() {
        return new EnumLexemeBuilder<WktName>(WktName.class, WktName.values()) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
