package com.cosmoloj.processor.bibliography.writer;

import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonObject;
import com.cosmoloj.language.json.expression.JsonValue;
import com.cosmoloj.language.json.lexeme.compound.JsonSignedNumericLiteral;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import com.cosmoloj.processor.common.writer.TypeElementWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author Samuel Andrés
 */
public class BibliographyWriter extends TypeElementWriter {

    private static final String ENTRY_TYPE_FIELD = "entry_type";
    private static final String CITE_KEY_FIELD = "cite_key";
    private static final String CROSS_REF_KEY = "crossref";

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final List<String> IMPORTS = List.of("com.cosmoloj.util.bib.Reference",
        "com.cosmoloj.util.bib.Book",
        "com.cosmoloj.util.bib.Proceedings",
        "com.cosmoloj.util.bib.InProceedings",
        "com.cosmoloj.util.bib.Article",
        "com.cosmoloj.util.bib.Misc",
        "com.cosmoloj.util.bib.TechReport",
        "com.cosmoloj.util.bib.TechReportKind",
        "com.cosmoloj.util.bib.PhdThesis");

    private final JsonArray<JsonValue> json;

    public BibliographyWriter(
            final ProcessingEnvironment env,
            final TypeElement element,
            final JsonArray<JsonValue> json) {
        super(env, element, "Bibliography");
        this.json = json;
    }

    @Override
    public void printImports() {

        IMPORTS.forEach(i -> {
            printImport(i);
            ln();
        });
        ln();
    }

    @Override
    public void printTypeElement() {

        LOG.info("generate file");
        indentln("class " + targetTypeName() + " {");
        ln();

        incrIndent();
        indentln("protected " + targetTypeName() + "() {");
        indentln("}");
        ln();

        for (final JsonValue bibEntry : json.getList()) {
            final List<Map.Entry<QuotedString, JsonValue>> entries = ((JsonObject) bibEntry).getEntries();

            final Map<String, JsonValue> map = entries.stream()
                    .collect(Collectors.toMap(e -> e.getKey().getSemantics(), Map.Entry::getValue));

            final JsonValue crossref = map.get(CROSS_REF_KEY);

            if (crossref != null) {
                indentln("@Reference(value = {"
                        + ((QuotedString) crossref).getSemantics().toUpperCase(Locale.ROOT) + "})");
            }

            switch (((QuotedString) map.get(ENTRY_TYPE_FIELD)).getSemantics()) {
                case "article" -> {
                    indent("@Article(");
                    writeKeys(map, "title", "subtitle", "pages", "issue", "volume", "month", "year", "url");
                }
                case "inproceedings" -> {
                    indent("@InProceedings(");
                    writeKeys(map, "title", "subtitle", "pages", "issue", "volume", "month", "year", "url");
                }
                case "phdthesis" -> {
                    indent("@PhdThesis(");
                    writeKeys(map, "title", "year", "url");
                }
                case "book" -> {
                    indent("@Book(");
                    writeKeys(map, "title", "editor", "year", "url");
                }
                case "techreport" -> {
                    indent("@TechReport(kind = TechReportKind."
                            + ((QuotedString) map.get("kind")).getSemantics().toUpperCase(Locale.ROOT));
                    writeKeys(map, false, "title", "number", "version", "year", "url");
                }
                case "proceedings" -> {
                    indent("@Proceedings(");
                    writeKeys(map, true, "title", "issn", "eIssn", "url");
                }
                case "misc" -> {
                    indent("@Misc(");
                    writeKeys(map, true, "title", "issn", "eIssn", "url");
                }
                default -> {
                }
            }

            final String key = ((QuotedString) map.get(CITE_KEY_FIELD)).getSemantics();
            indentln("public static final String " + key.toUpperCase(Locale.ROOT) + " = \"" + key + "\";");
            ln();
        }

        decrIndent();
        indentln("}");

    }

    private void writeKeys(final Map<String, JsonValue> map, final String... keys) {
        writeKeys(map, true, keys);
    }

    private void writeKeys(final Map<String, JsonValue> map, final boolean asFirst, final String... keys) {
        incrIndent();

        boolean first = asFirst;
        for (final String key : keys) {
            final JsonValue value = map.get(key);
            // only not null and skip "reference" type Json objects
            if (value != null && !(value instanceof JsonObject)) {
                if (first) {
                    first = false;
                } else {
                    println(",");
                    indent();
                }

                if (value instanceof QuotedString string) {
                    print(key + " = \"" + string.getSemantics() + "\"");
                } else if (value instanceof JsonSignedNumericLiteral literal) {
                    print(key + " = " + literal.getSemantics());
                }
            }
        }
        println(")");
        decrIndent();
    }
}
