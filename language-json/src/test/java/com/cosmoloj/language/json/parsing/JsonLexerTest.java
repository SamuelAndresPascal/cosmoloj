package com.cosmoloj.language.json.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.json.lexeme.simple.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class JsonLexerTest {

    @Test
    public void readTrueKeyword() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("true");

        lexer.lex(Keyword.class);
        Assertions.assertEquals(Keyword.TRUE, lexer.lexeme().getSemantics());
    }

    @Test
    public void readFalseKeyword() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("false");

        lexer.lex(Keyword.class);
        Assertions.assertEquals(Keyword.FALSE, lexer.lexeme().getSemantics());
    }

    @Test
    public void readNullKeyword() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("null");

        lexer.lex(Keyword.class);
        Assertions.assertEquals(Keyword.NULL, lexer.lexeme().getSemantics());
    }

    @Test
    public void readUnsignedInteger_1() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("325");

        lexer.lex(UnsignedInteger.class);
        Assertions.assertEquals(325, lexer.lexeme().getSemantics());
    }

    @Test
    public void readUnsignedInteger_2() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("325");

        lexer.lex();
        Assertions.assertEquals(325, lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_1() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"coco\" ");

        lexer.lex();
        Assertions.assertEquals("coco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_2() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\\"co\" ");

        lexer.lex();
        Assertions.assertEquals("co\"co", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_3() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\\\co\" ");

        lexer.lex();
        Assertions.assertEquals("co\\co", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_4() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\nco\" ");

        lexer.lex();
        Assertions.assertEquals("co\nco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_5() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\bco\" ");

        lexer.lex();
        Assertions.assertEquals("co\bco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_6() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\fco\" ");

        lexer.lex();
        Assertions.assertEquals("co\fco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_7() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\/co\" ");

        lexer.lex();
        Assertions.assertEquals("co/co", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_8() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\rco\" ");

        lexer.lex();
        Assertions.assertEquals("co\rco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_9() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\tco\" ");

        lexer.lex();
        Assertions.assertEquals("co\tco", lexer.lexeme().getSemantics());
    }

    @Test
    public void readString_10() throws LanguageException {

        final JsonLexer lexer = JsonLexer.initialize("\"co\\u1234co\" ");

        lexer.lex();
        Assertions.assertEquals("co\u1234co", lexer.lexeme().getSemantics());
    }
}
