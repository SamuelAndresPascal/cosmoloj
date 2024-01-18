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
public enum CsType implements SemanticEnum<CsType>, Predicate<Object> {

    affine, Cartesian, cylindrical, ellipsoidal, linear, parametric, polar, spherical, temporal, vertical;

    @Override
    public boolean test(final Object token) {
        return token instanceof CsType.Lexeme cs && this.equals(cs.getSemantics());
    }

    public static CsType toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<CsType> {

        public static final Predicate<Object> INSTANCE_OF = Lexeme.class::isInstance;

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public CsType parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<CsType> builder() {
        return new EnumLexemeBuilder<CsType>(CsType.class, CsType.values(), EnumLexemeBuilder.Case.IGNORE,
                Locale.ROOT) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
