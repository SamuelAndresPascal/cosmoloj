package com.cosmoloj.language.json.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonObject;
import com.cosmoloj.language.json.lexeme.compound.JsonSignedNumericLiteral;
import com.cosmoloj.language.json.lexeme.simple.Keyword;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class JsonParserTest {

    @Test
    public void readEmptyObject_1() throws LanguageException {

        final JsonParser parser = JsonParser.of("{}");

        Assertions.assertEquals(0, parser.object().getEntries().size());
    }

    @Test
    public void readObject_1() throws LanguageException {

        final JsonParser parser = JsonParser.of("{\"titi\": \"tutu\"}");

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_2() throws LanguageException {

        final JsonParser parser = JsonParser.of("{\"titi\": {\"tata\": \"tutu\"}}");

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals("tutu", ((QuotedString) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_3() throws LanguageException {

        final JsonParser parser = JsonParser.of("""
                            {"titi" :
                                {"tata": "tutu"}
                            }""");

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals("tutu", ((QuotedString) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_4() throws LanguageException {

        final JsonParser parser = JsonParser.of("{\"titi\": true}");

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_5() throws LanguageException {

        final JsonParser parser = JsonParser.of("{\"titi\": {\"tata\": true}}");

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.TRUE, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_6() throws LanguageException {

        final JsonParser parser = JsonParser.of("""
                                                {"titi" :
                                                    {"tata": true}
                                                }""");

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.TRUE, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_7() throws LanguageException {

        final String text = "{\"titi\": false}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_8() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": false}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.FALSE, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_9() throws LanguageException {

        final String text = """
                            {"titi" :
                                {"tata": false}
                            }""";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.FALSE, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_10() throws LanguageException {

        final String text = "{\"titi\": null}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_11() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": null}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.NULL, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_12() throws LanguageException {

        final String text = """
                            {"titi" :
                                {"tata": null}
                            }""";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(Keyword.NULL, ((Keyword.Lexeme) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_13() throws LanguageException {

        final String text = "{\"titi\": 12}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_14() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": 12}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(12, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_15() throws LanguageException {

        final String text = "{\"titi\": 12.3}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_16() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": 12.3}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(12.3, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_17() throws LanguageException {

        final String text = "{\"titi\": 12.3E15}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_18() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": 12.3E15}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(12.3e15, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_19() throws LanguageException {

        final String text = "{\"titi\": -12}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_20() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": -12}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(-12, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_21() throws LanguageException {

        final String text = "{\"titi\": -12.3}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_22() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": -12.3}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(-12.3, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_23() throws LanguageException {

        final String text = "{\"titi\": -12.3E-15}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(1, parser.object().getEntries().size());
    }

    @Test
    public void readObject_24() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": -12.3E-15}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(-12.3e-15, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readObject_25() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": -12.3e-15}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(-12.3e-15, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void readArray_1() throws LanguageException {
        final String text = "[]";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        Assertions.assertEquals(0, parser.array().getList().size());
    }

    @Test
    public void readArray_2() throws LanguageException {
        final String text = "[true, false]";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonArray array = parser.array();
        Assertions.assertEquals(2, array.getList().size());
        Assertions.assertEquals(Keyword.TRUE, ((Keyword.Lexeme) array.getList().get(0)).getSemantics());
        Assertions.assertEquals(Keyword.FALSE, ((Keyword.Lexeme) array.getList().get(1)).getSemantics());
    }

    @Test
    public void read_1() throws LanguageException {

        final String text = """
                            {"titi": {"tata": -12.3E-15,
                                        "tutu": [true, null, {"coco": "kiki",
                                                                "kayak": {"youyou": "popo",
                                                                            "papa": "toto",
                                                                            "titi": {"toutou": [4, 2, [[]]]}
                                                                            },
                                                                "yuyuyu": false
                                                                }]
                                        }
                            }""";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = parser.object();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(2, object1.getEntries().size());

        final var entry10 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry10.getKey().getSemantics());

        Assertions.assertEquals(-12.3e-15, ((JsonSignedNumericLiteral) entry10.getValue()).getSemantics());

        final var entry11 = object1.getEntries().get(1);

        Assertions.assertEquals("tutu", entry11.getKey().getSemantics());

        final var array1 = (JsonArray) entry11.getValue();

        Assertions.assertEquals(3, array1.getList().size());

        final var array10 = array1.getList().get(0);

        Assertions.assertEquals(Keyword.TRUE, ((Keyword.Lexeme) array10).getSemantics());

        final var array11 = array1.getList().get(1);

        Assertions.assertEquals(Keyword.NULL, ((Keyword.Lexeme) array11).getSemantics());

        final var array12 = (JsonObject) array1.getList().get(2);

        Assertions.assertEquals(3, array12.getEntries().size());

        final var entry20 = array12.getEntries().get(0);

        Assertions.assertEquals("coco", entry20.getKey().getSemantics());

        Assertions.assertEquals("kiki", ((QuotedString) entry20.getValue()).getSemantics());

        final var entry21 = array12.getEntries().get(1);

        Assertions.assertEquals("kayak", entry21.getKey().getSemantics());

        final var object2 = (JsonObject) entry21.getValue();

        Assertions.assertEquals(3, object2.getEntries().size());

        final var entry210 = object2.getEntries().get(0);

        Assertions.assertEquals("youyou", entry210.getKey().getSemantics());

        Assertions.assertEquals("popo", ((QuotedString) entry210.getValue()).getSemantics());

        final var entry211 = object2.getEntries().get(1);

        Assertions.assertEquals("papa", entry211.getKey().getSemantics());

        Assertions.assertEquals("toto", ((QuotedString) entry211.getValue()).getSemantics());

        final var entry212 = object2.getEntries().get(2);

        final var object3 = (JsonObject) entry212.getValue();

        Assertions.assertEquals(1, object3.getEntries().size());

        final var entry2120 = object3.getEntries().get(0);

        Assertions.assertEquals("toutou", entry2120.getKey().getSemantics());

        final var array2120 = (JsonArray) entry2120.getValue();

        Assertions.assertEquals(3, array2120.getList().size());
        Assertions.assertEquals(4, ((JsonSignedNumericLiteral) array2120.getList().get(0)).getSemantics());
        Assertions.assertEquals(2, ((JsonSignedNumericLiteral) array2120.getList().get(1)).getSemantics());

        final var array21211 = (JsonArray) array2120.getList().get(2);
        Assertions.assertEquals(1, array21211.getList().size());

        final var array212111 = (JsonArray) array21211.getList().get(0);
        Assertions.assertEquals(0, array212111.getList().size());

        final var entry22 = array12.getEntries().get(2);

        Assertions.assertEquals("yuyuyu", entry22.getKey().getSemantics());

        Assertions.assertEquals(Keyword.FALSE, ((Keyword.Lexeme) entry22.getValue()).getSemantics());

    }

    @Test
    public void read_2() throws LanguageException {

        final String text = "{\"titi\": {\"tata\": -12.3e-15}}";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonObject object0 = (JsonObject) parser.parse();
        Assertions.assertEquals(1, object0.getEntries().size());

        final var entry0 = object0.getEntries().get(0);

        Assertions.assertEquals("titi", entry0.getKey().getSemantics());

        final var object1 = (JsonObject) entry0.getValue();

        Assertions.assertEquals(1, object1.getEntries().size());

        final var entry1 = object1.getEntries().get(0);

        Assertions.assertEquals("tata", entry1.getKey().getSemantics());

        Assertions.assertEquals(-12.3e-15, ((JsonSignedNumericLiteral) entry1.getValue()).getSemantics());
    }

    @Test
    public void read_3() throws LanguageException {
        final String text = "[true, false]";

        final JsonLexer lexer = new JsonLexer(new DefaultStringScanner(text), new ArrayList<>());
        lexer.initialize();
        final JsonParser parser = new JsonParser(lexer);

        final JsonArray array = (JsonArray) parser.parse();
        Assertions.assertEquals(2, array.getList().size());
        Assertions.assertEquals(Keyword.TRUE, ((Keyword.Lexeme) array.getList().get(0)).getSemantics());
        Assertions.assertEquals(Keyword.FALSE, ((Keyword.Lexeme) array.getList().get(1)).getSemantics());
    }
}
