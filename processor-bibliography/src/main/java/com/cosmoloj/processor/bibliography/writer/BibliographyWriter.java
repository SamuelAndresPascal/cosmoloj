package com.cosmoloj.processor.bibliography.writer;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonObject;
import com.cosmoloj.language.json.expression.JsonValue;
import com.cosmoloj.language.json.lexeme.simple.QuotedString;
import com.cosmoloj.processor.bibliography.annotation.Entry;
import com.cosmoloj.processor.common.writer.TypeElementWriter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    private static final List<String> IMPORTS = List.of("com.cosmoloj.util.bib.Cite",
        "com.cosmoloj.processor.bibliography.annotation.Entry",
        "com.cosmoloj.processor.bibliography.annotation.EntryType");

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
                indentln("@Cite(value = {"
                        + ((QuotedString) crossref).getSemantics().toUpperCase(Locale.ROOT) + "})");
            }

            indent("@Entry(entryType = EntryType."
                            + ((QuotedString) map.get(ENTRY_TYPE_FIELD)).getSemantics().toUpperCase(Locale.ROOT));
            incrIndent();

            final String[] toDisplay = Stream.of(Entry.class.getMethods()).map(Method::getName).toArray(String[]::new);

            for (final String key : toDisplay) {

                if ("entryType".equals(key)) {
                    continue;
                }

                final JsonValue value = map.get(key);
                // only not null and skip "reference" type Json objects
                if (value != null && !(value instanceof JsonObject)) {
                    println(",");
                    indent();
                    print(key + " = \"" + ((Lexeme) value).getSemantics() + "\"");
                }
            }
            println(")");
            decrIndent();

            final String key = ((QuotedString) map.get(CITE_KEY_FIELD)).getSemantics();
            indentln("public static final String " + key.toUpperCase(Locale.ROOT) + " = \"" + key + "\";");
            ln();
        }

        decrIndent();
        indentln("}");
    }
}
