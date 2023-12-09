package com.cosmoloj.processor.common.writer;

import java.lang.invoke.MethodHandles;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class TypeElementWriter extends AstrolojCommonWriter {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProcessingEnvironment procEnv;
    private final TypeElement element;
    private final String suffix;
    private final StringBuilder builder = new StringBuilder();

    protected TypeElementWriter(final ProcessingEnvironment procEnv, final TypeElement element, final String suffix) {
        super(procEnv, null);
        this.procEnv = procEnv;
        this.element = element;
        this.suffix = suffix;
    }

    @Override
    protected final String flush() {
        LOG.debug("flush writer content");
        printPackage();
        printImports();
        printTypeElement();
        return builder.toString();
    }

    @Override
    protected final StringBuilder builder() {
        return builder;
    }

    @Override
    protected final String filePath() {
        final String pack = ProcessorUtil.packageName(procEnv, element).toString();
        if (pack.isEmpty()) {
            return element.getSimpleName() + suffix;
        } else {
            return pack + "." + element.getSimpleName() + suffix;
        }
    }

    protected void printImports() {
    }

    public final TypeElement getType() {
        return element;
    }

    protected final String targetTypeName() {
        return element.getSimpleName().toString() + suffix;
    }

    protected abstract void printTypeElement();

    private void printPackage() {
        final String[] pack = ProcessorUtil.packages(procEnv, element);
        if (pack.length != 0) {
            printPackage(pack);
            ln();
        }
    }
}
