package com.cosmoloj.processor.common.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <div class="fr">Une implémentation abstraite de base de JavaWriter.</div>
 *
 * @author Samuel Andrés
 */
public abstract class AbstractJavaWriter implements JavaWriter {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProcessingEnvironment procEnv;
    private final RoundEnvironment roundEnv;
    private int indent = 0;

    protected AbstractJavaWriter(final ProcessingEnvironment procEnv, final RoundEnvironment roundEnv) {
        this.procEnv = procEnv;
        this.roundEnv = roundEnv;
    }

    public final Types getTypes() {
        return procEnv.getTypeUtils();
    }

    public final ProcessingEnvironment getProcessingEnvironment() {
        return procEnv;
    }

    protected final RoundEnvironment getRoundEnvironment() {
        return roundEnv;
    }

    public void writeFile() throws IOException {

        final FileObject fo = procEnv.getFiler()
                .createSourceFile(filePath());

        try (OutputStream os = fo.openOutputStream();
                PrintWriter pw = new PrintWriter(os)) {

            LOG.info("generate file");
            pw.println(flush());
            pw.flush();
        }
    }

    protected abstract StringBuilder builder();

    protected abstract String filePath();

    protected abstract String flush();

    private final Iterations iteration = new Iterations();

    @Override
    public final int beginIteration(final String separator) {
        return iteration.create(separator);
    }

    @Override
    public final void endIteration(final int it) {
        iteration.remove(it);
    }

    @Override
    public final void nextIt(final int it) {
        iteration.nextIt(it, this::print);
    }

    @Override
    public final void print(final Object arg) {
        builder().append(arg);
    }

    @Override
    public final void indent() {
        for (int j = 0; j < indent; j++) {
            print(' ');
        }
    }

    @Override
    public void incrIndent(final int incr) {
        this.indent += (incr * defaultIndent());
    }

    @Override
    public void decrIndent(final int decr) {
        this.indent -= (decr * defaultIndent());
    }
}
