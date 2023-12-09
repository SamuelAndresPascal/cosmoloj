package com.cosmoloj.processor.common.writer;

import java.time.LocalDateTime;
import java.util.Arrays;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author Samuel Andrés
 */
public final class ProcessorUtil {

    private ProcessorUtil() {
    }

    public static String toHtml(final Object doc) {
        return doc.toString().replace("<", "&lt;").replace(">", "&gt;");
    }

    public static String generatedAnnotation(final Class<?> type) {
        return "@Generated(value = \"" + type.getCanonicalName() + "\", date = \"" + LocalDateTime.now() + "\")";
    }

    public static Name packageName(final ProcessingEnvironment env, final TypeElement element) {
        return env.getElementUtils()
                .getPackageOf(element)
                .getQualifiedName();
    }

    public static String[] packages(final ProcessingEnvironment env, final TypeElement element) {
        return packageName(env, element)
                .toString()
                .split("\\.");
    }

    public static String[] replaceAndCutPackage(final String[] packages, final String source, final String target) {
        for (int i = 0; i < packages.length; i++) {
            if (source.equals(packages[i])) {
                final String[] targetPackage = Arrays.copyOf(packages, i + 1);
                targetPackage[i] = target;
                return targetPackage;
            }
        }
        throw new IllegalStateException("API package not found");
    }

    public static String[] replacePackage(final String[] packages, final String source, final String target) {
        final String[] targetPackage = Arrays.copyOf(packages, packages.length);
        for (int i = 0; i < packages.length; i++) {
            if (source.equals(packages[i])) {
                targetPackage[i] = target;
            }
        }
        return targetPackage;
    }
}
