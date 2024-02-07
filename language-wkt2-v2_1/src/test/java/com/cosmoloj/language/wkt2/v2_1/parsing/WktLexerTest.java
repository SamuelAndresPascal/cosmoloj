package com.cosmoloj.language.wkt2.v2_1.parsing;


import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.RangeMeaningType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
    public void readAxisRangeMeaning1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("wraparound", LD, RD);

        lexer.lex(RangeMeaningType.class);
        Assertions.assertEquals(RangeMeaningType.WRAPAROUND, lexer.lexeme().getSemantics());
    }

    @Test
    public void readAxisRangeMeaning2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("exact", LD, RD);

        lexer.lex(RangeMeaningType.class);
        Assertions.assertEquals(RangeMeaningType.EXACT, lexer.lexeme().getSemantics());
    }

    @Test
    public void readPixelInCellLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("cellCenter", LD, RD);

        lexer.lex(PixelInCell.class);
        Assertions.assertEquals(PixelInCell.CELL_CENTER, lexer.lexeme().getSemantics());
    }

    @Test
    public void readPixelInCellLexeme2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("cellcenter", LD, RD);

        lexer.lex(PixelInCell.class);
        Assertions.assertEquals(PixelInCell.CELL_CENTER, lexer.lexeme().getSemantics());
    }

    @Test
    public void readDirectionLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("clockwise", LD, RD);

        lexer.lex(Direction.class);
        Assertions.assertEquals(Direction.clockwise, lexer.lexeme().getSemantics());
    }

    @Test
    public void readCsTypeLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("affine", LD, RD);

        lexer.lex(CsType.class);
        Assertions.assertEquals(CsType.AFFINE, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCRS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("PROJCRS", LD, RD);

        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme3() throws LanguageException {

        final LanguageException ex = Assertions.assertThrows(LanguageException.class, () -> {

            final WktLexer lexer = WktLexer.initialize("\"PROJCRS\"", LD, RD);

            lexer.lex(WktKeyword.class);
            Assertions.assertEquals("PROJCRS", lexer.lexeme().getSemantics());
        });
        System.out.println(ex.getMessage());
    }

    @Test
    public void readWktKeywordLexeme32() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("  PROJCRS  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals(WktKeyword.PROJCRS, lexer.lexeme().getSemantics());
    }

    public void readWktKeywordLexeme331() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("LINES2TRING", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertNull(lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme332() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize(" \"LINES2TRING\"  ", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals("LINES2TRING", lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme4() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("GEODCRS", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.GEODCRS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktKeywordLexeme5() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("USAGE", LD, RD);

        lexer.lex(WktKeyword.class);
        Assertions.assertEquals(WktKeyword.USAGE, lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme1() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"135\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("135", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme2() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1''35\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme3() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"'1'35\"", LD, RD);

        lexer.lex(QuotedLatinText.class);
        Assertions.assertEquals("'1'35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme4() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1654\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1654", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme5() throws LanguageException {

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
    public void characterStringLiteralLexeme6() throws LanguageException {

        final WktLexer lexer =  WktLexer.initialize("\"1''35\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme8() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("\"1'35\"", LD, RD);

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1'35", lexer.lexeme().getSemantics());
    }
}
