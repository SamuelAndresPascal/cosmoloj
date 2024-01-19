package com.cosmoloj.language.wkt2.v1_0.parsing;


import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.Direction;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktLexerTest {

    private static final int LD = '[';
    private static final int RD = ']';

    @Test
    public void readPixelInCellLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("cellCenter", LD, RD);

        lexer.lex(PixelInCell.class);
        Assertions.assertEquals(PixelInCell.CELL_CENTER, lexer.lexeme().getSemantics());
    }

    @Test
    public void readPixelInCellLexeme_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("cellcenter", LD, RD);

        lexer.lex(PixelInCell.class);
        Assertions.assertEquals(PixelInCell.CELL_CENTER, lexer.lexeme().getSemantics());
    }

    @Test
    public void readDirectionLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("clockwise", LD, RD);

        lexer.lex(Direction.class);
        Assertions.assertEquals(Direction.clockwise, lexer.lexeme().getSemantics());
    }

    @Test
    public void readCsTypeLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("affine", LD, RD);

        lexer.lex(CsType.class);
        Assertions.assertEquals(CsType.AFFINE, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCRS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCRS", LD, RD);

        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme_3() throws LanguageException {

        final LanguageException ex = Assertions.assertThrows(LanguageException.class, () -> {

            final WktLexer lexer = WktLexer.initialize("\"PROJCRS\"", LD, RD);

            lexer.lex(WktKeyword.class);
            Assertions.assertEquals("PROJCRS", lexer.lexeme().getSemantics());
        });
        System.out.println(ex.getMessage());
    }

    @Test
    public void readWktKeywordLexeme_3_2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("  PROJCRS  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    public void readWktKeywordLexeme_3_3_1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("LINES2TRING", LD, RD);

        lexer.lex(WktKeyword.Lexeme.class);
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

        final WktLexer lexer = WktLexer.initialize("GEODCRS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.GEODCRS, lexer.lexeme().getSemantics());
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

        final WktLexer lexer =  WktLexer.initialize("\"1''35\"", LD, RD);

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
