package com.cosmoloj.language.wkt.cts.lexeme;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum AxisDirectionName implements SemanticEnum<AxisDirectionName>, Predicate<Token> {

    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN,
    OTHER;

    @Override
    public boolean test(final Token token) {
        return token instanceof AxisDirectionName.Lexeme
                && this.equals(((AxisDirectionName.Lexeme) token).getSemantics());
    }

    public static AxisDirectionName toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<AxisDirectionName> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof AxisDirectionName.Lexeme;

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public AxisDirectionName parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<AxisDirectionName> builder() {
        return new EnumLexemeBuilder<AxisDirectionName>(AxisDirectionName.class, AxisDirectionName.values()) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
