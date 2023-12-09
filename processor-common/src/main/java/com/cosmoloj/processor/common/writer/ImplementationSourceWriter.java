package com.cosmoloj.processor.common.writer;

import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 *
 * @author Samuel Andrés
 */
public abstract class ImplementationSourceWriter extends AbstractJavaWriter implements JavaWriter {

    private final Generics generics;
    private final ProcessingEnvironment procEnv;
    private static final String PREFIX = "Bean";
    private final TypeElement type;
    private static final String SOURCE_PACKAGE = "api";
    private static final String TARGET_PACKAGE = "bean";
    private final StringBuilder builder;
    private final boolean nested;

    protected ImplementationSourceWriter(final ImplementationSourceWriter fw,
            final ProcessingEnvironment procEnv, final TypeElement elt) {
        super(procEnv, null);
        this.generics = Generics.of(elt, procEnv);
        this.procEnv = procEnv;
        this.type = elt;
        this.builder = fw.builder;
        this.nested = true;
    }

    protected ImplementationSourceWriter(final ProcessingEnvironment procEnv, final TypeElement type) {
        super(procEnv, null);
        this.generics = Generics.of(type, procEnv);
        this.procEnv = procEnv;
        this.type = type;
        this.builder = new StringBuilder();
        this.nested = false;
    }

    protected boolean isNested() {
        return nested;
    }

    @Override
    public String flush() {

        if (!nested) {
            final String[] pack = ProcessorUtil.replacePackage(
                    ProcessorUtil.packages(procEnv, type), SOURCE_PACKAGE, TARGET_PACKAGE);
            if (pack.length > 0) {
                printPackage(pack);
                ln(2);
            }

            printImport("javax.annotation.processing.Generated");
            ln(2);
        }
        printType();
        return this.builder.toString();
    }

    @Override
    protected StringBuilder builder() {
        return builder;
    }

    public final TypeElement getType() {
        return type;
    }

    protected String typeName() {
        return PREFIX + type.getSimpleName().toString();
    }

    protected abstract void printType();

    @Override
    protected String filePath() {
        return String.join(".",
                ProcessorUtil.replacePackage(ProcessorUtil.packages(procEnv, type), SOURCE_PACKAGE, TARGET_PACKAGE))
                + "." + typeName();
    }

    protected final void hierarchyDoc() {
        hierarchyDoc((DeclaredType) getType().asType());
    }

    private void hierarchyDoc(final DeclaredType declaredType) {

        indent();
        docLine("<li>", ProcessorUtil.toHtml(declaredType));

        final List<? extends TypeMirror> directSupertypes
                = getProcessingEnvironment().getTypeUtils().directSupertypes(declaredType);

        if (!directSupertypes.isEmpty()) {
            indent();
            docLine("<ul>");
            for (final TypeMirror t : directSupertypes) {
                hierarchyDoc((DeclaredType) t);
            }
            indent();
            docLine("</ul>");
        }
    }

    /**
     * <div class="fr">Documentation des correspondances des types génériques.</div>
     *
     */
    protected final void genericsDoc() {

        for (final Map.Entry<TypeElement, Map<Name, TypeMirror>> entry : generics.getMap().entrySet()) {
            if (!entry.getValue().isEmpty()) {
                indent();
                docLine("<table>");
                indent();
                docLine("<caption>", ProcessorUtil.toHtml(entry.getKey()), "</caption>");
                indent();
                docLine("<tbody>");
                for (final Map.Entry<Name, TypeMirror> entry2 : entry.getValue().entrySet()) {
                    indent();
                    docLine("<tr><td>", ProcessorUtil.toHtml(entry2.getKey()),
                            "<td>", ProcessorUtil.toHtml(entry2.getValue()));
                }
                indent();
                docLine("</table>");
            }
        }

        indent();
        docLine();

        final Map<Name, TypeMirror> current = generics.getMap().get(type);
        for (final Name entry2 : current.keySet()) {
            indent();
            docLine("@param <", entry2, ">");
        }
    }
}
