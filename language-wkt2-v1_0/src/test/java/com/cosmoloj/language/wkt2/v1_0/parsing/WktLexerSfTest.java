package com.cosmoloj.language.wkt2.v1_0.parsing;


import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests de compatibilité avec la norme WKT-SF.
 *
 * @author Samuel Andrés
 */
public class WktLexerSfTest {

    private static final int LD = '[';
    private static final int RD = ']';

    @Test
    public void readWktKeywordLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.PROJCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCS", LD, RD);

        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_3() throws LanguageException {

        final LanguageException ex = Assertions.assertThrows(LanguageException.class, () -> {

            final WktLexer lexer = WktLexer.initialize("\"PROJCS\"", LD, RD);

            lexer.lex(WktKeyword.class);
            Assertions.assertEquals("PROJCS", lexer.lexeme().getSemantics());
        });
        Assertions.assertEquals("""
                                unexpected code point " at 1, but expected:
                                A|B|C|D|E|G|I|L|M|O|P|R|S|T|U|V|a|b|c|d|e|g|i|l|m|o|p|r|s|t|u|v
                                """, ex.getMessage());
    }

    @Test
    public void readWktKeywordLexeme_3_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("  PROJCS  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCS, lexer.lexeme().getSemantics());
    }

    public void readWktKeywordLexeme_3_3_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("LINES2TRING", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertNull(lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_3_3_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize(" \"LINES2TRING\"  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals("LINES2TRING", lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_4() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("GEOGCS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.GEOGCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"135\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("135", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1''35\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_3() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"'1'35\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("'1'35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_4() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1654\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1654", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_5() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1654\" \"adfum\" \"bd''f um\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1654", lexer.lexeme().getSemantics());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals("adfum", lexer.lexeme().getSemantics());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals("bd''f um", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_6() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1''35\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_8() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1'35\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1'35", lexer.lexeme().getSemantics());
    }
}
