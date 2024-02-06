package com.cosmoloj.language.wkt.geom.parsing;


import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt.geom.lexeme.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests de compatibilité avec la norme WKT-SF.
 *
 * @author Samuel Andrés
 */
public class WktLexerTest {

    private static final int LD = '[';
    private static final int RD = ']';

    @Test
    public void readKeywordLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("POINT", LD, RD);

        lexer.lex(Keyword.class);
        Assertions.assertEquals(Keyword.POINT, lexer.lexeme().getSemantics());
    }

    @Test
    public void readKeywordLexeme2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("POINT", LD, RD);

        lexer.lex();
        Assertions.assertEquals(Keyword.POINT, lexer.lexeme().getSemantics());
    }

    @Test
    public void readKeywordLexeme_3() throws LanguageException {

        final LanguageException ex = Assertions.assertThrows(LanguageException.class, () -> {

            final WktLexer lexer = WktLexer.initialize("\"POINT\"", LD, RD);

            lexer.lex(Keyword.class);
            Assertions.assertEquals("POINT", lexer.lexeme().getSemantics());
        });
        Assertions.assertEquals("""
                                unexpected code point " at 1, but expected:
                                P|p|T|t|G|g|L|l|M|m
                                """, ex.getMessage());
    }

    @Test
    public void readKeywordLexeme_3_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("  POINT  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals(Keyword.POINT, lexer.lexeme().getSemantics());
    }

    public void readKeywordLexeme_3_3_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PONT", LD, RD);

        lexer.lex(Keyword.class);
        Assertions.assertNull(lexer.lexeme().getSemantics());
    }
}
