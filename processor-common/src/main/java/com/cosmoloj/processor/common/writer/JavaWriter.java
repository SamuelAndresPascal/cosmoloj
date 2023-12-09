package com.cosmoloj.processor.common.writer;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

/**
 *
 * @author Samuel Andrés
 */
public interface JavaWriter {

    /**
     * @return <span class="fr">la largeur d'une unité d'indentation</span>
     */
    default int defaultIndent() {
        return 4;
    }

    /**
     * @param arg <span class="fr">augmente l'indentation courante du nombre d'unités indiqué en paramètre</span>
     */
    void incrIndent(int arg);

    /**
     * <span class="fr">augmente l'indentation courante d'une unité</span>
     */
    default void incrIndent() {
        incrIndent(1);
    }

    /**
     * @param arg <span class="fr">diminue l'indentation courante du nombre d'unités indiqué en paramètre</span>
     */
    void decrIndent(int arg);

    /**
     * <span class="fr">diminue l'indentation courante d'une unité</span>
     */
    default void decrIndent() {
        decrIndent(1);
    }

    void indent();

    default void indentln(final String arg) {
        indent();
        println(arg);
    }

    default void println(final Object arg) {
        print(arg);
        ln();
    }

    default void openComment() {
        print("/*");
    }

    default void closeComment() {
        print("*/");
    }

    default void openDoc() {
        print("/**");
    }

    default void closeDoc() {
        print(" */");
    }

    default void openDocLine() {
        print(" * ");
    }

    default void docLine(final Object... doc) {
        openDocLine();
        print(doc);
        ln();
    }

    default void openGenerics() {
        print('<');
    }

    default void closeGenerics() {
        print('>');
    }

    default void openBlock() {
        print('{');
    }

    default void closeBlock() {
        print('}');
    }

    default void space() {
        space(1);
    }

    default void space(final int i) {
        for (int j = 0; j < i; j++) {
            print(' ');
        }
    }

    default void ln() {
        ln(1);
    }

    default void ln(final int i) {
        for (int j = 0; j < i; j++) {
            print(System.lineSeparator());
        }
    }

    default void printImport(final String name) {
        print("import ", name, ";");
    }

    default void printPackage(final String[] name) {
        printPackage(String.join(".", name));
    }

    default void printPackage(final String name) {
        print("package ", name, ";");
    }

    default void comment(final Object... comment) {
        print("/*");
        print(comment);
        print("*/");
    }

    default void indent(final String arg) {
        indent();
        print(arg);
    }

    void print(Object object);

    default void indent(final Object... args) {
        indent();
        print(args);
    }

    default void print(final Object... args) {
        for (final Object s : args) {
            print(s);
        }
    }

    default void println(final Object... args) {
        print(args);
        ln();
    }

    default void indentln(final Object... args) {
        indent(args);
        ln();
    }

    default void appendHtml(final Object... doc) {
        print(Stream.of(doc)
                .map(ProcessorUtil::toHtml)
                .collect(Collectors.joining()));
    }

    default void docHtmlLine(final String tag, final Object... doc) {
        openDocLine();
        print("<", tag, ">");
        print(Stream.of(doc)
                .map(ProcessorUtil::toHtml)
                .collect(Collectors.joining()));
        print("</", tag, ">");
        ln();
    }

    int beginIteration(String separator);

    void endIteration(int i);

    void nextIt(int iteration);

    default void appendIt(final String separator, final Iterable<?> iterable) {
        appendIt(separator, iterable, this::print);
    }

    default <T> void appendIt(final String separator, final Iterable<? extends T> iterable,
            final Consumer<T> consumer) {

        final int it = beginIteration(separator);
        for (final T itElt : iterable) {
            nextIt(it);
            consumer.accept(itElt);
        }
        endIteration(it);
    }

    /**
     *
     * @param type
     * @param includeBounds
     * @return if type parameters have been written.
     */
    default boolean printTypeParameters(final TypeElement type, final boolean includeBounds) {

        if (type.getTypeParameters().isEmpty()) {
            return false;
        }

        openGenerics();

        final int parametersIt = beginIteration(", ");
        for (final TypeParameterElement tpe : type.getTypeParameters()) {
            nextIt(parametersIt);
            print(tpe);
            if (includeBounds && !tpe.getBounds().isEmpty()) {
                print(" extends ");
                appendIt(" & ", tpe.getBounds());
            }
        }
        endIteration(parametersIt);

        closeGenerics();

        return true;
    }
}
