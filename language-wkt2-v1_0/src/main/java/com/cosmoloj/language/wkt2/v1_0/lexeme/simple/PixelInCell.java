package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.language.api.semantic.Token;
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
public enum PixelInCell implements SemanticEnum<PixelInCell>, Predicate<Token> {

    cellCentre, cellCenter, cellCorner;

    @Override
    public boolean test(final Token token) {
        return token instanceof PixelInCell.Lexeme && this.equals(((PixelInCell.Lexeme) token).getSemantics());
    }

    public static PixelInCell toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<PixelInCell> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof PixelInCell.Lexeme;

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public PixelInCell parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<PixelInCell> builder() {
        return new EnumLexemeBuilder<PixelInCell>(PixelInCell.class, PixelInCell.values(),
                EnumLexemeBuilder.Case.IGNORE, Locale.ROOT) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
