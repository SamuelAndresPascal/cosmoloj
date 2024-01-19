package com.cosmoloj.language.wkt.cts.parsing;


import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.ArrayList;
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
    public void readUnitKeyword_a() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("UNIT", LD, RD);

        lexer.lex(WktName.class);
        Assertions.assertEquals(WktName.UNIT, lexer.lexeme().getSemantics());
    }

    @Test
    public void readUnitKeyword_b() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("UNIT", LD, RD);

        lexer.lex();
        Assertions.assertEquals(WktName.UNIT, lexer.lexeme().getSemantics());
    }

    @Test
    public void readAuthorityKeyword_a() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("AUTHORITY", LD, RD);

        lexer.lex(WktName.class);
        Assertions.assertEquals(WktName.AUTHORITY, lexer.lexeme().getSemantics());
    }

    @Test
    public void readAuthorityKeyword_b() throws LanguageException {

        final WktLexer lexer = WktLexer.initialize("AUTHORITY", LD, RD);

        lexer.lex();
        Assertions.assertEquals(WktName.AUTHORITY, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktNameLexeme_1() throws LanguageException {

        final String text = "PROJCS";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(WktName.class);
        Assertions.assertEquals(WktName.PROJCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktNameLexeme_2() throws LanguageException {

        final String text = "PROJCS";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex();
        Assertions.assertEquals(WktName.PROJCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktNameLexeme_3() throws LanguageException {

        final LanguageException ex = Assertions.assertThrows(LanguageException.class, () -> {
            final String text = "\"PROJCS\"";

            final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
            lexer.initialize();

            lexer.lex(WktName.class);
            Assertions.assertEquals("PROJCS", lexer.lexeme().getSemantics());
        });
        Assertions.assertEquals("""
                                unexpected code point " at 1, but expected:
                                A|C|D|E|F|G|I|L|P|S|T|U|V|a|c|d|e|f|g|i|l|p|s|t|u|v
                                """, ex.getMessage());
    }

    @Test
    public void readWktNameLexeme_3_2() throws LanguageException {

        final String text = "  PROJCS  ";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals(WktName.PROJCS, lexer.lexeme().getSemantics());
    }

    public void readWktNameLexeme_3_3_1() throws LanguageException {

        final String text = "LINES2TRING";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(WktName.class);
        Assertions.assertNull(lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktNameLexeme_3_3_2() throws LanguageException {

        final String text = " \"LINES2TRING\"  ";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        Assertions.assertNull(lexer.lexeme());
        lexer.flush();
        lexer.lex();
        Assertions.assertEquals("LINES2TRING", lexer.lexeme().getSemantics());
    }

    @Test
    public void readWktNameLexeme_4() throws LanguageException {

        final String text = "GEOGCS";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(WktName.class);
        Assertions.assertEquals(WktName.GEOGCS, lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_1() throws LanguageException {

        final String text = "\"135\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(QuotedName.class);
        Assertions.assertEquals("135", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_2() throws LanguageException {

        final String text = "\"1''35\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(QuotedName.class);
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_3() throws LanguageException {

        final String text = "\"'1'35\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        lexer.lex(QuotedName.class);
        Assertions.assertEquals("'1'35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_4() throws LanguageException {

        final String text = "\"1654\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1654", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_5() throws LanguageException {

        final String text = "\"1654\" \"adfum\" \"bd''f um\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

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

        final String text = "\"1''35\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1''35", lexer.lexeme().getSemantics());
    }

    @Test
    public void characterStringLiteralLexeme_8() throws LanguageException {

        final String text = "\"1'35\"";

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(text), new ArrayList<>(), LD, RD);
        lexer.initialize();

        Assertions.assertNull(lexer.lexeme());
        lexer.lex();
        Assertions.assertEquals("1'35", lexer.lexeme().getSemantics());
    }
}
